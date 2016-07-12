package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Controller
public class UserMealRestController extends AbstractUserMealController {

    @RequestMapping(value = "/meals", method = RequestMethod.POST)
    public String save(HttpServletRequest request) {
        final UserMeal userMeal = new UserMeal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        if (request.getParameter("id").isEmpty()) {
            super.create(null);
        } else {
            super.update(userMeal, getId(request));
        }
        return "redirect:meals";
    }

    @RequestMapping(value = "/meals", params = {"action=delete", "id"}, method = RequestMethod.GET)
    public String delete(@RequestParam("id") Integer i) {
        int id = i;
        super.delete(id);
        return "redirect:meals";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String getAll(Model model) {
        List<UserMealWithExceed> list = super.getAll();
        model.addAttribute("mealList", list);
        return "mealList";
    }

    @RequestMapping(value = "/meals", params = "action=update", method = RequestMethod.GET)
    public ModelAndView update(HttpServletRequest request) {
        int id = Integer.valueOf(request.getParameter("id"));
        UserMeal meal = super.get(id);
        ModelAndView modelAndView = new ModelAndView("mealEdit");
        modelAndView.addObject("meal", meal);
        return modelAndView;
    }

    @RequestMapping(value = "/meals", params = "action=create", method = RequestMethod.GET)
    public ModelAndView create() {
        UserMeal meal = new UserMeal(LocalDateTime.now().withNano(0).withSecond(0), " ", 1000);
        meal = super.create(meal);
        ModelAndView modelAndView = new ModelAndView("mealEdit");
        modelAndView.addObject("meal", meal);
        return modelAndView;
    }


    @RequestMapping(value = "/meals", params = "action=filter", method = RequestMethod.POST)
    public ModelAndView getBetween(HttpServletRequest request) {
        LocalDate startDate = TimeUtil.parseLocalDate(resetParam("startDate", request));
        LocalDate endDate = TimeUtil.parseLocalDate(resetParam("endDate", request));
        LocalTime startTime = TimeUtil.parseLocalTime(resetParam("startTime", request));
        LocalTime endTime = TimeUtil.parseLocalTime(resetParam("endTime", request));
        List<UserMealWithExceed> list = super.getBetween(startDate, startTime, endDate, endTime);
        ModelAndView modelAndView = new ModelAndView("mealList");
        modelAndView.addObject("mealList", list);
        return modelAndView;
    }

    private String resetParam(String param, HttpServletRequest request) {
        String value = request.getParameter(param);
        request.setAttribute(param, value);
        return value;
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}