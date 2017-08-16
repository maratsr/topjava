package ru.javawebinar.topjava.service.impl;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles(profiles = {"jpa","postgres"})
public class UserServiceTestForJpaPostgress extends UserServiceTest {
}
