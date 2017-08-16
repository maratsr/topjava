package ru.javawebinar.topjava.service.impl;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles(profiles = {"datajpa","hsqldb"})
public class UserServiceTestForDataJpaHsqldb extends UserServiceTest {
}
