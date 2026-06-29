package org.example.service;

import org.example.domain.User;
import org.example.exception.InvalidPasswordException;
import org.example.exception.UserAlreadyExistsException;
import org.example.exception.UserLoginNotFoundException;
import org.example.repository.UserRepository;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String login, String password) {
        if (userRepository.findByLogin(login) != null) {
            throw new UserAlreadyExistsException("Пользователь с таким логином уже существует");
        }
        User user = new User(login, password);
        userRepository.save(user);
        return user;
    }

    public User login(String login, String password) {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new UserLoginNotFoundException("Пользователь не найден");
        }
        if (!user.getPassword().equals(password)) {
            throw new InvalidPasswordException("Неверный пароль");
        }
        return user;
    }
}
