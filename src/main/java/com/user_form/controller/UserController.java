package com.user_form.controller;

import com.user_form.model.User;
import com.user_form.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    @ResponseBody
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping(value = "/user/{id}")
    @ResponseBody
    public Optional<User> findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping(value = "/user")
    @ResponseBody
    public Optional<User> save(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping(value = "/user/{id}")
    @ResponseBody
    public Optional<User> update(@RequestBody User user, @PathVariable Long id) {
        return userService.update(user, id);
    }

    @DeleteMapping(value = "/user/{id}")
    @ResponseBody
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
