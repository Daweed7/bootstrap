package ru.kata.spring.boot_security.demo.controller;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    UserService userService;

    @GetMapping(value = {"/"})
    public String homePage() {
        return "index";
    }

    @GetMapping("/user")
    public String userPage(ModelMap modelMap, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        modelMap.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin")
    public String showAllUsers(ModelMap modelMap) {
        List<User> users = userService.listUsers();
        modelMap.addAttribute("users", users);
        return "admin";
    }

    @RequestMapping(value = {"/addUser"})
    public String showAddUserPage(ModelMap modelMap) {
        User users = new User();
        List<Role> roles = userService.getRoles();
        modelMap.addAttribute("users", users);
        modelMap.addAttribute("roles", roles);
        return "addUser";
    }

    @RequestMapping(value = {"/addUser"}, method = RequestMethod.POST)
    public String create(@ModelAttribute("user") User user) {

        String username = user.getUsername();
        String password = user.getPassword();

        if (username != null && password != null) {
            userService.create(user);
        }
        return "redirect:/admin";
    }

    @RequestMapping("/{id}/edit")
    public String editUser(ModelMap modelMap, @ModelAttribute("id") Long id) {

        List<Role> roles = userService.getRoles();
        modelMap.addAttribute("roles", roles);
        modelMap.addAttribute("user", userService.findById(id));
        return "edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user) {
        userService.create(user);

        return "redirect:/admin";
    }

    @RequestMapping(value = {"/{id}"}, method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable("id") Long id) {

        userService.delete(id);

        return "redirect:/admin";
    }
}
