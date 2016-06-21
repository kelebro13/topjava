package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.repository.mock.InMemoryUserMealRepositoryImpl;
import ru.javawebinar.topjava.util.TimeUtil;

import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static UserMealRepository repository = new InMemoryUserMealRepositoryImpl();


    public static void resetRepository() {

        List<UserMeal> USER_MEAL_LIST = Arrays.asList(
                new UserMeal(TimeUtil.parseLocalDateTime("2016-05-30 10:00"), "Завтрак", 500),
                new UserMeal(TimeUtil.parseLocalDateTime("2016-05-30 13:00"), "Обед", 1000),
                new UserMeal(TimeUtil.parseLocalDateTime("2016-05-30 20:00"), "Ужин", 500),
                new UserMeal(TimeUtil.parseLocalDateTime("2016-05-31 10:00"), "Завтрак", 1000),
                new UserMeal(TimeUtil.parseLocalDateTime("2016-05-31 13:00"), "Обед", 500),
                new UserMeal(TimeUtil.parseLocalDateTime("2016-05-31 20:00"), "Ужин", 510));

        List<UserMeal> ADMIN_MEAL_LIST = Arrays.asList(

                new UserMeal(TimeUtil.parseLocalDateTime("2016-06-01 14:00"), "Админ ланч", 510),
                new UserMeal(TimeUtil.parseLocalDateTime("2016-06-01 21:00"), "Админ ужин", 1500));

        repository = new InMemoryUserMealRepositoryImpl();
        USER_MEAL_LIST.forEach(um -> repository.save(um, USER_ID));
        ADMIN_MEAL_LIST.forEach(um -> repository.save(um, ADMIN_ID));
    }

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(UserMeal::toString);




}
