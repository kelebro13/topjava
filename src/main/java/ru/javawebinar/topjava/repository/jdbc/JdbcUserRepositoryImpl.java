package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.RoleUtil;

import javax.sql.DataSource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public User save(User user) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("name", user.getName())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("registered", user.getRegistered())
                .addValue("enabled", user.isEnabled())
                .addValue("caloriesPerDay", user.getCaloriesPerDay());

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(map);
            user.setId(newKey.intValue());
            insertRoles(user);
        } else {
            deleteRoles(user);
            insertRoles(user);
            namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", map);
        }
        return user;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        return setRoles(DataAccessUtils.singleResult(users));
    }

    @Override
    public User getByEmail(String email) {
        User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        return setRoles(user);
    }

    @Override
    public List<User> getAll() {
        List<User> listUsers = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        Map<Integer, List<RoleUtil>> roles = jdbcTemplate.query("SELECT user_id, role FROM user_roles", (rs, rowNum) -> {
            RoleUtil roleUtil = new RoleUtil(
                    rs.getInt("user_id"),
                    Role.valueOf(rs.getString("role")));
            return roleUtil;
        }).stream().collect(Collectors.groupingBy(RoleUtil::getId));
        listUsers.forEach(u -> u.setRoles(roles.get(u.getId()).stream().map(roleUtil -> roleUtil.getRole()).collect(Collectors.toList())));
        return listUsers;
    }

    public void insertRoles(User user) {
        Set<Role> roles = user.getRoles();
        int userId = user.getId();
        Iterator<Role> iterator = roles.iterator();
        while(iterator.hasNext()) {
            jdbcTemplate.update("INSERT INTO user_roles (user_id, role) VALUES (?, ?)", userId, iterator.next().name());
        }
    }

    public User setRoles(User user) {
        if(user == null) return null;
        List<Role> roleList = jdbcTemplate.query("SELECT role FROM user_roles WHERE user_id=?", (rs, rowNum) -> {
            Role role = Role.valueOf(rs.getString("role"));
            return role;
        }, user.getId());
        user.setRoles(roleList);
        return user;
    }

    public void deleteRoles(User user) {
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", user.getId());
    }

}
