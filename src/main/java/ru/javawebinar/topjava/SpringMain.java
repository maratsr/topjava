package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email", "password", Role.ROLE_ADMIN));
            adminUserController.create(new User(null, "John Doe", "john@gmail.com", "john", Role.ROLE_USER));
            adminUserController.create(new User(null, "John Doe", "john2@gmail.com", "john2", Role.ROLE_USER));
            adminUserController.create(new User(null, "Ganzafar Mamedovich", "ganzafar@gmail.com", "ganzafar", Role.ROLE_USER));
            adminUserController.create(new User(null, "Anna Maria Bernardini", "anna@gmail.com", "anna", Role.ROLE_USER));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            System.out.println("test MealRestController, size list=" + mealRestController.getAll().size());
        }
    }
}
