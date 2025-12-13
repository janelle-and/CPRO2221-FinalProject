package com.finalProject.campusJobBoardSystem.service;

import com.finalProject.campusJobBoardSystem.model.Job;
import com.finalProject.campusJobBoardSystem.model.JobApplication;
import com.finalProject.campusJobBoardSystem.model.User;
import com.finalProject.campusJobBoardSystem.repository.JobApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

    @Mock
    private JobApplicationRepository repo;

    @InjectMocks
    private ApplicationService service;

    // tests findAll()
    @Test
    void findAll_whenJobApplicationsExists_ReturnJobApplications() {
        List<JobApplication> jobs = new ArrayList<>();
        jobs.add(new JobApplication());
        jobs.add(new JobApplication());
        jobs.add(new JobApplication());

        when(repo.findAll()).thenReturn(jobs);

        List<JobApplication> result = service.findAll();

        assertNotNull(result);

        verify(repo, times(1)).findAll();
    }


    // test save()
    @Test
    void saveJobApplication_CorrectInfo() {
        // arrange
        JobApplication jobApplication = new JobApplication();
        jobApplication.setApplication_id(2L);

        when(repo.save(any(JobApplication.class))).thenReturn(jobApplication);

        // act
        JobApplication savedJobApp = service.save(jobApplication);

        // assert
        assertNotNull(savedJobApp);
        assertEquals(jobApplication.getApplication_id(), savedJobApp.getApplication_id());

        verify(repo, times(1)).save(any(JobApplication.class));
    }

    // test findByStudent()
    @Test
    void findByStudent_whenStudentExists() {
        // arrange
        User student = new User();
        student.setUser_id(1L);
        student.setFullName("Student");
        student.setEmail("email@email.com");
        student.setPassword("password");
        student.setRole(User.Role.STUDENT);
        List<User> students = new ArrayList<>();
        students.add(student);

        JobApplication jobApplication = new JobApplication();
        jobApplication.setApplication_id(1L);
        jobApplication.setStudent_id(students);
        jobApplication.setStart_time(new Timestamp(System.currentTimeMillis()));
        jobApplication.setStatus(JobApplication.Status.SUBMITTED);

        List<JobApplication> jobApplications = new ArrayList<>();
        jobApplications.add(jobApplication);
        Optional.of(student);

        when(repo.findByStudent(student)).thenReturn((jobApplications));

        // act
        List<JobApplication> result = service.findByStudent(student);

        // assert
        assertNotNull(result);
        assertEquals(1L, result.getFirst().getApplication_id());
        verify(repo, times(1)).findByStudent(student);
    }

    // test findByJob()
    @Test
    void findByJob_whenJobExists() {
        // arrange
        Job job = new Job();
        job.setJob_id(1L);

        JobApplication jobApplication = new JobApplication();
        jobApplication.setApplication_id(1L);
        jobApplication.setJob_id(job);
        List<JobApplication> jobApplications = new ArrayList<>();
        jobApplications.add(jobApplication);
        Optional.of(job);
        when(repo.findByJob(job)).thenReturn((jobApplications));

        // act
        List<JobApplication> result = service.findByJob(job);

        // assert
        assertNotNull(result);
        assertEquals(1L, result.getFirst().getApplication_id());
        verify(repo, times(1)).findByJob(job);
    }

}