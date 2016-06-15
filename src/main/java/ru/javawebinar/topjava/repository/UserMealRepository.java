package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.to.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public interface UserMealRepository {
    UserMeal save(int userId, UserMeal userMeal);

    boolean delete(int userId, int id);

    UserMeal get(int userId, int id);

    List<UserMealWithExceed> getAll(int userId);

    List<UserMealWithExceed> getFiltered(int userId, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime);
}
