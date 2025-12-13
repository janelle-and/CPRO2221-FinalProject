package com.finalProject.campusJobBoardSystem.controller;

import com.finalProject.campusJobBoardSystem.model.JobApplication;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.finalProject.campusJobBoardSystem.model.Job;
import com.finalProject.campusJobBoardSystem.model.User;
import com.finalProject.campusJobBoardSystem.service.ApplicationService;
import com.finalProject.campusJobBoardSystem.service.JobService;
import com.finalProject.campusJobBoardSystem.service.UserService;

import jakarta.validation.Valid;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping
public class EmployerController {
    private final JobService jobService;
    private final ApplicationService jobAppService;
    private final UserService userService;

    public EmployerController(JobService jobService, ApplicationService jobAppService,UserService userService) {
        this.jobService = jobService;
        this.jobAppService = jobAppService;
        this.userService = userService;
    }

    // View employer's job postings
    @GetMapping("/myJobs")
    public String jobs(Model model) {
        // gets employer's email, to find the current employer, to then get their job posts
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User employer = userService.findByEmail(email);
        List<Job> jobs = jobService.findByEmployer(employer);

        model.addAttribute("jobs", jobs);
        return "myJobs";
    }

    // Create new Job posting
    @GetMapping("/myJobs/new")
    public String showForm(Model model) {
        model.addAttribute("job", new Job());
        return "editJob";
    }

    // Save new job posting
    @PostMapping("/myJobs/save")
    public String saveItem(@Valid @ModelAttribute("job") Job job, BindingResult result) {
        // if job form isn't valid, return to editJob form
        if (result.hasErrors()) {
            return "editJob";
        }
        // gets employer's email to set the new job with employer foreign key
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.findByEmail(email);
        job.setEmployer_id(currentUser);
        // update Updated_at if job being edited
        job.setUpdated_at(new Timestamp(System.currentTimeMillis()));

        jobService.save(job);
        return "redirect:/myJobs";
    }

    // Edit job posting
    @GetMapping("/myJobs/edit/{id}")
    public String editItem(@PathVariable Long id, Model model) {
        Job job = jobService.findById(id);
        // if job doesn't exist return to myJobs page
        if (job == null) {
            return "redirect:/myJobs";
        }
        model.addAttribute("job", job);
        return "editJob";
    }

    // Delete job posting
    @GetMapping("/myJobs/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        jobService.deleteById(id);
        return "redirect:/myJobs";
    }

    // View posting applications submitted by students
    @GetMapping("/viewApplicants")
    public String jobApplications(Model model){
        // gets employer's email, to find the current employer, to then get their job posts
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User employer = userService.findByEmail(email);
        List<Job> jobs = jobService.findByEmployer(employer);
        // finds all applications related to all job posts by the employer
        List<JobApplication> applications = new ArrayList<>();
        for (Job job:jobs){
            applications.addAll(jobAppService.findByJob(job));
        }
        // gets the students that apply to all the job posts
        List<User> students = new ArrayList<>();
        for (JobApplication application:applications){
            students.addAll(application.getStudent_id());
        }
        model.addAttribute("students", students);
        return "viewApplicants";
    }
}
