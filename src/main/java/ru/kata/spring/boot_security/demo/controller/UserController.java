package ru.kata.spring.boot_security.demo.controller;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = {"/"})
    public String homePage() {
        return "/index";
    }

    @GetMapping("/user")
    public String userPage(ModelMap modelMap, Principal principal) {

        User user = userService.findByUsername(principal.getName());
        modelMap.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin")
    public String showAllUsers(ModelMap modelMap, @ModelAttribute("user") User user) {

        List<User> users = userService.listUsers();
        List<Role> roles = userService.getRoles();
        modelMap.addAttribute("users", users);
        modelMap.addAttribute("userAuth", userService.getAuthenticatedUser());
        modelMap.addAttribute("roles", roles);
        modelMap.addAttribute("user", user);
        return "admin";
    }

    @PostMapping(value = {"/addUser"})
    public String create(@ModelAttribute("user") User user) {

        String username = user.getUsername();
        String password = user.getPassword();

        if (username != null && password != null) {
            userService.create(user);
        }
        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @RequestParam(required = false) List<Role> rolesList) {

        user.setRoles(rolesList);

        userService.edit(user);

        return "redirect:/admin";
    }

    @DeleteMapping(value = "/{id}")
    public String deleteUser(@PathVariable("id") Long id) {

        userService.delete(id);

        return "redirect:/admin";
    }
}
