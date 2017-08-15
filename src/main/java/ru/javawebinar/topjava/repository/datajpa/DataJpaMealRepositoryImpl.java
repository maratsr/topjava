package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {
    private static final Sort SORT_NAME_EMAIL = new Sort(Sort.Direction.DESC,"date_time");

    @Autowired
    private CrudMealRepository crudRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Meal save(Meal meal, int userId) {
        if (!meal.isNew() && get(meal.getId(), userId) == null)
            return null;
        meal.setUser(userRepository.get(userId));
        return crudRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.delete(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        //return crudRepository.findOne(id);
        List<Meal> meals = crudRepository.findByUserAndId(userRepository.get(userId), id);
        return meals.size()==1 ? meals.get(0) : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.findByUserOrderByDateTimeDesc(userRepository.get(userId));
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudRepository.findByUserAndDateTimeBetweenOrderByDateTimeDesc(userRepository.get(userId), startDate, endDate);
    }
}
