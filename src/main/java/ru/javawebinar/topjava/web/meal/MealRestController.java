package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller // Annotation using example (without spring-app.xml description)
public class MealRestController {

    private MealService service;

    private AuthorizedUser authorizedUser;

    @Autowired
    private void setAuthorizedUser(AuthorizedUser authorizedUser) {
        this.authorizedUser = authorizedUser;
    }

    @Autowired
    private void setService(MealService service) {
        this.service = service;
    }

    public Meal save(Meal meal) throws NotFoundException {
        return service.save(meal, authorizedUser.id());
    }

    public void delete(int id) throws NotFoundException {
        service.delete(id, authorizedUser.id());
    }

    public Meal get(int id) throws NotFoundException {
        return service.get(id, authorizedUser.id());
    }

    public void update(Meal meal) throws NotFoundException {
        service.update(meal, authorizedUser.id());
    }

    public List<Meal> getAll() {
        return service.getAll(authorizedUser.id());
    }

    private List<Meal> getFilteredList(LocalDate beginDate, LocalTime beginTime, LocalDate endDate, LocalTime endTime, String name) {
        return service.getFilteredList(beginDate, beginTime, endDate, endTime, name, authorizedUser.id());
    }

    public List<MealWithExceed> getWithExceeded(LocalDate beginDate, LocalTime beginTime, LocalDate endDate, LocalTime endTime, String name) {
        Map<LocalDate, Integer> caloriesSumByDate = getAll()
                .stream().collect(Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));

        return getFilteredList(beginDate, beginTime, endDate, endTime, name).stream()
                .map(meal -> createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > authorizedUser.getCaloriesPerDay()))
                .collect(Collectors.toList());
    }

    public static MealWithExceed createWithExceed(Meal meal, boolean exceeded) {
        return new MealWithExceed(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceeded);
    }
}