package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(MEAL_BREAKFAST_24.getId(), USER_ID);
        MATCHER.assertEquals(MEAL_BREAKFAST_24, meal);
    }

    @Test(expected = NotFoundException.class)
    public void getOtherUserMeal() throws Exception {
        service.get(MEAL_BREAKFAST_24.getId(), ADMIN_ID);
    }

    @Test
    public void delete() throws Exception {
        int i = service.getAll(USER_ID).size();
        service.delete(MEAL_BREAKFAST_24.getId(), USER_ID);
        assertEquals(--i, service.getAll(USER_ID).size());
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(MEAL_BREAKFAST_24.getId(), ADMIN_ID);
    }

    @Test
    public void getBetweenDates() throws Exception {
        List<Meal> filtered = service.getBetweenDates(LocalDate.of(2017, Month.JULY, 25),
            LocalDate.of(2017, Month.JULY, 25), USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL_DINNER_25, MEAL_LUNCH_25, MEAL_BREAKFAST_25), filtered);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        List<Meal> filtered = service.getBetweenDateTimes(LocalDateTime.of(2017, Month.JULY, 25, 10, 0),
                LocalDateTime.of(2017, Month.JULY, 26, 10, 0), USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL_DINNER_25, MEAL_LUNCH_25), filtered);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> all = service.getAll(USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL_DINNER_25, MEAL_LUNCH_25, MEAL_BREAKFAST_25,
                MEAL_DINNER_24, MEAL_LUNCH_24, MEAL_BREAKFAST_24), all);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(MEAL_BREAKFAST_25.getId(), MEAL_BREAKFAST_25.getDateTime(), MEAL_BREAKFAST_25.getDescription(), MEAL_BREAKFAST_25.getCalories());
        updated.setDescription("UpdatedName");
        updated.setCalories(333);
        service.update(updated, USER_ID);
        MATCHER.assertEquals(updated, service.get(MEAL_BREAKFAST_25.getId(), USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void updateOtherUserMeal() throws Exception {
        Meal updated = new Meal(MEAL_BREAKFAST_25.getId(), MEAL_BREAKFAST_25.getDateTime(), MEAL_BREAKFAST_25.getDescription(), MEAL_BREAKFAST_25.getCalories());
        updated.setDescription("UpdatedName2");
        updated.setCalories(555);
        service.update(updated, ADMIN_ID);
        MATCHER.assertEquals(updated, service.get(MEAL_BREAKFAST_25.getId(), USER_ID));
    }

    @Test
    public void save() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510);
        Meal created = service.save(newMeal, USER_ID);
        newMeal.setId(created.getId());
        MATCHER.assertEquals(created, newMeal);
    }

}