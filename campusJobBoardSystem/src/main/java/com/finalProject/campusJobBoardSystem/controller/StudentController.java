package com.finalProject.campusJobBoardSystem.controller;

import com.finalProject.campusJobBoardSystem.model.Job;
import com.finalProject.campusJobBoardSystem.model.JobApplication;
import com.finalProject.campusJobBoardSystem.service.ApplicationService;
import com.finalProject.campusJobBoardSystem.service.JobService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class StudentController {
    private final JobService jobService;
    private final ApplicationService jobAppService;

    public StudentController(JobService jobService, ApplicationService jobAppService) {
        this.jobService = jobService;
        this.jobAppService = jobAppService;
    }

    // View (admin approved) Detailed Job descriptions  ( there is how to add a search in the lostfound secure example )
    @GetMapping("/jobs")
    public String jobs(Model model) {

        model.addAttribute("items", jobService.findAll());
        return "jobs-list";
    }

    // Apply for a job (need to make sure they can only apply for a job once)
    @GetMapping("/jobs/{id}")
    public String apply(@PathVariable Long id, Model model){
        Optional<JobApplication> jobApp = jobAppService.findById(id);

        if (jobApp == null) {
            return "redirect:/jobs-list";
        }

        model.addAttribute("jobApplication", jobApp);
        return "job-application";
    }

    // Save job application
    @PostMapping("/jobs/{id}/save")
    public String saveItem(@Valid @ModelAttribute("job") JobApplication jobApp,
                           BindingResult result) {
        if (result.hasErrors()) {
            return "job-application";
        }
        jobAppService.save(jobApp);
        return "redirect:/jobs-list";
    }
}
