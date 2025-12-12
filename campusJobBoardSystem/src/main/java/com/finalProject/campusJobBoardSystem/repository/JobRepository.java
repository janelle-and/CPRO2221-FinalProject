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
    List<Job> findByTitleContainingIgnoreCaseAndStatus(String title, Job.Status status);
    List<Job> findByStatus(Job.Status status);

    @Query("SELECT job FROM Job job JOIN job.employer_id e WHERE e = :employer")
    List<Job> findByEmployer(@Param("employer") User employer);
}
