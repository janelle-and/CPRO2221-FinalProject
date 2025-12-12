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


    public List<JobApplication> findAll() {
        return jobAppRepo.findAll();
    }

    public Optional<JobApplication> findById(Long id) {
        return jobAppRepo.findById(id);
    }

    public JobApplication save(JobApplication jobApp) {
        return jobAppRepo.save(jobApp);
    }

    public List<JobApplication> findByStudent(User student) {
        return jobAppRepo.findByStudent(student);
    }

    public List<JobApplication> findByJob(Job job) {
        return jobAppRepo.findByJob(job);
    }

    public void delete(JobApplication jobApp) {
        jobAppRepo.delete(jobApp);
    }

}
