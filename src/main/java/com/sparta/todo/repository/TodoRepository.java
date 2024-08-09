package com.sparta.todo.repository;

import com.sparta.todo.entity.Manager;
import com.sparta.todo.entity.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TodoRepository {
    private final JdbcTemplate jdbcTemplate;

    public Todo save(Todo entity) {
        String sql = "INSERT INTO todo2(todo,manager_id,password,created_at,updated_at) values(?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, entity.getTodo());
            ps.setObject(2, entity.getManager().getId());
            ps.setObject(3, entity.getPassword());
            ps.setObject(4, entity.getCreatedAt());
            ps.setObject(5, entity.getUpdatedAt());
            return ps;
        }, keyHolder);

        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        entity.changeId(id);

        return entity;
    }

    public Optional<Todo> findById(Long id) {
        String sql =
            """
                SELECT 
                    t.id, 
                    t.todo, 
                    t.password, 
                    t.created_at, 
                    t.updated_at, 
                    m.id as manager_id, 
                    m.name as manager_name, 
                    m.email as manager_email,
                    m.created_at as manager_created_at,
                    m.updated_at as manager_updated_at
                FROM todo2 t JOIN manager m ON t.manager_id = m.id 
                WHERE t.id = ?
            """;

        List<Todo> result = jdbcTemplate.query(sql, rowMapper(), id);
        return result.stream().findFirst();
    }

    public List<Todo> search(String date, String managerName, int page, int size) {
        StringBuilder sql = new StringBuilder(
            """
                SELECT 
                    t.id, 
                    t.todo, 
                    t.password, 
                    t.created_at, 
                    t.updated_at, 
                    m.id as manager_id, 
                    m.name as manager_name, 
                    m.email as manager_email,
                    m.created_at as manager_created_at,
                    m.updated_at as manager_updated_at
                FROM todo2 t 
                JOIN manager m ON t.manager_id = m.id 
                WHERE 1=1
            """
        );

        List<Object> params = new ArrayList<>();

        if (date != null) {
            LocalDate searchDate = LocalDate.parse(date);
            LocalDateTime startOfDay = searchDate.atTime(LocalTime.MIN);
            LocalDateTime endOfDay = searchDate.atTime(LocalTime.MAX);

            sql.append(" AND t.updated_at BETWEEN ? AND ?");
            params.add(startOfDay);
            params.add(endOfDay);
        }

        if (managerName != null) {
            sql.append(" AND m.name = ?");
            params.add(managerName);
        }

        sql.append(" ORDER BY t.updated_at DESC");

        int offset = page * size;
        sql.append(" LIMIT ? OFFSET ?");
        params.add(size);
        params.add(offset);

        return jdbcTemplate.query(sql.toString(), rowMapper(), params.toArray());
    }

    public void update(Todo entity) {
        String sql = "UPDATE todo2 SET todo = ?, manager_id = ? WHERE id = ?";

        List<String> params = new ArrayList<>();
        params.add(entity.getTodo());
        params.add(entity.getManager().getId().toString());
        params.add(String.valueOf(entity.getId()));

        jdbcTemplate.update(sql, params.toArray());
    }

    public void delete(long todoId) {
        String sql = "DELETE FROM todo2 WHERE id = ?";
        jdbcTemplate.update(sql, todoId);
    }

    private RowMapper<Todo> rowMapper() {
        return (rs, rowNum) -> {
            Manager manager = new Manager(
                    rs.getLong("manager_id"),
                    rs.getString("manager_name"),
                    rs.getString("manager_email"),
                    rs.getTimestamp("manager_created_at").toLocalDateTime(),
                    rs.getTimestamp("manager_updated_at").toLocalDateTime()
            );

            return new Todo(
                    rs.getLong("id"),
                    rs.getString("todo"),
                    manager,
                    rs.getString("password"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
            );
        };
    }
}
