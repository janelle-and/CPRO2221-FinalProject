package com.finalProject.campusJobBoardSystem.repository;

import com.finalProject.campusJobBoardSystem.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobApplicationRepository extends JpaRepository<JobApplication,Long> {
}
