package ru.javawebinar.topjava.service.impl;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles(profiles = {"datajpa","postgres"})
public class UserServiceTestForDataJpaPostgress extends UserServiceTest {
}
