package ru.javawebinar.topjava.service.impl;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles(profiles = {"jpa","hsqldb"})
public class UserServiceTestForJpaHsqldb extends UserServiceTest {
}
