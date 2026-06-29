package org.example.repository;

import org.example.domain.User;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private Map<String, User> users = new HashMap<>();

    public void save(User user) {
        users.put(user.getLogin(), user);
    }

    public User findByLogin(String login) {
        return users.get(login);
    }
}
