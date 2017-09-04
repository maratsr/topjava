package ru.javawebinar.topjava.web.meal;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.util.MealsUtil.createWithExceed;

public class MealRestControllerTest extends AbstractControllerTest{
    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Test
    public void getAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_EXCEED.contentListMatcher(
                        createWithExceed(MEAL6, true),
                        createWithExceed(MEAL5, true),
                        createWithExceed(MEAL4, true),
                        createWithExceed(MEAL3, false),
                        createWithExceed(MEAL2, false),
                        createWithExceed(MEAL1, false)
                )));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + (MEAL1_ID+1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MEAL2));
    }

    @Test
    public void testDelete() throws Exception {
        int amountBefore = mealService.getAll(USER_ID).size();
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk());
        Assert.assertEquals(mealService.getAll(USER_ID).size(), --amountBefore);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = new Meal(MEAL1.getId(), MEAL1.getDateTime(), MEAL1.getDescription(), MEAL1.getCalories());
        updated.setDescription("Extra");
        updated.setUser(ru.javawebinar.topjava.UserTestData.USER);
        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());
        MATCHER.assertEquals(updated, mealService.get(MEAL1_ID, USER_ID));
    }


}