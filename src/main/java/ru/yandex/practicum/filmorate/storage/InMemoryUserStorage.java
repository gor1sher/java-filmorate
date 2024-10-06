package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();

    public Collection<User> findAll() {
        return users.values();
    }

    public User userByIdentifier(Long userId) {
        checkIdentifier(userId);
        return users.get(userId);
    }

    @Override
    public User create(User user) {
        validateCreate(user);

        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        user.setId(getNextId());
        user.setFriends(new ArrayList<>());
        users.put(user.getId(), user);

        return user;
    }

    @Override
    public User update(User newUser) {
        validateUpdate(newUser);
        return changingOldDataToNewOnes(newUser);
    }

    @Override
    public void remove(User user) {

    }

    public void checkIdentifier(Long userId) {
        User user = users.get(userId);
        if (user == null) {
            throw new NotFoundException("Указан неверный идентификатор");
        }
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

    private void validateCreate(User user) {
        checkEmail(user);
        checkingObjectCriteria(user);
    }

    private void validateUpdate(User user) {
        if (!users.containsKey(user.getId())) {
            log.error("ошибка: пользователь не найден id: {}", user.getId());
            throw new NotFoundException("Пользователь не найден");
        }

        checkEmail(user);
        checkingObjectCriteria(user);
    }

    private void checkingObjectCriteria(User user) {
        if (user.getBirthday().isAfter(LocalDate.now())
                || (user.getLogin() == null)) {

            log.error("ошибка: данные регистрации пользователя неверные id: {}", user.getId());
            throw new ConditionsNotMetException("Не выполнены условия для регистрации пользователя");
        }
    }

    private void checkEmail(User user) {
        if ((user.getEmail() == null || user.getEmail().isBlank())
                || (!user.getEmail().contains("@"))) {

            log.error("ошибка: email не указан либо некорректно введен");
            throw new ConditionsNotMetException("Email не указан либо некорректно введен");
        }
    }

    private User changingOldDataToNewOnes(User newUser) {
        User oldUser = users.get(newUser.getId());

        if (newUser.getName() == null) {
            oldUser.setName(newUser.getLogin());
        } else {
            oldUser.setName(newUser.getName());
        }

        oldUser.setEmail(newUser.getEmail());
        oldUser.setLogin(newUser.getLogin());
        oldUser.setBirthday(newUser.getBirthday());

        return oldUser;
    }
}

