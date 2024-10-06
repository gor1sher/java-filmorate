package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
    UserService userService = new UserService();

    @GetMapping
    public Collection<User> findAll() {
        log.info("получение всех пользователей");
        return inMemoryUserStorage.findAll();
    }

    @GetMapping("/{id}")
    public User userByIdentifier(@RequestParam Long id){
        log.info("получение фильма по идентификатору id: {}", id);

        return inMemoryUserStorage.userByIdentifier(id);
    }

    @GetMapping("/{id}/friends")
    public List<User> listОfAllUserFriends(@RequestParam Long id){
        return userService.listОfAllUserFriends(id);
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public List<User> listOfCommonFriends(@RequestParam Long id, @RequestParam Long otherId){
        return userService.commonOfFriends(id, otherId);
    }

    @PostMapping
    public User create(@RequestBody User newUser) {
        log.info("занесение пользователя");

        User user = inMemoryUserStorage.create(newUser);

        log.info("успешное создание пользователя id: {}", user.getId());
        return user;
    }

    @PutMapping
    public User update(@RequestBody User newUser) {
        log.info("обновление данных пользователя id: {}", newUser.getId());

        User user = inMemoryUserStorage.update(newUser);

        log.info("успешное обновление данных пользователя id: {}", user.getId());
        return user;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@RequestParam Long id, @RequestParam Long friendId){
        log.info("добавление друга к пользователю id: {}", id);

        userService.addFriend(id, friendId);

        log.info("успешное добавление друга к пользователю id: {}", id);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@RequestParam Long id, @RequestParam Long friendId){
        log.info("удаление друга у пользователя id: {}", id);

        userService.removeFriend(id, friendId);

        log.info("успешное удаление друга у пользователю id: {}", id);
    }
}

