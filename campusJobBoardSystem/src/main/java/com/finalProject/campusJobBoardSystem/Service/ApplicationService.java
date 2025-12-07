package com.finalProject.campusJobBoardSystem.service;


import com.finalProject.campusJobBoardSystem.model.Job;
import com.finalProject.campusJobBoardSystem.model.JobApplication;
import com.finalProject.campusJobBoardSystem.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly=true)
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
    @Transactional
    public JobApplication save(JobApplication jobApp) {
        return jobAppRepo.save(jobApp);
    }
    @Transactional
    public void delete(JobApplication jobApp) {
        jobAppRepo.delete(jobApp);
    }

}
