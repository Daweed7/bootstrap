package ru.kata.spring.boot_security.demo.service;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

@Service
public interface UserService extends UserDetailsService {

    User findByUsername(String username);

    User findById(Long id);

    List<User> listUsers();

    void create(User user);

    void delete(Long id);

    List<Role> getRoles();
}
