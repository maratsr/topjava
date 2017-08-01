package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.*;
import ru.javawebinar.topjava.service.*;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet  extends HttpServlet  {
    private static final Logger log = getLogger(MealServlet.class);
    private static final int CALORIES_PER_DAY=2000;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private CrudCollection<Meal> meals = new MealServiceInMemoryImpl();

    @Override
    public void init() throws ServletException {
        MealsUtil.initValues((MealServiceInMemoryImpl) meals);
        log.debug("initialization meal list");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(new ArrayList<>(meals.readAll()), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
        req.setAttribute("mealWithExceeds", mealWithExceeds);
        req.setAttribute("formatter", DATE_TIME_FORMATTER);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
        log.debug("redirect to meal");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String param = req.getParameter("operation");

        if ("create".equals(param)) { // create
            try {
                meals.create(new Meal(LocalDateTime.parse(req.getParameter("datepart") + " " + req.getParameter("timepart"), DATE_TIME_FORMATTER),
                        req.getParameter("description"), Integer.parseInt(req.getParameter("calories"))));
                log.debug("meal has been created");
            } catch(Exception e) {
                log.error("non valid meal");
            }
            resp.sendRedirect(req.getContextPath() + "/meals");
            return;
        }

        if ("delete".equals(param)) { // delete
            try {
                meals.delete(Long.parseLong(req.getParameter("id")));
                log.debug("meal has been deleted");
            } catch(Exception e) {
                log.error("incorrect delete");
            }
            resp.sendRedirect(req.getContextPath() + "/meals");
            return;
        }

        if ("updateform".equals(param)) { // load updated values into separate form (read and pass to the form)
            try{
                Meal meal = meals.read(Long.parseLong(req.getParameter("id")));
                req.setAttribute("meal", meal);
                req.setAttribute("formatter", DATE_TIME_FORMATTER);
                req.getRequestDispatcher("/mealupdate.jsp").forward(req, resp);
                log.debug("redirect to meal update form");
            } catch(Exception e) {
                log.error("incorrect update mode");
            }
            return;
        }

        if ("updateconfirm".equals(param)) { //update confirm
            try {
                meals.update(Long.parseLong(req.getParameter("id")), new Meal(LocalDateTime.parse(req.getParameter("datepart") + " " + req.getParameter("timepart"), DATE_TIME_FORMATTER),
                        req.getParameter("description"), Integer.parseInt(req.getParameter("calories"))));
                log.debug("meal has been updated");
            } catch(Exception e) {
                log.error("incorrect update confirm");
            }
            resp.sendRedirect(req.getContextPath() + "/meals");
        }
    }
}
