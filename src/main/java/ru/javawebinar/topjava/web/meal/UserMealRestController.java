package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.to.UserMealWithExceed;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController {

    @Autowired
    private UserMealService service;

    public List<UserMealWithExceed> getAll(){
        return UserMealsUtil.getWithExceeded(service.getAll(LoggedUser.getId()), LoggedUser.getCaloriesPerDay());
    }

    public List<UserMealWithExceed> getFiltered(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime){
        return getAll().stream()
                .filter((um) -> TimeUtil.isBetweenDate(um.getDateTime().toLocalDate(), (startDate == null ? LocalDate.MIN : startDate), (endDate == null ? LocalDate.MAX : endDate)))
                .filter((um) -> TimeUtil.isBetweenTime(um.getDateTime().toLocalTime(), (startTime == null ? LocalTime.MIN : startTime), (endTime == null ? LocalTime.MAX : endTime)))
                .collect(Collectors.toList());
    }

    public UserMeal get(int id) throws NotFoundException {
        return service.get(LoggedUser.getId(), id);
    }

    public void delete(int id) throws NotFoundException {
        service.delete(LoggedUser.getId(), id);
    }

    public void save(UserMeal userMeal) throws NotFoundException {
        service.save(LoggedUser.getId(), userMeal);
    }
}
