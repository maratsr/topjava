package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);

    {
        MealsUtil.MEALS.forEach(meal -> this.save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {}", meal);
        if (meal == null)
            return null;

        if (meal.isNew())
            meal.setId(counter.incrementAndGet());
        meal.setUserId(userId);

        return repository.put(meal.getId(), meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete meal {}", id);
        Meal meal = repository.get(id);
        return meal!=null && repository.remove(id)!=null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get meal {}", id);
        Meal meal= repository.get(id);
        if (meal == null || meal.getUserId() != userId)
            return null;
        return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("get meal for user {}", userId);
        return repository.values().stream()
                .filter(meal -> (meal.getUserId()==userId))
                .sorted(Comparator.comparing(Meal::getDateTime))
                .collect(Collectors.toList());
    }


    @Override
    public List<Meal> getFilteredList(LocalDate beginDate, LocalTime beginTime, LocalDate endDate, LocalTime endTime, String name, int userId) {
        log.info(String.format("get list of meal in ([%s]:{%s},[%s]:{%s}) for user [%d] descr mask is {%s}", beginDate, beginTime,
                endDate, endTime, userId, name));
        return repository.values().stream() // Can be collapsed in 1 filter but 4 is more clear
                .filter(meal -> (meal.getUserId() == userId))
                .filter(meal -> (DateTimeUtil.isBetween(meal.getDate(), beginDate, endDate)))
                .filter(meal -> (DateTimeUtil.isBetween(meal.getTime(), beginTime, endTime)))
                .filter(meal -> (meal.getDescription().toLowerCase().contains(name.toLowerCase())))
                .sorted(Comparator.comparing(Meal::getDateTime, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
}

