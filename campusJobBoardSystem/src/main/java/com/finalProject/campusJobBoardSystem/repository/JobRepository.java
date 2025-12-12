package com.finalProject.campusJobBoardSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.finalProject.campusJobBoardSystem.model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {
    List<Job> findByTitleContainingIgnoreCaseOrLocationContainingIgnoreCase(String title, String location);
    List<Job> findByStatus(Job.Status status);

    @Query("SELECT j FROM Job j WHERE j.status = :status AND (LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(j.location) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Job> findApprovedJobsByKeyword(@Param("status") Job.Status status, @Param("keyword") String keyword);
}
