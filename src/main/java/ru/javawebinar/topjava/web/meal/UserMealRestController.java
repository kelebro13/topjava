package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.to.UserMealWithExceed;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController {

    @Autowired
    private UserMealService service;

    public List<UserMealWithExceed> getAll(){
        return service.getAll(LoggedUser.getId());
    }

    public List<UserMealWithExceed> getFiltered(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime){
        if(startDate == null) startDate = LocalDate.MIN;
        if(endDate == null) endDate = LocalDate.MAX;
        if(startTime == null) startTime = LocalTime.MIN;
        if(endTime == null) endTime = LocalTime.MAX;
        return service.getFiltered(LoggedUser.getId(), startDate, startTime, endDate, endTime);
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
