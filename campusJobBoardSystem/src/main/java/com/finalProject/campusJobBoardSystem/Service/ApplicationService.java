package com.finalProject.campusJobBoardSystem.service;


import java.util.List;
import java.util.Optional;

import com.finalProject.campusJobBoardSystem.model.Job;
import org.springframework.stereotype.Service;

import com.finalProject.campusJobBoardSystem.model.JobApplication;
import com.finalProject.campusJobBoardSystem.model.User;
import com.finalProject.campusJobBoardSystem.repository.JobApplicationRepository;

@Service
public class ApplicationService{

    private final JobApplicationRepository jobAppRepo;

    public ApplicationService(JobApplicationRepository jobAppRepo) {
        this.jobAppRepo = jobAppRepo;
    }

    // finds all job applications
    public List<JobApplication> findAll() {
        return jobAppRepo.findAll();
    }

    // saves a job application
    public JobApplication save(JobApplication jobApp) {
        return jobAppRepo.save(jobApp);
    }

    // finds a job application by its associated student
    public List<JobApplication> findByStudent(User student) {
        return jobAppRepo.findByStudent(student);
    }

    // finds a job application by its associated job
    public List<JobApplication> findByJob(Job job) {
        return jobAppRepo.findByJob(job);
    }

}
