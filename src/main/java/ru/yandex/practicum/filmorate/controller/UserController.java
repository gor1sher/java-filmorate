package ru.yandex.practicum.filmorate.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    @NonNull
    protected UserService userService;

    @GetMapping
    public Collection<User> findAll() {
        log.info("получение всех пользователей");
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User userById(@PathVariable(name = "id") Long id) {
        log.info("получение фильма по идентификатору id: {}", id);

        return userService.userByIdentifier(id);
    }

    @GetMapping("/{id}/friends")
    public List<User> listОfAllUserFriends(@PathVariable(name = "id") Long id) {
        return userService.getUserFriend(id);
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public List<User> listOfCommonFriends(@PathVariable(name = "id") Long id, @PathVariable(name = "otherId") Long otherId) {
        return userService.commonOfFriends(id, otherId);
    }

    @PostMapping
    public User create(@RequestBody User newUser) {
        log.info("занесение пользователя");

        validateUserCreation(newUser);
        User user = userService.create(newUser);

        log.info("успешное создание пользователя id: {}", user.getId());
        return user;
    }

    @PutMapping
    public User update(@RequestBody User newUser) {
        log.info("обновление данных пользователя id: {}", newUser.getId());

        validateUpdate(newUser);
        User user = userService.update(newUser);

        log.info("успешное обновление данных пользователя id: {}", user.getId());
        return user;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable(name = "id") Long id, @PathVariable(name = "friendId") Long friendId) {
        log.info("добавление друга к пользователю id: {}", id);

        userService.addFriend(id, friendId);

        log.info("успешное добавление друга к пользователю id: {}", id);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable(name = "id") Long id, @PathVariable(name = "friendId") Long friendId) {
        log.info("удаление друга у пользователя id: {}", id);

        userService.removeFriend(id, friendId);

        log.info("успешное удаление друга у пользователю id: {}", id);
    }

    private void validateUpdate(User user) {
        userService.checkingUserInStorage(user);

        checkEmail(user);
        validateUserCriteria(user);
    }

    private void validateUserCriteria(User user) {
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

    private void validateUserCreation(User user) {
        checkEmail(user);
        validateUserCriteria(user);
    }
}

