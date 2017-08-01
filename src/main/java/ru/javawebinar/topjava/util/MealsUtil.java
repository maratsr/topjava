package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;


public class MealsUtil {
    public static final List<Meal> MEALS = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500, 1),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000, 1),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500, 1),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000, 1),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500, 1),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510, 1),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 400, 2),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1100, 2),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 540, 2),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1500, 3),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 400, 3),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 410, 3)

    );

    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

}