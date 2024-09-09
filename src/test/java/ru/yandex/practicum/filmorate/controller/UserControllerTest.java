package ru.yandex.practicum.filmorate.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;

import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    UserController userController;

    @Test
    public void testUserCreate() {
        User user = new User();

        user.setName("log");
        user.setLogin("logger");
        user.setEmail("logger@mail.ru");
        user.setBirthday(LocalDate.now().minusYears(20));

        assertEquals(user, userController.create(user));
    }

    @Test
    public void testUserCreate_ErrorInEmail() {
        User user = new User();

        user.setName("log");
        user.setLogin("logger");
        user.setEmail("loggermail.ru");
        user.setBirthday(LocalDate.now().minusYears(20));

        assertThrows(ConditionsNotMetException.class, () -> {
            userController.create(user);
        }, "Email не указан либо некорректно введен");
    }

    @Test
    public void testUserCreate_IncorrectLoginInformation() {
        User user = new User();

        user.setName("log");
        user.setEmail("logger@mail.ru");
        user.setBirthday(LocalDate.now().minusYears(20));

        assertThrows(ConditionsNotMetException.class, () -> {
            userController.create(user);
        }, "Не выполнены условия для регистрации пользователя");
    }

    @Test
    public void testUserCreate_IncorrectBirthdayInformation() {
        User user = new User();

        user.setName("log");
        user.setLogin("logger");
        user.setEmail("logger@mail.ru");
        user.setBirthday(LocalDate.now());

        assertThrows(ConditionsNotMetException.class, () -> {
            userController.create(user);
        }, "Не выполнены условия для регистрации пользователя");
    }

    @Test
    public void testFindAll_ReturnListOfUsers() {
        User user1 = new User();

        user1.setName("log");
        user1.setLogin("logger");
        user1.setEmail("logger@mail.ru");
        user1.setBirthday(LocalDate.now().minusYears(20));

        userController.create(user1);

        User user2 = new User();

        user2.setName("run");
        user2.setLogin("runner");
        user2.setEmail("ger@mail.ru");
        user2.setBirthday(LocalDate.now().minusYears(21));

        userController.create(user2);

        Collection<User> list = userController.findAll();

        assertTrue(list.contains(user1));
        assertTrue(list.contains(user2));
    }

    @Test
    public void testUserUpdate() {
        User user1 = new User();

        user1.setName("log");
        user1.setLogin("logger");
        user1.setEmail("logger@mail.ru");
        user1.setBirthday(LocalDate.now().minusYears(20));

        User user2 = userController.create(user1);

        user2.setLogin("runner");
        user2.setBirthday(LocalDate.now().minusYears(21));

        Assert.assertEquals("runner", userController.update(user2).getLogin());
    }

    @Test
    public void testUserUpdate_EmailIsEmpty() {
        User user1 = new User();

        user1.setName("log");
        user1.setLogin("logger");
        user1.setEmail("logger@mail.ru");
        user1.setBirthday(LocalDate.now().minusYears(20));

        User user2 = userController.create(user1);

        user2.setLogin("runner");
        user2.setEmail(null);
        user2.setBirthday(LocalDate.now().minusYears(21));

        assertThrows(ConditionsNotMetException.class, () -> {
            userController.update(user2);
        }, "Некорректные данные");
    }
}
