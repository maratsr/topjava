package ru.javawebinar.topjava.service.impl;


import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.MealServiceTest;

@ActiveProfiles(profiles = {"jdbc","postgres"})
public class MealServiceTestForJdbcPostgress extends MealServiceTest {
}
