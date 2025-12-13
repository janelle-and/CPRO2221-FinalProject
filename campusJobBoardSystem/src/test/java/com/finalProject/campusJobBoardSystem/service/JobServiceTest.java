package com.finalProject.campusJobBoardSystem.service;

import com.finalProject.campusJobBoardSystem.model.Job;
import com.finalProject.campusJobBoardSystem.model.JobApplication;
import com.finalProject.campusJobBoardSystem.model.User;
import com.finalProject.campusJobBoardSystem.repository.JobRepository;
import com.finalProject.campusJobBoardSystem.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobServiceTest {

    @Mock
    private JobRepository repo;

    @InjectMocks
    private JobService service;

    // test findAll()
    @Test
    void findAllJobs_Success() {
        // Arrange
        User user = new User(2L,"name","email@email.com","123",User.Role.EMPLOYER,User.Status.ACTIVE,new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()));
        List<Job> jobs = Arrays.asList(
                new Job(1L, "title", "description", "location",10,"category", new Date(2022,5,5), Job.Status.APPROVED,new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()),user),
                new Job(2L, "title", "description", "location",10,"category", new Date(2022,5,5), Job.Status.APPROVED,new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()),user),
                new Job(3L, "title", "description", "location",10,"category", new Date(2022,5,5), Job.Status.APPROVED,new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()),user)
        );
        when(repo.findAll()).thenReturn(jobs);

        // Act
        List<Job> result = service.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size(), "The list size should be 3");
        assertEquals(jobs, result, "The returned list should match the expected list");
        verify(repo, times(1)).findAll();
    }

    // test findAllAdminApproved
    @Test
    void findAllAdminApproved() {
        // arranged
        User user = new User(2L,"name","email@email.com","123",User.Role.EMPLOYER,User.Status.ACTIVE,new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()));


        Job job1 = new Job(1L, "title", "description", "location",10,"category", new Date(2022,5,5), Job.Status.APPROVED,new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()),user);
        Job job2 = new Job(2L, "title", "description", "location",10,"category", new Date(2022,5,5), Job.Status.REJECTED,new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()),user);
        Job job3 = new Job(3L, "title", "description", "location",10,"category", new Date(2022,5,5), Job.Status.APPROVED,new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()),user);
        repo.save(job1);
        repo.save(job2);
        repo.save(job3);

        List<Job> expectedResult =  new ArrayList<>();
        expectedResult.add(job1);
        expectedResult.add(job3);
        when(repo.findByStatus(Job.Status.APPROVED)).thenReturn(expectedResult);

        // act
        List<Job> result = service.findAllAdminApproved();

        // assert
        assertNotNull(result);
        assertEquals(2, result.size(), "The list size should be 2");
        assertEquals(expectedResult, result, "The returned list should match the expected list");
        verify(repo, times(1)).findByStatus(Job.Status.APPROVED);
    }

    // test save()
    @Test
    void saveJobSuccessfullyIntoRepo() {
        // Arrange
        Job job = new Job();
        job.setJob_id(2L);

        when(repo.save(any(Job.class))).thenReturn(job);

        // Act
        Job savedJob = service.save(job);

        // Assert
        assertNotNull(savedJob);
        assertEquals(job.getJob_id(), savedJob.getJob_id());
        verify(repo, times(1)).save(any(Job.class));
    }

    // test deleteById(id)
    @Test
    void deleteJobById_SuccessfullyRemoveFromRepo() {
        // Assert
        Job job = new Job();
        job.setJob_id(3L);

        // Act
        service.deleteById(job.getJob_id());

        // Assert
        assertThrows(RuntimeException.class, () -> {
            service.findById(job.getJob_id());
        });
        verify(repo, times(1)).deleteById(3L);
    }

    // test findById(id)
    @Test
    void findJobById_WhenJobExists_ReturnsJob() {
        // Arrange
        User user = new User(2L,"name","email@email.com","123",User.Role.EMPLOYER,User.Status.ACTIVE,new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()));
        Job job = new Job(3L, "title", "description", "location",10,"category", new Date(2022,5,5), Job.Status.APPROVED,new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()),user);

        repo.save(job);

        when(repo.findById(3L)).thenReturn(Optional.of(job));

        // Act
        Job result = service.findById(3L);

        // Assert
        assertNotNull(result);
        assertEquals(job.getJob_id(), result.getJob_id());
        verify(repo, times(1)).findById(3L);
    }

    // test findById(id) throws exception
    @Test
    void findJobById_WhenJobDoesNotExists_ThrowsException() {
        // Arrange
        when(repo.findById(6L)).thenReturn(Optional.empty());

        // Act / Assert
        assertThrows(RuntimeException.class, () -> {
            service.findById(6L);
        });
    }

    // test findByEmployer(user)
    @Test
    void findJobByEmployer_WhenEmployerExists() {
        // arrange
        User user = new User();
        user.setUser_id(2L);

        User user2 = new User();
        user.setUser_id(4L);

        Job job1 = new Job();
        job1.setJob_id(1L);
        job1.setEmployer_id(user);
        Job job2 = new Job();
        job2.setJob_id(2L);
        job2.setEmployer_id(user);

        Job job3 = new Job();
        job2.setJob_id(3L);
        job2.setEmployer_id(user2);

        List<Job> jobs = new ArrayList<>();
        jobs.add(job1);
        jobs.add(job2);
        jobs.add(job3);

        List<Job> expectedResult =  new ArrayList<>();
        expectedResult.add(job1);
        expectedResult.add(job2);

        Optional.of(jobs);

        when(repo.findByEmployer(user)).thenReturn(expectedResult);

        // act
        List<Job> result = service.findByEmployer(user);

        // assert
        assertNotNull(result);
        assertEquals(expectedResult, result, "The returned list should match the expected list");
        verify(repo, times(1)).findByEmployer(user);
    }

    // test search(keyword)
    @Test
    void searchKeywordFound_displaysList() {
        // Arrange
        User user = new User(2L,"name","email@email.com","123",User.Role.EMPLOYER,User.Status.ACTIVE,new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()));
        Job job1 = new Job(1L, "pencil", "description", "location",10,"category", new Date(2022,5,5), Job.Status.APPROVED,new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()),user);
        Job job2 = new Job(2L, "book", "description", "location",10,"category", new Date(2022,5,5), Job.Status.APPROVED,new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()),user);
        Job job3 = new Job(3L, "books", "description", "location",10,"category", new Date(2022,5,5), Job.Status.APPROVED,new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()),user);
        List<Job> expectedResult =  Arrays.asList(
                new Job(2L, "book", "description", "location",10,"category", new Date(2022,5,5), Job.Status.APPROVED,new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()),user),
                new Job(3L, "books", "description", "location",10,"category", new Date(2022,5,5), Job.Status.APPROVED,new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()),user)
        );

        when(repo.findByTitleContainingIgnoreCaseAndStatus("book",Job.Status.APPROVED)).thenReturn(expectedResult);

        // Act
        List<Job> result = service.search("book");

        // Assert
        assertEquals(2, result.size(), "The list size should be 2");
        assertTrue(result.containsAll(expectedResult) && expectedResult.containsAll(result), "Result list should contain exactly the expected result");
    }

    @Test
    void searchKeywordNotFound_emptyList() {
        // Arrange
        List<Job> expectedResult =  new ArrayList<>();

        when(repo.findByTitleContainingIgnoreCaseAndStatus("school",Job.Status.APPROVED)).thenReturn(expectedResult);

        // Act
        List<Job> result = service.search("school");

        // Assert
        assertEquals(0, result.size(), "The list should be empty");
        assertTrue(result.containsAll(expectedResult) && expectedResult.containsAll(result), "Result list should contain exactly the expected result");
    }

}