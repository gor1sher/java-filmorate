package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final Map<Long, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAll() {
        log.info("получение всех пользователей");
        return users.values();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        log.info("занесение пользователя id: {}", user.getId());

        checkEmail(user);

        if (user.getBirthday().isBefore(LocalDate.now())
                && ((user.getLogin() != null))) {

            if (user.getName() == null) {
                user.setName(user.getLogin());
            }
            user.setId(getNextId());

            users.put(user.getId(), user);
            log.info("успешное создание пользователя id: {}", user.getId());
            return user;
        }
        log.error("ошибка: данные регистрации пользователя неверные id: {}", user.getId());
        throw new ConditionsNotMetException("Не выполнены условия для регистрации пользователя");
    }

    @PutMapping
    public User update(@RequestBody User newUser) {
        log.info("обновление данных пользователя id: {}", newUser.getId());

        if (users.containsKey(newUser.getId())) {
            checkEmail(newUser);

            User oldUser = users.get(newUser.getId());

            if (newUser.getLogin() != null || newUser.getBirthday().isBefore(LocalDate.now())) {

                return changingOldDataToNewOnes(oldUser, newUser);
            }
            log.error("ошибка: некорректные данные id: {}", oldUser.getId());
            throw new ConditionsNotMetException("Некорректные данные");
        }
        log.error("ошибка: пользователь не найден id: {}", newUser.getId());
        throw new NotFoundException("Пользователь не найден");
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        long nextId = ++currentMaxId;
        log.info("создание нового id: {}", nextId);
        return nextId;
    }

    private void checkEmail(User user) {
        if ((user.getEmail() == null || user.getEmail().isBlank())
                || (!user.getEmail().contains("@"))) {

            log.error("ошибка: email не указан либо некорректно введен");
            throw new ConditionsNotMetException("Email не указан либо некорректно введен");
        }
    }

    private User changingOldDataToNewOnes(User oldUser, User newUser) {
        if (newUser.getName() == null) {
            oldUser.setName(newUser.getLogin());
        } else {
            oldUser.setName(newUser.getName());
        }

        oldUser.setEmail(newUser.getEmail());
        oldUser.setLogin(newUser.getLogin());
        oldUser.setBirthday(newUser.getBirthday());

        log.info("успешное обновление данных пользователя id: {}", oldUser.getId());
        return oldUser;
    }
}

