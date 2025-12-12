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

    // tests save()

    // add throw to service
//    @Test
//    void saveJobApplication_givenIncorrectInfo() {
//        JobApplication jobApplication = new JobApplication(null,null,null,null,null);
//
//        assertThrows(IllegalArgumentException.class, () -> {
//            service.save(jobApplication);
//        });
//
//        verify(repo, times(0)).save(any(JobApplication.class));
//    }

    // test save()
    @Test
    void saveJobApplication_CorrectInfo() {
        JobApplication jobApplication = new JobApplication();
        jobApplication.setApplication_id(2L);

        when(repo.save(any(JobApplication.class))).thenReturn(jobApplication);

        JobApplication savedJobApp = service.save(jobApplication);

        assertNotNull(savedJobApp);
        assertEquals(jobApplication.getApplication_id(), savedJobApp.getApplication_id());

        verify(repo, times(1)).save(any(JobApplication.class));
    }

    // test findByStudent()
    @Test
    void findByStudent_whenStudentExists() {
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

        List<JobApplication> result = service.findByStudent(student);

        assertNotNull(result);
        assertEquals(1L, result.getFirst().getApplication_id());
        verify(repo, times(1)).findByStudent(student);
    }

    // test findByStudent() with no student
    // need to add throw
    @Test
    void findByStudent_whenStudentDoesNotExist() {
        when(repo.findByStudent(null)).thenReturn(null);
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.findByStudent(null));
        assertTrue(ex.getMessage().contains("No job applications not found"));
    }

    // test findByJob()
    @Test
    void findByJob_whenJobExists() {
        Job job = new Job();
        job.setJob_id(1L);

        JobApplication jobApplication = new JobApplication();
        jobApplication.setApplication_id(1L);
        jobApplication.setJob_id(job);
        jobApplication.setStart_time(new Timestamp(System.currentTimeMillis()));
        jobApplication.setStatus(JobApplication.Status.SUBMITTED);
        List<JobApplication> jobApplications = new ArrayList<>();
        jobApplications.add(jobApplication);
        Optional.of(job);
        when(repo.findByJob(job)).thenReturn((jobApplications));

        List<JobApplication> result = service.findByJob(job);

        assertNotNull(result);
        assertEquals(1L, result.getFirst().getApplication_id());
        verify(repo, times(1)).findByJob(job);
    }

    // test findByJob() with no student
    // need to add throw
    @Test
    void findByJob_whenJobDoesNotExist() {
        when(repo.findByJob(null)).thenReturn(null);
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.findByJob(null));
        assertTrue(ex.getMessage().contains("No job applications not found"));
    }

}