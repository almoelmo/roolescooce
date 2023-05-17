package com.example.roles.controller;

import com.example.roles.model.Role;
import com.example.roles.model.User;
import com.example.roles.model.User;
import com.example.roles.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = {"/", "/main"})
    public String mainPage(){
        return "index";
    }

    @GetMapping(value = "/users")
    public String getAllUsers(Model model){
        model.addAttribute("users", userService.allUsers());
        return "users";
    }
    @GetMapping(value = "/users/{id}")
    public String getPostById(Model model, @PathVariable long id) {
        User user = null;
        try {
            user = userService.findUserById(id);
            model.addAttribute("allowDelete", false);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping(value = {"/register"})
    public String showNewUser(Model model) {
        User user = new User();
        model.addAttribute("add", true);
        model.addAttribute("user", user);
        return "user-form";
    }

    @PostMapping(value = "/register")
    public String addUser(Model model, @ModelAttribute("user") User user) {
        try {
            Set<Role> userRoles = new HashSet<>();
            userRoles.add(userService.findAnyRole());
            user.setRoles(userRoles);
            userService.saveUser(user);
            return "redirect:/login";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", true);
            return "user-form";
        }
    }
    @GetMapping(value = {"/users/{id}/edit"})
    public String showEditUser(Model model, @PathVariable long id) {
        User user = null;
        try {
            user = userService.findUserById(id);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("add", false);
        model.addAttribute("user", user);
        return "user-form";
    }

    @PostMapping(value = {"/users/{id}/edit"})
    public String updateUser(Model model,
                             @PathVariable long id,
                             @ModelAttribute("user") User user) {
        try {
            user.setId(id);
            userService.saveUser(user);
            return "redirect:/users";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", false);
            return "user-form";
        }
    }
    @GetMapping(value = {"/users/{id}/delete"})
    public String showDeleteUserById(Model model, @PathVariable long id) {
        User user = null;
        try {
            user = userService.findUserById(id);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("allowDelete", true);
        model.addAttribute("user", user);
        return "user";
    }

    @PostMapping(value = {"/users/{id}/delete"})
    public String deleteUserById(Model model, @PathVariable long id) {
        try {
            userService.deleteUser(id);
            return "redirect:/users";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            return "user";
        }
    }
}
