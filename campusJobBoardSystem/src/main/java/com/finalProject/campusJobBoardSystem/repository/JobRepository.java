package com.finalProject.campusJobBoardSystem.repository;

import com.finalProject.campusJobBoardSystem.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job,Long> {
}
