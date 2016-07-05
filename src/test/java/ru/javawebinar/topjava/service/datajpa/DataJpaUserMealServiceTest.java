package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealServiceTest;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_ID;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ActiveProfiles({Profiles.ACTIVE_DB, Profiles.DATAJPA})
public class DataJpaUserMealServiceTest extends UserMealServiceTest {

        @Test
        public void testGetUserAndMeal() throws Exception {
            UserMeal actual = service.getUserAndMeal(ADMIN_MEAL_ID, ADMIN_ID);
            UserTestData.MATCHER.assertEquals(ADMIN, actual.getUser());
        }

}
