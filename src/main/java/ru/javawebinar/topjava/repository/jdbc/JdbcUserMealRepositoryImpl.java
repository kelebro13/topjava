package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcUserMealRepositoryImpl implements UserMealRepository {

    private static final BeanPropertyRowMapper<UserMeal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(UserMeal.class);
    private static final Comparator<UserMeal> USER_MEAL_COMPARATOR = Comparator.comparing(UserMeal::getDateTime).reversed();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertMeal;

    @Autowired
    public JdbcUserMealRepositoryImpl(DataSource dataSource) {
        this.insertMeal = new SimpleJdbcInsert(dataSource)
                .withTableName("MEALS")
                .usingGeneratedKeyColumns("id");
    }


    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        Objects.requireNonNull(userMeal);

        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", userMeal.getId())
                .addValue("dateTime", userMeal.getDateTime())
                .addValue("description", userMeal.getDescription())
                .addValue("calories", userMeal.getCalories())
                .addValue("userId", userId);

        if (userMeal.isNew()) {
            Number newKey = insertMeal.executeAndReturnKey(map);
            userMeal.setId(newKey.intValue());
        } else {
            if (namedParameterJdbcTemplate.update(
                    "UPDATE meals SET id=:id, datetime=:dateTime, " +
                            "description=:description, calories=:calories WHERE id=:id AND user_id=:userId", map
            ) == 0) {
                return null;
            }

        }
        return userMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=? AND user_id=?", id, userId) != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {

        List<UserMeal> userMeals = jdbcTemplate.query("SELECT * FROM meals WHERE user_id=? and id=?", ROW_MAPPER, userId, id);
        return DataAccessUtils.singleResult(userMeals);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        List<UserMeal> userMeals = jdbcTemplate.query("SELECT  * FROM meals WHERE user_id=? ORDER BY datetime DESC", ROW_MAPPER, userId);
        return userMeals == null ? Collections.emptyList() : userMeals;
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(endDate);

        List<UserMeal> userMeals = jdbcTemplate.query("SELECT  * FROM meals WHERE user_id=? and datetime BETWEEN ? and ? ORDER BY datetime DESC"
                , ROW_MAPPER, userId, Timestamp.valueOf(startDate), Timestamp.valueOf(endDate));
        return userMeals == null ? Collections.emptyList() : userMeals;
    }
}
