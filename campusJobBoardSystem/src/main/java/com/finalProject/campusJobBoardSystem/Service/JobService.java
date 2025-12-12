package com.finalProject.campusJobBoardSystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.finalProject.campusJobBoardSystem.model.Job;
import com.finalProject.campusJobBoardSystem.repository.JobRepository;

@Service
public class JobService {
private final JobRepository jobRepo;

    public JobService(JobRepository JobRepo) {
        this.jobRepo = JobRepo;
    }

    public List<Job> findAll() {
        return jobRepo.findAll();
    }

    public List<Job> findAllAdminApproved() {
        return jobRepo.findByStatus(Job.Status.APPROVED);
    }

    public Job save(Job job) {
        return jobRepo.save(job);
    }

    public void deleteById(Long id) { jobRepo.deleteById(id);
    }
    public Job findById(Long id) {
        return jobRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Job Not Found: " + id));
    }

    public List<Job> search(String keyword) {
        return jobRepo.findByTitleContainingIgnoreCaseAndStatus(keyword, Job.Status.APPROVED);
    }

}
