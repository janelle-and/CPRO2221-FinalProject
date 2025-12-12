package com.finalProject.campusJobBoardSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.finalProject.campusJobBoardSystem.model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {
    List<Job> findByTitleContainingIgnoreCaseAndStatus(String title, Job.Status status);
    List<Job> findByStatus(Job.Status status);
}
