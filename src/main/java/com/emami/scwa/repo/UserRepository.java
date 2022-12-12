package com.emami.scwa.repo;

import com.emami.scwa.model.Message;
import com.emami.scwa.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static UserRepository userRepository = null;
    private final List<User> userList = new ArrayList<>();

    private UserRepository() {
    }

    public static UserRepository getInstance() {
        if (userRepository == null)
            userRepository = new UserRepository();
        return userRepository;
    }

    public void save(User user) {
        userList.removeIf(user1 -> user1.getId().equals(user.getId()));
        userList.add(user);
    }

    public void removeUser(User user) {
        disconnectToEach(user);
        userList.removeIf(user1 -> user1.getId().equals(user.getId()));
    }

    public List<User> getAll() {
        return userList;
    }

    public User find(String id) {
        User[] user = {null};
        userList.forEach(user1 -> {
            if (user1.getId().equals(id))
                user[0] = user1;
        });
        return user[0];
    }

    public List<User> findByTypeAndStatus(String userType, User.StatusType statusType) {
        List<User> users = new ArrayList<>();
        userList.forEach(user1 -> {
            if (user1.getType().equals(userType) && user1.getStatusType().equals(statusType))
                users.add(user1);
        });
        return users;
    }
    public List<User> findByType(String userType) {
        List<User> users = new ArrayList<>();
        userList.forEach(user1 -> {
            if (user1.getType().equals(userType))
                users.add(user1);
        });
        return users;
    }

    public void connectToEach(User user1, User user2) {
        if (user1 == null || user2 == null) return;
        user1.setConnectedUser(user2);
        user2.setConnectedUser(user1);
    }

    public void disconnectToEach(User user) {
        User user2 = user.getConnectedUser();
        if (user2 == null) return;
        user.setConnectedUser(null);
        user2.setConnectedUser(null);
        userList.removeIf(user1 -> user1.getId().equals(user.getId()) || user1.getId().equals(user2.getId()));
        userList.add(user);
        userList.add(user2);
    }
}
