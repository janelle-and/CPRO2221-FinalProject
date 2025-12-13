package com.finalProject.campusJobBoardSystem.service;

import java.util.List;

import com.finalProject.campusJobBoardSystem.model.User;
import org.springframework.stereotype.Service;

import com.finalProject.campusJobBoardSystem.model.Job;
import com.finalProject.campusJobBoardSystem.repository.JobRepository;

@Service
public class JobService {
private final JobRepository jobRepo;

    public JobService(JobRepository JobRepo) {
        this.jobRepo = JobRepo;
    }

    // finds all jobs
    public List<Job> findAll() {
        return jobRepo.findAll();
    }

    // finds all jobs that have status set as APPROVED by an admin
    public List<Job> findAllAdminApproved() {
        return jobRepo.findByStatus(Job.Status.APPROVED);
    }

    // save a job in the repo
    public Job save(Job job) {
        return jobRepo.save(job);
    }

    // delete a job by its id
    public void deleteById(Long id) { jobRepo.deleteById(id);
    }

    // finds a job by its id
    public Job findById(Long id) {
        return jobRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Job Not Found: " + id));
    }

    // finds a job by its associated employer
    public List<Job> findByEmployer(User employer) {
        return jobRepo.findByEmployer(employer);
    }

    // searches through approved jobs using a keyword
    public List<Job> search(String keyword) {
        return jobRepo.findByTitleContainingIgnoreCaseAndStatus(keyword, Job.Status.APPROVED);
    }

}
