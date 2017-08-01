package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MealService {
    Meal save(Meal meal,int userId) throws NotFoundException;

    void delete(int id, int userId) throws NotFoundException;

    Meal get(int id,int userId) throws NotFoundException;

    void update(Meal meal,int userId) throws NotFoundException;

    List<Meal> getAll(int userId);

    List<Meal> getFilteredList(LocalDate beginDate, LocalTime beginTime, LocalDate endDate, LocalTime endTime, String name, int userId);
}