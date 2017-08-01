package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.service.MealServiceInMemoryImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class MealsUtil {
    public static void initValues(MealServiceInMemoryImpl meals) {
        for(int i=0; i<12; i++)
            meals.create(new Meal(LocalDateTime.of(2015, Month.MAY, 1 + i/3, 6 + 6*(i%3), ThreadLocalRandom.current().nextInt(30)),
                    i%3 == 0? "Завтрак": i%3 == 1? "Обед" : "Ужин", 600 + ThreadLocalRandom.current().nextInt(150)));
    }

    public static List<MealWithExceed> getFilteredWithExceeded(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );

        return meals.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .map(meal ->
                        new MealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                                caloriesSumByDate.get(meal.getDate()) > caloriesPerDay, meal.getId()))
                .collect(Collectors.toList());
    }
}