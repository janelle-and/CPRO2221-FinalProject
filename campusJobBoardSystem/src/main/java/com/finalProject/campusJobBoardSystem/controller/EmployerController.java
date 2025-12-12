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



@Controller
@RequestMapping("/employer")
public class EmployerController {
    private final JobService jobService;
    private final ApplicationService jobAppService;

    public EmployerController(JobService jobService, ApplicationService jobAppService) {
        this.jobService = jobService;
        this.jobAppService = jobAppService;
    }



    // View employer's job postings
    @GetMapping("/jobs")
    public String jobs(Model model) {
        model.addAttribute("jobs", jobService.findAll());
        return "myJobs";
    }

    // Create new Job posting
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("job", new Job());
        return "editJob";
    }

    // Save new job posting
    @PostMapping("/save")
    public String saveItem(@Valid @ModelAttribute("job") Job job,
                           BindingResult result) {
        if (result.hasErrors()) {
            return "editJob";
        }
        jobService.save(job);
        return "redirect:/myJobs";
    }

    // Edit job posting
    @GetMapping("/edit/{id}")
    public String editItem(@PathVariable Long id, Model model) {
        Job job = jobService.findById(id);

        if (job == null) {
            return "redirect:/myJobs";
        }

        model.addAttribute("job", job);
        return "editJob";
    }

    // Delete job posting
    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        jobService.deleteById(id);
        return "redirect:/myJobs";
    }

    // View posting applications submitted by students
    @GetMapping("/applications")
    public String jobApplications(Model model){
        model.addAttribute("applications", jobAppService.findAll());
        return "viewApplicants";
    }
}
