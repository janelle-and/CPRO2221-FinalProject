package com.finalProject.campusJobBoardSystem.controller;

import com.finalProject.campusJobBoardSystem.model.Job;
import com.finalProject.campusJobBoardSystem.model.User;
import com.finalProject.campusJobBoardSystem.service.ApplicationService;
import com.finalProject.campusJobBoardSystem.service.JobService;
import com.finalProject.campusJobBoardSystem.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class AdminController {
    private final JobService jobService;
    private final UserService userService;

    public AdminController(JobService jobService, UserService userService) {
        this.jobService = jobService;
        this.userService = userService;
    }

    // View all user accounts (make it so they can activate/deactivate user accounts from here? need to make another method?)
    @GetMapping("/userManagement")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "userManagement";
    }

    // Activate a user
    @GetMapping("/userManagement/activate/{id}")
    public String activateUser(@PathVariable Long id) {
        userService.findById(id).setStatus(User.Status.ACTIVE);
        userService.save(userService.findById(id));
        return "redirect:/userManagement";
    }

    // deactivate a user
    @GetMapping("/userManagement/deactivate/{id}")
    public String deactivateUser(@PathVariable Long id) {
        userService.findById(id).setStatus(User.Status.INACTIVE);
        userService.save(userService.findById(id));
        return "redirect:/userManagement";
    }

    // View all job postings (make it so they can approve/reject jobs from here? need to make another method?)
    @GetMapping("/jobApproval")
    public String jobs(Model model) {
        model.addAttribute("jobs", jobService.findAll());
        return "jobApproval";
    }

    // Approve a job
    @GetMapping("/jobApproval/approve/{id}")
    public String approveJob(@PathVariable Long id) {
        jobService.findById(id).setStatus(Job.Status.APPROVED);
        jobService.save(jobService.findById(id));
        return "redirect:/jobApproval";
    }

    // Reject a job
    @GetMapping("/jobApproval/reject/{id}")
    public String rejectJob(@PathVariable Long id) {
        jobService.findById(id).setStatus(Job.Status.REJECTED);
        jobService.save(jobService.findById(id));
        return "redirect:/jobApproval";
    }
}
