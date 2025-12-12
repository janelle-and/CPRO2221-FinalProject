package com.finalProject.campusJobBoardSystem.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.finalProject.campusJobBoardSystem.model.Job;
import com.finalProject.campusJobBoardSystem.model.JobApplication;
import com.finalProject.campusJobBoardSystem.model.User;
import com.finalProject.campusJobBoardSystem.service.ApplicationService;
import com.finalProject.campusJobBoardSystem.service.JobService;
import com.finalProject.campusJobBoardSystem.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping
public class StudentController {
    private final JobService jobService;
    private final ApplicationService jobAppService;
    private final UserService userService;

    public StudentController(JobService jobService, ApplicationService jobAppService, UserService userService) {
        this.jobService = jobService;
        this.jobAppService = jobAppService;
        this.userService = userService;
    }

    // View (admin approved) jobs  + search
    @GetMapping("/jobList")
    public String jobs(@RequestParam(value = "keyword", required = false) String keyword, Model model){

            if (keyword != null && !keyword.isEmpty()) {
                model.addAttribute("jobs", jobService.search(keyword));
                model.addAttribute("keyword", keyword);
            } else {
                model.addAttribute("jobs", jobService.findAllAdminApproved());
            }

            return "jobList";
    }

    // View (admin approved) jobs
//    @GetMapping("/jobList")
//    public String jobs(Model model){
//        model.addAttribute("jobs", jobService.findAllAdminApproved());
//        return "jobList";
//    }

    // View job details
    @GetMapping("/jobDetails/{id}")
    public String jobDetails(@PathVariable Long id, Model model){
        Job job = jobService.findById(id);

        if (job == null) {
            return "redirect:/jobList";
        }

        model.addAttribute("job", job);
        return "jobDetails";
    }

    // View applications
    @GetMapping("/apply")
    public String apply(Model model){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User student = userService.findByEmail(email);
        List<JobApplication> applications = jobAppService.findByStudent(student);
        List<Job> jobs = new ArrayList<>();
        for(JobApplication ja : applications){
            jobs.add(jobService.findById(ja.getJobBy_id().getJob_id()));
        }
        model.addAttribute("jobs", jobs);
        return "apply";
    }

    // Save job application (make only students jobs applied to)
    @GetMapping("/apply/{id}")
    public String saveJob(@PathVariable Long id, @Valid @ModelAttribute("jobApp") JobApplication jobApp,
                           BindingResult result) {
        if (result.hasErrors()) {
            return "apply";
        }
        Job job = jobService.findById(id);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User student = userService.findByEmail(email);
        jobApp.setJob_id(job);
        jobApp.getStudent_id().add(student);
        jobAppService.save(jobApp);
        return "redirect:/jobList";
    }
}
