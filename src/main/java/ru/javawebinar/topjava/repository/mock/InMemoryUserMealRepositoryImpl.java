package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.to.UserMealWithExceed;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {

    private Map<Integer, Map<Integer, UserMeal>> repository = new ConcurrentHashMap<>();

    private AtomicInteger counter = new AtomicInteger(0);
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserMealRepositoryImpl.class);

    {
        List<UserMeal> MEAL_LIST1 = asList(
                new UserMeal(LocalDateTime.of(2016, Month.JUNE, 5, 10, 0), "Завтрак1", 500),
                new UserMeal(LocalDateTime.of(2016, Month.JUNE, 5, 13, 0), "Обед1", 1000),
                new UserMeal(LocalDateTime.of(2016, Month.JUNE, 5, 20, 0), "Ужин1", 500),
                new UserMeal(LocalDateTime.of(2016, Month.JUNE, 8, 10, 0), "Завтрак1", 1000),
                new UserMeal(LocalDateTime.of(2016, Month.JUNE, 8, 13, 0), "Обед1", 500),
                new UserMeal(LocalDateTime.of(2016, Month.JUNE, 8, 20, 0), "Ужин1", 510)
        );

        List<UserMeal> MEAL_LIST2 = asList(
                new UserMeal(LocalDateTime.of(2016, Month.JUNE, 2, 10, 0), "Завтрак2", 600),
                new UserMeal(LocalDateTime.of(2016, Month.JUNE, 2, 13, 0), "Обед2", 900),
                new UserMeal(LocalDateTime.of(2016, Month.JUNE, 2, 20, 0), "Ужин2", 400),
                new UserMeal(LocalDateTime.of(2016, Month.JUNE, 3, 10, 0), "Завтрак2", 900),
                new UserMeal(LocalDateTime.of(2016, Month.JUNE, 3, 13, 0), "Обед2", 400),
                new UserMeal(LocalDateTime.of(2016, Month.JUNE, 3, 20, 0), "Ужин2", 610)
        );

        MEAL_LIST1.stream().forEach(u -> save(1, u));
        MEAL_LIST2.stream().forEach(u -> save(2, u));
    }

    @Override
    public UserMeal save(int userId, UserMeal userMeal) {
        if(!repository.containsKey(userId)){
            repository.put(userId, new ConcurrentHashMap<>());
        }

        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
            repository.get(userId).put(userMeal.getId(), userMeal);
        }

        if (!repository.get(userId).containsKey(userMeal.getId())) {
            return null;
        }
        repository.get(userId).put(userMeal.getId(), userMeal);
        LOG.info("save " + userMeal);
        return userMeal;
    }

    @Override
    public boolean delete(int userId, int id) {

        if (!repository.get(userId).containsKey(id)) {
            return false;
        }

        try {
            repository.get(userId).remove(id);
            LOG.info("delete " + id);
            return true;
        } catch (Exception e) {
            LOG.info("Can't delete " + id);
            return false;
        }
    }

    @Override
    public UserMeal get(int userId, int id) {
        if (!repository.get(userId).containsKey(id)) {
            return null;
        }
        try {
            UserMeal userMeal = repository.get(userId).get(id);
            LOG.info("get " + id);
            return userMeal;
        } catch (Exception e) {
            LOG.info("Can't get " + id);
            return null;
        }

    }

    @Override
    public List<UserMealWithExceed> getAll(int userId) {
        LOG.info("getAll");
        List<UserMealWithExceed> list = UserMealsUtil.getWithExceeded(repository.get(userId).values().stream().collect(Collectors.toList()), LoggedUser.getCaloriesPerDay());
        if(list.isEmpty()){
            return Collections.emptyList();
        }else {
            return list;
        }
    }

    @Override
    public List<UserMealWithExceed> getFiltered(int userId, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        LOG.info("getAll");
        List<UserMealWithExceed> list = getAll(userId)
                .stream()
                .filter((um) -> TimeUtil.isBetweenDate(um.getDateTime().toLocalDate(), startDate, endDate))
                .filter((um) -> TimeUtil.isBetweenTime(um.getDateTime().toLocalTime(), startTime, endTime))
                .sorted((um1, um2) -> um2.getDateTime().compareTo(um1.getDateTime()))
                .collect(Collectors.toList());
        if(list.isEmpty()){
            return Collections.emptyList();
        }else {
            return list;
        }
    }
}

