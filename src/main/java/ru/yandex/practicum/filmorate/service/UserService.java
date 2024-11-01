package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.UserRepository;
import ru.yandex.practicum.filmorate.dal.mappers.UserRowMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserStorage inMemoryUserStorage;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRowMapper userRowMapper;

    public User create(User user) {
        return inMemoryUserStorage.create(user);
    }

    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> userByIdentifier(Long id) {
        return inMemoryUserStorage.userById(id);
    }

    public User update(User newUser) {
        return inMemoryUserStorage.update(newUser);
    }

    public void checkingUserInStorage(User user) {
        inMemoryUserStorage.checkId(user.getId());
    }

    // public Collection<User> findAll() {
    //   return inMemoryUserStorage.findAll();
    //}

    //public User userByIdentifier(Long id) {
    //     return inMemoryUserStorage.userByIdentifier(id);
    // }

    //public User create(User newUser) {
    //    return inMemoryUserStorage.create(newUser);
    // }

    // public User update(User newUser) {
    //   return inMemoryUserStorage.update(newUser);
    //}

    // public void checkingUserInStorage(User user) {
    //      inMemoryUserStorage.checkingUserInStorage(user);
    //  }

    public void addFriend(Long userId, Long friendId) {
        List<User> userList = checkAndGetUserById(userId, friendId);

        List<Long> userFriends = userList.get(0).getListFriends();
        userFriends.add(userList.get(1).getId());
        userList.get(0).setListFriends(userFriends);
        userRepository.update(userList.get(0));

        List<Long> userFriends1 = userList.get(1).getListFriends();
        userFriends1.add(userList.get(0).getId());
        userList.get(1).setListFriends(userFriends1);
        userRepository.update(userList.get(1));
    }


//    public void addFriend(Long userId, Long friendId) {
//        List<User> userList = checkAndGetUserById(userId, friendId);
//
//
//        List<Long> userFriends = userList.get(0).getListFriends();
//        userFriends.add(userList.get(1).getId());
//        userList.get(0).setListFriends(userFriends);
//
//        List<Long> userFriends1 = userList.get(1).getListFriends();
//        userFriends1.add(userList.get(0).getId());
//        userList.get(1).setListFriends(userFriends1);
//    }

    public void removeFriend(Long userId, Long friendId) {
        List<User> userList = checkAndGetUserById(userId, friendId);

        List<Long> userFriends = userList.get(0).getListFriends();
        userFriends.remove(userList.get(1).getId());
        userList.get(0).setListFriends(userFriends);
        userRepository.update(userList.get(0));

        List<Long> userFriends1 = userList.get(1).getListFriends();
        userFriends1.remove(userList.get(0).getId());
        userList.get(1).setListFriends(userFriends1);
        userRepository.update(userList.get(1));
    }

    public List<Optional<User>> getUserFriend(Long id) {
        Optional<User> user = inMemoryUserStorage.userById(id);
        return user.get().getListFriends().stream().map(friendId -> inMemoryUserStorage.userById(friendId)).toList();
    }

    public List<Optional<User>> commonOfFriends(Long userId, Long otherId) {
        List<User> userList = checkAndGetUserById(userId, otherId);
        return userList.get(0).getListFriends().stream().map(long1 -> inMemoryUserStorage.userById(long1)).toList().stream()
                .filter(friend -> userList.get(1).getListFriends().stream().map(long1 -> inMemoryUserStorage.userById(long1)).toList().stream()
                        .anyMatch(otherFriend -> otherFriend.equals(friend)))
                .collect(Collectors.toList());
    }

    //проверка на то, что User обьект может быть пустым - есть, в классе InMemoryUserStorage
    private List<User> checkAndGetUserById(Long id, Long friendId) {
        Optional<User> user = inMemoryUserStorage.userById(id);
        Optional<User> userFriend = inMemoryUserStorage.userById(friendId);

        List<User> result = new ArrayList<>();
        user.ifPresent(result::add);
        userFriend.ifPresent(result::add);

        return result;
    }
}
