package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User userById   = em.find(User.class, userId); // paranoic mode - meal record could been deleted before update
        if (userById == null)
            return null;

        if (!meal.isNew()) {
            Meal oldMeal = get(meal.getId(), userId);
            if (oldMeal == null)
                return null;
            else
                meal.setUser(userById);
            return em.merge(meal);
        } else
            meal.setUser(userById);
            em.persist(meal);
            return meal;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        User user = em.find(User.class, userId);        // validate user before delete
        return user != null && user.getId()==userId &&
                em.createNamedQuery(Meal.DELETE)
                        .setParameter("id", id)
                        .setParameter("userId", userId)
                        .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = em.find(Meal.class, id);
        return meal.getUser().getId()==userId ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.ALL_SORTED, Meal.class)
                .setParameter("userId",userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(Meal.FILTERED, Meal.class)
                .setParameter("userId",userId)
                .setParameter("dateStart", startDate)
                .setParameter("dateEnd", endDate)
                .getResultList();
    }
}