package ru.javawebinar.topjava.web;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class RootControllerTest extends AbstractControllerTest {

    @Test
    public void testUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
                .andExpect(model().attribute("users", hasSize(2)))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("id", is(START_SEQ)),
                                hasProperty("name", is(USER.getName()))
                        )
                )));
    }

    @Test
    public void testMeals() throws Exception {
        mockMvc.perform(get("/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("meals"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/meals.jsp"))
                .andExpect(model().attribute("meals", hasSize(6)))
                .andExpect(model().attribute("meals", hasItems(
                        both(
                                hasProperty("id", is(MEAL1.getId())))
                                .and(
                                        hasProperty("description", is(MEAL1.getDescription()))
                                ),
                        both(
                                hasProperty("id", is(MEAL2.getId())))
                                .and(
                                        hasProperty("calories", is(MEAL2.getCalories()))
                                ),
                        both(
                                hasProperty("id", is(MEAL3.getId())))
                                .and(
                                        hasProperty("calories", is(MEAL4.getCalories()))
                                )
                                .and(
                                        hasProperty("exceed", is(false))),

                        hasProperty("id", is(MEAL4.getId())),
                        hasProperty("id", is(MEAL5.getId())),
                        hasProperty("id", is(MEAL6.getId()))
                )))
        ;
    }
}