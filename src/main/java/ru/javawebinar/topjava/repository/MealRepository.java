package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MealRepository {
    Meal save(Meal Meal, int userId);

    boolean delete(int id, int userId);

    Meal get(int id, int userId);

    List<Meal> getAll(int userId);

    List<Meal> getFilteredList(LocalDate beginDate, LocalTime beginTime, LocalDate endDate, LocalTime endTime, String name, int userId);
}