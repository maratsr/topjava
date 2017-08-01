package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.*;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController mealRestController;
    private ConfigurableApplicationContext appCtx;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try(ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            mealRestController = appCtx.getBean(MealRestController.class);
            log.debug("get MealRestController = " + mealRestController);
        }
    }

    @Override
    public void destroy() {
        appCtx.close();
        super.destroy();
    }

    private void setParam(HttpServletRequest request, boolean insertMeals) {
        String paramSearch    = request.getParameter("mealsearchtmpl");
        String paramBeginDate = request.getParameter("datepartbegin");
        String paramBeginTime = request.getParameter("timepartbegin");
        String paramEndDate   = request.getParameter("datepartend");
        String paramEndTime   = request.getParameter("timepartend");

        LocalDate beginDate = !paramBeginDate.isEmpty()? LocalDate.parse(paramBeginDate, DATE_FORMATTER) : MIN_DATE;
        LocalDate endDate   = !paramEndDate.isEmpty()  ? LocalDate.parse(paramEndDate,   DATE_FORMATTER) : MAX_DATE;
        LocalTime beginTime = !paramBeginTime.isEmpty()? LocalTime.parse(paramBeginTime, TIME_FORMATTER) : LocalTime.MIN;
        LocalTime endTime   = !paramEndTime.isEmpty()  ? LocalTime.parse(paramEndTime,   TIME_FORMATTER) : LocalTime.MAX;

        if (insertMeals)
            request.setAttribute("meals",
                mealRestController.getWithExceeded(beginDate, beginTime, endDate, endTime, paramSearch));

        if (beginDate != null && !beginDate.equals(MIN_DATE))
            request.setAttribute("begindate", beginDate.format(DATE_FORMATTER));
        if (endDate != null && !endDate.equals(MAX_DATE))
            request.setAttribute("enddate", endDate.format(DATE_FORMATTER));
        if (!beginTime.equals(LocalTime.MIN))
            request.setAttribute("begintime", beginTime.format(TIME_FORMATTER));
        if (!endTime.equals(LocalTime.MAX))
            request.setAttribute("endtime", endTime.format(TIME_FORMATTER));
        if (!paramSearch.isEmpty())
            request.setAttribute("mealsearchtmpl", paramSearch);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String operation = request.getParameter("operation");

        if (request.getParameterMap().get("delete") != null) operation = "delete";
        else if (request.getParameterMap().get("update") != null) operation = "update";
        else if (request.getParameterMap().get("insert") != null) operation = "insert";
        else if (request.getParameterMap().get("dosearch") != null) operation = "search";

        switch(operation == null ? "modify" : operation) {
            case "search": // filter
                log.info("search");
                setParam(request, true);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;

            case "delete":
                log.info("Delete {}", request.getParameter("id"));
                mealRestController.delete(Integer.valueOf(request.getParameter("delete")));
                setParam(request, true);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;

            case "update":
                Meal meal =  mealRestController.get(Integer.valueOf(request.getParameter("update")));
                log.info("update {}", meal);
                request.setAttribute("meal", meal);
                setParam(request, false);
                request.getRequestDispatcher("/meal.jsp").forward(request, response);
                break;

            case "insert":
                meal =  new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000,
                        0);
                log.info("insert {}", meal);
                request.setAttribute("meal", meal);
                setParam(request, false);
                request.getRequestDispatcher("/meal.jsp").forward(request, response);
                break;

            case "modify": // update/insert meal
            default:
                String id = request.getParameter("id");
                meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                        LocalDateTime.parse(request.getParameter("dateTime")),
                        request.getParameter("description"),
                        Integer.valueOf(request.getParameter("calories")),
                        0);

                log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
                mealRestController.save(meal);
                setParam(request, true);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("getAll");
        request.setAttribute("meals",
                mealRestController.getWithExceeded(MIN_DATE, LocalTime.MIN, MAX_DATE,LocalTime.MAX,""));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
