package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */

public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private UserMealRestController controller;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            controller = appCtx.getBean(UserMealRestController.class);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (request.getParameterMap().containsKey("startDate")) {
            LocalDate startDate = null;
            if(!request.getParameter("startDate").isEmpty()){
                startDate = TimeUtil.getDate(request.getParameter("startDate"));
            }
            LocalDate endDate = null;
            if(!request.getParameter("endDate").isEmpty()){
                endDate = TimeUtil.getDate(request.getParameter("endDate"));
            }
            LocalTime startTime = null;
            if(!request.getParameter("startTime").isEmpty()){
                startTime = TimeUtil.getTime(request.getParameter("startTime"));
            }
            LocalTime endTime = null;
            if(!request.getParameter("endTime").isEmpty()){
                endTime = TimeUtil.getTime(request.getParameter("endTime"));
            }
            request.setAttribute("mealList", controller.getFiltered(startDate, startTime, endDate, endTime));
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        }


        String id = request.getParameter("id");
        UserMeal userMeal = new UserMeal(id.isEmpty() ? null : Integer.valueOf(id),
                TimeUtil.getDateTime(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        LOG.info(userMeal.isNew() ? "Create {}" : "Update {}", userMeal);
        controller.save(userMeal);
        response.sendRedirect("meals");
    }

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            LOG.info("getAll");
            request.setAttribute("mealList", controller.getAll());
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            int getId = getId(request);
            LOG.info("Delete {}", getId);
            controller.delete(getId);
            response.sendRedirect("meals");
        } else if (action.equals("create") || action.equals("update")) {
            final UserMeal meal = action.equals("create") ?
                    new UserMeal(LocalDateTime.now().withNano(0).withSecond(0), "", 1000) :
                    controller.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

    @Override
    public void destroy() {
        ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        appCtx.close();
        super.destroy();
    }
}
