package ru.javawebinar.topjava.service.impl;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.MealServiceTest;

@ActiveProfiles(profiles = {"datajpa","postgres"})
public class MealServiceTestForDataJpaPostgress extends MealServiceTest {
}
