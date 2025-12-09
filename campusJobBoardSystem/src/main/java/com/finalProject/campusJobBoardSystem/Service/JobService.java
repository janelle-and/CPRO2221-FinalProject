package com.finalProject.campusJobBoardSystem.service;

import com.finalProject.campusJobBoardSystem.model.Job;
import com.finalProject.campusJobBoardSystem.model.JobApplication;
import com.finalProject.campusJobBoardSystem.repository.JobRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JobService {
private final JobRepository jobRepo;

    public JobService(JobRepository JobRepo) {
        this.jobRepo = JobRepo;
    }

    public List<Job> findAll() {
        return jobRepo.findAll();
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

}
