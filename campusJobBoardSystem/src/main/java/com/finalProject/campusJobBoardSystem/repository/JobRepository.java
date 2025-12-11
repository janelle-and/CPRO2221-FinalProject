package com.finalProject.campusJobBoardSystem.repository;

import com.finalProject.campusJobBoardSystem.model.Job;
import com.finalProject.campusJobBoardSystem.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {
    List<JobApplication> findByTitleContainingIgnoreCaseOrLocationContainingIgnoreCase(String title, String location);
}
