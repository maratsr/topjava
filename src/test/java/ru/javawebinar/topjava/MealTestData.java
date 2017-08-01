package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.BeanMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

public class MealTestData {

    public static final Meal MEAL_BREAKFAST_24  = new Meal(START_SEQ+2, LocalDateTime.of(2017, Month.JULY, 24, 06, 0), "завтрак", 500);
    public static final Meal MEAL_LUNCH_24  = new Meal(START_SEQ+3, LocalDateTime.of(2017, Month.JULY, 24, 13, 0), "обед", 1000);
    public static final Meal MEAL_DINNER_24  = new Meal(START_SEQ+4, LocalDateTime.of(2017, Month.JULY, 24, 18, 0), "ужин", 600);
    public static final Meal MEAL_BREAKFAST_25  = new Meal(START_SEQ+5, LocalDateTime.of(2017, Month.JULY, 25, 06, 0), "завтрак", 400);
    public static final Meal MEAL_LUNCH_25  = new Meal(START_SEQ+6, LocalDateTime.of(2017, Month.JULY, 25, 13, 0), "обед", 800);
    public static final Meal MEAL_DINNER_25  = new Meal(START_SEQ+7, LocalDateTime.of(2017, Month.JULY, 25, 18, 0), "ужин", 500);

    public static final BeanMatcher<Meal> MATCHER = new BeanMatcher<>(
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getDateTime(), actual.getDateTime())
                            && Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getCalories(), actual.getCalories())
                            && Objects.equals(expected.getDescription(), actual.getDescription())));
}
