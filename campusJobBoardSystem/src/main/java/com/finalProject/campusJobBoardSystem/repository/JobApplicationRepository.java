package com.finalProject.campusJobBoardSystem.repository;

import java.util.List;

import com.finalProject.campusJobBoardSystem.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.finalProject.campusJobBoardSystem.model.JobApplication;
import com.finalProject.campusJobBoardSystem.model.User;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication,Long> {

    @Query("SELECT ja FROM JobApplication ja JOIN ja.student_id s WHERE s = :student")
    List<JobApplication> findByStudent(@Param("student") User student);

    @Query("SELECT ja FROM JobApplication ja JOIN ja.job_id j WHERE j = :job")
    List<JobApplication> findByJob(@Param("job") Job job);

}
