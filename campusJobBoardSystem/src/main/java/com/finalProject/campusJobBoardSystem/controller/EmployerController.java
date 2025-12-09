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



// might need to replace some return names when we make thymeleaf pages

@Controller
@RequestMapping("/employer")
public class EmployerController {
    private final JobService jobService;
    private final ApplicationService jobAppService;

    public EmployerController(JobService jobService, ApplicationService jobAppService) {
        this.jobService = jobService;
        this.jobAppService = jobAppService;
    }


    // need this?
    // View all job postings
    @GetMapping("/jobs")
    public String jobs(Model model) {
        model.addAttribute("items", jobService.findAll());
        return "jobs-list";
    }

    // Create new Job posting
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("job", new Job());
        return "job-form";
    }

    // Save new job posting
    @PostMapping("/save")
    public String saveItem(@Valid @ModelAttribute("job") Job job,
                           BindingResult result) {
        if (result.hasErrors()) {
            return "job-form";
        }
        jobService.save(job);
        return "redirect:/jobs-list";
    }

    // Edit job posting
    @GetMapping("/edit/{id}")
    public String editItem(@PathVariable Long id, Model model) {
        Job job = jobService.findById(id);

        if (job == null) {
            return "redirect:/jobs-list";
        }

        model.addAttribute("job", job);
        return "job-form";
    }

    // Delete job posting
    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        jobService.deleteById(id);
        return "redirect:/jobs-list";
    }

    // View posting applications submitted by students
    @GetMapping("/applications")
    public String jobApplications(Model model){
        model.addAttribute("applications", jobAppService.findAll());
        return "applications-list";
    }
}
