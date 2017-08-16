package ru.javawebinar.topjava.service.impl;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.MealServiceTest;

@ActiveProfiles(profiles = {"jpa","hsqldb"})
public class MealServiceTestJpaHsqldb extends MealServiceTest {
}
