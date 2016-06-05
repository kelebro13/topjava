package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Timur on 04.06.2016.
 */
public class MealServlet extends HttpServlet {

    private static final Logger LOG = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("redirect to mealList");

        req.setAttribute("mealList", UserMealsUtil.getFilteredWithExceeded(UserMealsUtil.getMealList(), LocalTime.MIN, LocalTime.MAX, 2000));
        req.getRequestDispatcher("/mealList.jsp").forward(req, resp);

//        resp.sendRedirect("mealList.jsp");

    }
}
