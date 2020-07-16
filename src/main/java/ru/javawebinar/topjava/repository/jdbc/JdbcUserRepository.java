package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.JdbcUtil;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {
    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        JdbcUtil.isValid(user);

        if (user.isNew()) {
            user.setId(insertUser.executeAndReturnKey(parameterSource).intValue());
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE users SET name = :name, email = :email, password = :password, registered = :registered, " +
                        "enabled = :enabled, calories_per_day = :caloriesPerDay WHERE id = :id",
                parameterSource
        ) == 0) {
            return null;
        } else {
            namedParameterJdbcTemplate.update("DELETE FROM user_roles WHERE user_id = :id", parameterSource);
        }

        batchInsert(user, new ArrayList<>(user.getRoles()));
        return user;
    }

    public void batchInsert(User user, List<Role> roles) {
        this.jdbcTemplate.batchUpdate(
            "INSERT INTO user_roles (user_id, role) VALUES (?, ?)",
            new BatchPreparedStatementSetter() {
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1, user.getId());
                    ps.setString(2, roles.get(i).toString());
                }

                public int getBatchSize() {
                    return roles.size();
                }
            }
        );
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id = ?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id = ?", ROW_MAPPER, id);
        return loadRoles(DataAccessUtils.singleResult(users));
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email = ?", ROW_MAPPER, email);
        return loadRoles(DataAccessUtils.singleResult(users));
    }

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM users u LEFT JOIN user_roles r ON u.id = r.user_id ORDER BY name, email",
                new UserMapper()
        );
        User currentUser = null;

        for (Iterator<User> iterator = users.iterator(); iterator.hasNext(); ) {
            User user = iterator.next();

            if (currentUser == null || !Objects.requireNonNull(currentUser.getId()).equals(user.getId())) {
                currentUser = user;
            } else {
                currentUser.getRoles().addAll(user.getRoles());
                iterator.remove();
            }
        }

        return users;
    }

    private User loadRoles(User user) {
        if (user != null) {
            List<Map<String, Object>> roles = jdbcTemplate.queryForList(
                    "SELECT * FROM user_roles WHERE user_id = ?",
                    user.getId()
            );
            user.setRoles(roles.stream()
                    .map(map -> Role.valueOf((String) map.get("role")))
                    .collect(Collectors.toList()));
        }

        return user;
    }

    public static class UserMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setRegistered(rs.getTimestamp("registered"));
            user.setEnabled(rs.getBoolean("enabled"));
            user.setCaloriesPerDay(rs.getInt("calories_per_day"));
            user.setRoles(List.of(Role.valueOf(rs.getString("role"))));
            return user;
        }
    }
}


