package com.sparta.todo.repository;

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
        String sql = "INSERT INTO todo(todo,manager_name,password,created_at,updated_at) values(?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, entity.getTodo());
            ps.setObject(2, entity.getManagerName());
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
        List<Todo> result = jdbcTemplate.query("SELECT * FROM todo WHERE id = ?", rowMapper(), id);
        return result.stream().findFirst();
    }

    public List<Todo> search(String date, String managerName) {
        StringBuilder sql = new StringBuilder("SELECT * FROM todo WHERE 1=1");
        List<String> params = new ArrayList<>();

        if (date != null) {
            LocalDate searchDate = LocalDate.parse(date);
            LocalDateTime startOfDay = searchDate.atTime(LocalTime.MIN);
            LocalDateTime endOfDay = searchDate.atTime(LocalTime.MAX);

            sql.append(" AND updated_at BETWEEN ? AND ?");
            params.add(startOfDay.toString());
            params.add(endOfDay.toString());
        }

        if (managerName != null) {
            sql.append(" AND manager_name = ?");
            params.add(managerName);
        }

        sql.append(" ORDER BY updated_at DESC");

        return jdbcTemplate.query(sql.toString(), rowMapper(), params.toArray());
    }

    public void update(Todo entity) {
        String sql = "UPDATE todo SET todo = ?, manager_name = ?, updated_at = NOW() WHERE id = ?";

        List<String> params = new ArrayList<>();
        params.add(entity.getTodo());
        params.add(entity.getManagerName());
        params.add(String.valueOf(entity.getId()));

        jdbcTemplate.update(sql, params.toArray());
    }

    public void delete(long todoId) {
        String sql = "DELETE FROM todo WHERE id = ?";
        jdbcTemplate.update(sql, todoId);
    }

    private RowMapper<Todo> rowMapper() {
        return (rs, rowNum) -> {
            return new Todo(
                    rs.getLong("id"),
                    rs.getString("todo"),
                    rs.getString("manager_name"),
                    rs.getString("password"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
            );
        };
    }
}
