package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.DBUserMealDaoImpl;
import ru.javawebinar.topjava.dao.UserMealDao;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger LOG = getLogger(MealServlet.class);
    private static UserMealDao userMealDao;

    public MealServlet(){
        super();
        userMealDao = new DBUserMealDaoImpl();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("redirect to mealList");
        String action = req.getParameter("action");
        long index;
        if(action != null) {
            if (action.equals("delete")) {
                index = Long.parseLong(req.getParameter("id"));
                userMealDao.deleteUserMeal(index);
            } else if (action.equals("edit")) {
                index = Long.parseLong(req.getParameter("id"));
                req.setAttribute("userMeal", userMealDao.getUserMeal(index));
            }
        }
        req.setAttribute("mealList", UserMealsUtil.getFilteredWithExceeded(userMealDao.getAllUserMeals(), LocalTime.MIN, LocalTime.MAX, 2000));

        req.getRequestDispatcher("/mealList.jsp").forward(req, resp);

//        resp.sendRedirect("mealList.jsp");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        long id = 0;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        LocalDateTime dateTime = null;
        try {
            dateTime = LocalDateTime.parse(req.getParameter("date"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String desc = null;
        try {
            desc = req.getParameter("description");
        } catch (Exception e) {
            e.printStackTrace();
        }
        int calories = 0;
        try {
            calories = Integer.parseInt(req.getParameter("calories"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        UserMeal userMeal = new UserMeal(id, dateTime, desc, calories);
        userMealDao.updateUserMeal(userMeal);
        req.setAttribute("mealList", UserMealsUtil.getFilteredWithExceeded(userMealDao.getAllUserMeals(), LocalTime.MIN, LocalTime.MAX, 2000));
        RequestDispatcher view = req.getRequestDispatcher("/mealList.jsp");
        view.forward(req, resp);


    }
}
