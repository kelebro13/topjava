package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import java.util.Arrays;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL2;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ActiveProfiles({Profiles.ACTIVE_DB, Profiles.DATAJPA})
public class DataJpaUserServiceTest extends UserServiceTest {

    @Test
    public void testGetUserAndMeal() throws Exception {
        User actual = service.getUserAndMeal(ADMIN_ID);
        MealTestData.MATCHER.assertCollectionEquals(Arrays.asList(ADMIN_MEAL, ADMIN_MEAL2), actual.getMeals());
    }
}
