package com.finalProject.campusJobBoardSystem.controller;

import com.finalProject.campusJobBoardSystem.model.Job;
import com.finalProject.campusJobBoardSystem.model.JobApplication;
import com.finalProject.campusJobBoardSystem.service.ApplicationService;
import com.finalProject.campusJobBoardSystem.service.JobService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final JobService jobService;
    private final ApplicationService jobAppService;

    public StudentController(JobService jobService, ApplicationService jobAppService) {
        this.jobService = jobService;
        this.jobAppService = jobAppService;
    }

    // View (admin approved) jobs  + search
    @GetMapping("/jobs")
    public String jobs(
        @RequestParam(value = "keyword", required = false) String keyword, Model model){

            if (keyword != null && !keyword.isEmpty()) {
                model.addAttribute("jobs", jobService.search(keyword));
                model.addAttribute("keyword", keyword);
            } else {
                model.addAttribute("jobs", jobService.findAllAdminApproved());
            }

            return "jobList";
    }

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

    // Apply for a job (need to make sure they can only apply for a job once)
    @GetMapping("/jobs/{id}")
    public String apply(@PathVariable Long id, Model model){
        Optional<JobApplication> jobApp = jobAppService.findById(id);

        if (jobApp == null) {
            return "redirect:/jobList";
        }

        model.addAttribute("jobApplication", jobApp);
        return "apply";
    }

    // Save job application
    @PostMapping("/jobs/{id}/save")
    public String saveJob(@Valid @ModelAttribute("job") JobApplication jobApp,
                           BindingResult result) {
        if (result.hasErrors()) {
            return "apply";
        }
        jobAppService.save(jobApp);
        return "redirect:/jobList";
    }
}
