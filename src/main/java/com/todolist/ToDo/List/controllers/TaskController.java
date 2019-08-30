package com.todolist.ToDo.List.controllers;

import com.todolist.ToDo.List.enums.TaskStatusEnum;
import com.todolist.ToDo.List.model.Task;
import com.todolist.ToDo.List.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping
public class TaskController {

 private final TaskRepo taskRepo;

    @Autowired
    public TaskController(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    @PostMapping("/addtask")
    public String addTask(Task task, BindingResult res, Model model) {
        if (res.hasErrors()) {
            return "task-add";
        }
        taskRepo.save(task);
        model.addAttribute("tasks",taskRepo.findAll());
        return "redirect:/";
    }

    @GetMapping({"list","/"})
    public String showUpdateForm(Model model) {
        model.addAttribute("tasks",taskRepo.findAll());
        return "index";
    }

    @GetMapping("/signup")
    public String showSignUpForm(Task task,Model model) {

        Set<TaskStatusEnum> statusList = new HashSet<>();
        TaskStatusEnum.stream().forEach(statusList::add);
        model.addAttribute("statusList", statusList);

        return "task-add";
    }
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Task task = taskRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));
        model.addAttribute("task", task);

        Set<TaskStatusEnum> statusList = new HashSet<>();
        TaskStatusEnum.stream().forEach(statusList::add);
        model.addAttribute("statusList", statusList);
        return "update-task";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid Task task, BindingResult result, Model model) {
        if (result.hasErrors()) {
            task.setId(id);
            return "redirect:/";
        }

        taskRepo.save(task);
        model.addAttribute("tasks", taskRepo.findAll());
        return "redirect:/";
    }

    @GetMapping("/{status}")
    public String displayByStatus(Model model, @PathVariable("status") String taskStatus) {

        if(taskStatus.equals(TaskStatusEnum.NEW.getTypeOfStatus())){
            model.addAttribute("tasks", taskRepo.getTasksByStatus(TaskStatusEnum.NEW));
        } else if(taskStatus.equals(TaskStatusEnum.CLOSE.getTypeOfStatus())){
            model.addAttribute("tasks", taskRepo.getTasksByStatus(TaskStatusEnum.CLOSE));
        } else {
            model.addAttribute("tasks", taskRepo.findAll());
        }

        Set<TaskStatusEnum> statusList = new HashSet<>();
        TaskStatusEnum.stream().forEach(statusList::add);
        model.addAttribute("statusList", statusList);

        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") long id, Model model) {
        Task task = taskRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));
        taskRepo.delete(task);
        model.addAttribute("tasks", taskRepo.findAll());
        return "redirect:/";
    }
}
