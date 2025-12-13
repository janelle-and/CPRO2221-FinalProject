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

    // View (admin approved) jobs  + Search
    @GetMapping("/jobList")
    public String jobs(@RequestParam(value = "keyword", required = false) String keyword, Model model){
        // if the student entered a keyword, search approved jobs for job containing this job title
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("jobs", jobService.search(keyword));
            model.addAttribute("keyword", keyword);
        }
        // if the student left the search bar blank display all admin approved jobs
        else {
            model.addAttribute("jobs", jobService.findAllAdminApproved());
        }
        return "jobList";
    }

    // View job details
    @GetMapping("/jobDetails/{id}")
    public String jobDetails(@PathVariable Long id, Model model){
        Job job = jobService.findById(id);
        // if job does not exist return to jobList
        if (job == null) {
            return "redirect:/jobList";
        }
        model.addAttribute("job", job);
        return "jobDetails";
    }

    // View student's applications
    @GetMapping("/apply")
    public String apply(Model model){
        // get email to get the current student, to find all job applications by this student
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User student = userService.findByEmail(email);
        List<JobApplication> applications = jobAppService.findByStudent(student);
        // then find all jobs associated with the job applications to display
        List<Job> jobs = new ArrayList<>();
        for(JobApplication ja : applications){
            jobs.add(jobService.findById(ja.getJob_id().getJob_id()));
        }
        model.addAttribute("jobs", jobs);
        return "apply";
    }

    // Save job application
    @GetMapping("/apply/{id}")
    public String saveApplication(@PathVariable Long id, @Valid @ModelAttribute("jobApp") JobApplication jobApp,
                           BindingResult result) {
        // if the result isn't valid, return to the apply page
        if (result.hasErrors()) {
            return "apply";
        }
        // gets job being applied to
        Job job = jobService.findById(id);
        // gets student applying for the job
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User student = userService.findByEmail(email);
        // gets job applications the student has already completed
        List<JobApplication> applications = jobAppService.findByStudent(student);
        // if the student has already completed an application for this job
        // return to jobList without creating a new job application
        for (JobApplication ja : applications){
            if(ja.getJob_id().getJob_id().equals(job.getJob_id())){
                return "redirect:/jobList";
            }
        }
        // otherwise create new job application
        jobApp.setJob_id(job);
        jobApp.getStudent_id().add(student);
        jobAppService.save(jobApp);
        return "redirect:/jobList";
    }
}
