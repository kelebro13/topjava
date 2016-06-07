package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.web.MealServlet;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class DBUserMealDaoImpl implements UserMealDao {

    //Map is working as a database
    private static Map<Long, UserMeal> userMealMap;
    private static final Logger LOG = getLogger(MealServlet.class);

    public DBUserMealDaoImpl() {
        userMealMap = new ConcurrentHashMap<>();
        UserMeal userMeal1 = new UserMeal(1, LocalDateTime.now(), "Завтрак", 1000);
        UserMeal userMeal2 = new UserMeal(2, LocalDateTime.now(), "Обед", 500);
        userMealMap.put(userMeal1.getId(), userMeal1);
        userMealMap.put(userMeal2.getId(), userMeal2);
        LOG.debug("create map");

    }

    @Override
    public void deleteUserMeal(long id) {
        userMealMap.remove(id);
        LOG.debug("delete UserMeal " + id);
    }

    @Override
    public  List<UserMeal> getAllUserMeals() {
        LOG.debug("get List all usermeals");
        return userMealMap.entrySet().stream().sorted((d1,d2) -> d1.getValue().getDateTime().compareTo(d2.getValue().getDateTime())).map(Map.Entry::getValue).collect(Collectors.toList());
    }

    @Override
    public UserMeal getUserMeal(long id) {
        LOG.debug("get UserMeal " + id);
        return userMealMap.get(id);
    }

    @Override
    public void updateUserMeal(UserMeal userMeal) {
        LOG.debug("update UserMeal " + userMeal.getId());
        userMealMap.put(userMeal.getId(), userMeal);
    }
}
