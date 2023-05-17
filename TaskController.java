package com.example.roles.controller;

import com.example.roles.model.Task;
import com.example.roles.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TaskController extends ExceptionController {

    @Autowired
    private TaskRepository service;
    @GetMapping(value = "/tasks")
    public String getAllTasks(Model model){
        model.addAttribute("tasks", service.findAll());
        return "tasks";
    }

    @GetMapping(value = "/tasks/{id}")
    public String getPostById(Model model, @PathVariable long id) {
        Task task = null;
        try {
            task = service.findById(id).get();
            model.addAttribute("allowDelete", false);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("task", task);
        return "task";
    }

    @GetMapping(value = {"/tasks/new"})
    public String showNewTask(Model model) {
        Task task = new Task();
        model.addAttribute("add", true);
        model.addAttribute("task", task);
        return "task-form";
    }

    @PostMapping("/tasks/new")
    public String addTask(Model model, @ModelAttribute Task task){
        try {
            service.save(task);
            return "redirect:/tasks";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", true);
            return "task-form";
        }
    }

    @GetMapping(value = {"/tasks/{id}/edit"})
    public String showEditTask(Model model, @PathVariable long id) {
        Task task = null;
        try {
            task = service.findById(id).get();
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("add", false);
        model.addAttribute("task", task);
        return "task-form";
    }

    @PostMapping(value = {"/tasks/{id}/edit"})
    public String updateTask(Model model,
                             @PathVariable long id,
                             @ModelAttribute("task") Task task) {
        try {
            task.setId(id);
            service.save(task);
            return "redirect:/tasks";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", false);
            return "task-form";
        }
    }
    @GetMapping(value = {"/tasks/{id}/delete"})
    public String showDeleteTaskById(Model model, @PathVariable long id) {
        Task task = null;
        try {
            task = service.findById(id).get();
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("allowDelete", true);
        model.addAttribute("task", task);
        return "task";
    }

    @PostMapping(value = {"/tasks/{id}/delete"})
    public String deleteTaskById(Model model, @PathVariable long id) {
        try {
            service.deleteById(id);
            return "redirect:/tasks";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            return "task";
        }
    }
}
