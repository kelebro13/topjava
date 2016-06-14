package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */
public interface UserMealService {

    UserMeal save(int userId, UserMeal userMeal) throws NotFoundException;

    void delete(int userId, int id) throws NotFoundException;

    UserMeal get(int userId, int id) throws NotFoundException;

    List<UserMeal> getAll(int userId, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime);
}
