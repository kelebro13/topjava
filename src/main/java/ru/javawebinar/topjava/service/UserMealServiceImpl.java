package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.to.UserMealWithExceed;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class UserMealServiceImpl implements UserMealService {

    @Autowired
    private UserMealRepository repository;

    @Override
    public void delete(int userId, int id) throws NotFoundException {
        ExceptionUtil.checkNotFoundWithId(repository.delete(userId, id), id);
    }

    @Override
    public UserMeal get(int userId, int id) throws NotFoundException {
        return ExceptionUtil.checkNotFoundWithId(repository.get(userId, id), id);
    }

    @Override
    public List<UserMealWithExceed> getAll(int userId) {
        return repository.getAll(userId);
    }

    @Override
    public List<UserMealWithExceed> getFiltered(int userId, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        return repository.getFiltered(userId, startDate, startTime, endDate, endTime);
    }

    @Override
    public UserMeal save(int userId, UserMeal userMeal) throws NotFoundException{
        return ExceptionUtil.checkNotFound(repository.save(userId, userMeal), String.valueOf(userMeal.getId()));
    }
}
