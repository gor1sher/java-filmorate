package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.UserRepository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserDbStorage implements UserStorage {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Collection<User> findAll() {
        return List.of();
    }

    @Override
    public Optional<User> userById(Long userId) {
        checkId(userId);
        return userRepository.findById(userId);
    }

    @Override
    public User create(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        user.setListFriends(new ArrayList<>());

        return userRepository.save(user);
    }

    @Override
    public User update(User newUser) {
        return replaceOldData(newUser);
    }

    @Override
    public void checkId(Long userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("Указан неверный идентификатор");
        }
    }

    private User replaceOldData(User newUser) {
        Optional<User> oldUser = userById(newUser.getId());

        if (newUser.getName() == null) {
            oldUser.get().setName(newUser.getLogin());
        } else {
            oldUser.get().setName(newUser.getName());
        }

        oldUser.get().setEmail(newUser.getEmail());
        oldUser.get().setLogin(newUser.getLogin());
        oldUser.get().setBirthday(newUser.getBirthday());

        return userRepository.update(oldUser.get());
    }

    private long getNextId() {
        var users = userRepository.findAll();
        long currentMaxId = userRepository.findAll()
                .stream()
                .mapToLong(User::getId)
                .max()
                .orElse(0);
        long nextId = ++currentMaxId;
        log.info("создание нового id: {}", nextId);
        return nextId;
    }
}

