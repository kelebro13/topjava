package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 510),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 50)
        );
        getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);



//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mapCaloriesDay = new HashMap<>();
        List<UserMeal> userMealList  = mealList.stream().peek(u -> {
            if(mapCaloriesDay.containsKey(u.getDateTime().toLocalDate())){
                int tmp = mapCaloriesDay.get(u.getDateTime().toLocalDate());
                mapCaloriesDay.put(u.getDateTime().toLocalDate(), tmp + u.getCalories());
            }else{
                mapCaloriesDay.put(u.getDateTime().toLocalDate(), u.getCalories());
            }
        }).filter(u -> u.getDateTime().toLocalTime().isAfter(startTime) && u.getDateTime().toLocalTime().isBefore(endTime))
                .collect(Collectors.toList());

        return userMealList.stream()
                .map(u ->{
                    boolean checkCalories = false;
                    if(mapCaloriesDay.get(u.getDateTime().toLocalDate()) > caloriesPerDay) checkCalories = true;
                    return new UserMealWithExceed(u.getDateTime(), u.getDescription(), u.getCalories(), checkCalories);
                }).collect(Collectors.toList());
    }
}
