package com.finalProject.campusJobBoardSystem.repository;

import java.util.List;

import com.finalProject.campusJobBoardSystem.model.JobApplication;
import com.finalProject.campusJobBoardSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.finalProject.campusJobBoardSystem.model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {
    // search method to search by title in approved jobs
    List<Job> findByTitleContainingIgnoreCaseAndStatus(String title, Job.Status status);

    // finds job based on status (APPROVED)
    List<Job> findByStatus(Job.Status status);

    // finds all jobs created by one employer (joins with foreign key)
    @Query("SELECT job FROM Job job JOIN job.employer_id e WHERE e = :employer")
    List<Job> findByEmployer(@Param("employer") User employer);
}
