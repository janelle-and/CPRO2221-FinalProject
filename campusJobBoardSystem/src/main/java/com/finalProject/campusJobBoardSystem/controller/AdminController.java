package com.finalProject.campusJobBoardSystem.controller;

import com.finalProject.campusJobBoardSystem.service.ApplicationService;
import com.finalProject.campusJobBoardSystem.service.JobService;
import com.finalProject.campusJobBoardSystem.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final JobService jobService;
    private final ApplicationService jobAppService;
    private final UserService userService;

    public AdminController(JobService jobService, ApplicationService jobAppService,  UserService userService) {
        this.jobService = jobService;
        this.jobAppService = jobAppService;
        this.userService = userService;
    }

    // View all user accounts (make it so they can activate/deactivate user accounts from here? need to make another method?)
    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("user", userService.findAll());
        return "users-list";
    }

    // View all job postings (make it so they can approve/reject user accounts from here? need to make another method?)
    @GetMapping("/jobs")
    public String jobs(Model model) {
        model.addAttribute("items", jobService.findAll());
        return "jobs-list";
    }
}
