package ru.javawebinar.topjava.service.impl;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles(profiles = {"jdbc","hsqldb"})
public class UserServiceTestForJdbcHsqldb extends UserServiceTest {
}
