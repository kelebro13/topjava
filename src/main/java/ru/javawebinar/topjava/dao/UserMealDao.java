package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.UserMeal;

import java.util.List;

public interface UserMealDao {
    public List<UserMeal> getAllUserMeals();

    public UserMeal getUserMeal(long id);

    public void updateUserMeal(UserMeal userMeal);

    public void deleteUserMeal(long id);
}
