package com.sparta.todo.repository;

import com.sparta.todo.entity.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ManagerRepository {
    private final JdbcTemplate jdbcTemplate;

    public Manager save(Manager entity) {
        String sql = "INSERT INTO manager(name,email,created_at,updated_at) values(?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, entity.getName());
            ps.setObject(2, entity.getEmail());
            ps.setObject(3, entity.getCreatedAt());
            ps.setObject(4, entity.getUpdatedAt());
            return ps;
        }, keyHolder);

        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        entity.changeId(id);

        return entity;
    }

    public Optional<Manager> findById(Long id) {
        List<Manager> result = jdbcTemplate.query("SELECT * FROM manager WHERE id = ?", rowMapper(), id);
        return result.stream().findFirst();
    }

    public List<Manager> search() {
        String sql = "SELECT * FROM manager";
        return jdbcTemplate.query(sql, rowMapper());
    }

    public void update(Manager entity) {
        String sql = "UPDATE manager SET name = ?, email = ?, updated_at = NOW() WHERE id = ?";

        List<String> params = new ArrayList<>();
        params.add(entity.getName());
        params.add(entity.getEmail());
        params.add(String.valueOf(entity.getId()));

        jdbcTemplate.update(sql, params.toArray());
    }

    public void delete(long managerId) {
        String sql = "DELETE FROM manager WHERE id = ?";
        jdbcTemplate.update(sql, managerId);
    }

    private RowMapper<Manager> rowMapper() {
        return (rs, rowNum) -> {
            return new Manager(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
            );
        };
    }
}
