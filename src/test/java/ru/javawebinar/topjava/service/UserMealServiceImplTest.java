package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.MealTestData.*;

/**
 * Created by Timur on 20.06.2016.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceImplTest {

    @Autowired
    private UserMealService service;

    @Autowired
    private DbPopulator populator;


    @Before
    public void setUp() throws Exception {
        populator.execute();
        resetRepository();
    }


    @Test
    public void testSave() throws Exception {
        UserMeal userMeal = new UserMeal(LocalDateTime.now(), "Чай", 25);
        UserMeal created = service.save(userMeal, ADMIN_ID);
        userMeal.setId(created.getId());
        save(userMeal, ADMIN_ID);
        MATCHER.assertCollectionEquals(getUserMealList(ADMIN_ID), service.getAll(ADMIN_ID));
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(100002, USER_ID);
        delete(100002, USER_ID);
        MATCHER.assertCollectionEquals(getUserMealList(USER_ID), service.getAll(USER_ID));
    }


    @Test(expected = NotFoundException.class)
    public void testDeleteSomeoneElseMeal() throws Exception {
        service.delete(100002, ADMIN_ID); //100002 еда User-а

    }

    @Test
    public void testGet() throws Exception {
        UserMeal userMealFromDB = service.get(100002, USER_ID);
        UserMeal userMealFromMemory = get(100002, USER_ID);
        MATCHER.assertEquals(userMealFromMemory, userMealFromDB);
    }


    @Test(expected = NotFoundException.class)
    public void testGetSomeoneElseMeal() throws Exception {
        service.get(100002, ADMIN_ID); //100002 еда User-а
    }

    @Test
    public void getAll() throws Exception {
        List<UserMeal> listFromDB = (List<UserMeal>) service.getAll(USER_ID);
        List<UserMeal> listTest = getUserMealList(USER_ID);
        MATCHER.assertCollectionEquals(listTest, listFromDB);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        LocalDateTime start = LocalDateTime.of(2016, 05, 30, 00, 00);
        LocalDateTime end = LocalDateTime.of(2016, 05, 30, 23, 59);
        List<UserMeal> listFromDB = (List<UserMeal>) service.getBetweenDateTimes(start, end, USER_ID);
        List<UserMeal> listFromMemory = getUserMealList(USER_ID).stream()
                .filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime(), start, end))
                .collect(Collectors.toList());
        MATCHER.assertCollectionEquals(listFromMemory, listFromDB);
    }

    @Test
    public void testUpdate() throws Exception {
        UserMeal userMeal = service.get(100008, ADMIN_ID);
        userMeal.setCalories(777);
        service.update(userMeal, ADMIN_ID);

        UserMeal userMeal1 = get(100008, ADMIN_ID);
        userMeal1.setCalories(777);
        save(userMeal1, ADMIN_ID);

        MATCHER.assertCollectionEquals(getUserMealList(ADMIN_ID), service.getAll(ADMIN_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateSomeoneElseMeal() throws Exception {
        UserMeal userMeal = service.get(100008, ADMIN_ID);
        service.update(userMeal, USER_ID);
    }
}