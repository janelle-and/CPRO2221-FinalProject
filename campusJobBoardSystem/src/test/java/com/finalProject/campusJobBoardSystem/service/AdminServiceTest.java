package com.finalProject.campusJobBoardSystem.service;

import com.finalProject.campusJobBoardSystem.model.User;
import com.finalProject.campusJobBoardSystem.repository.JobApplicationRepository;
import com.finalProject.campusJobBoardSystem.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private UserRepository repo;

    @InjectMocks
    private AdminService service;

    // test findAll()
    @Test
    void findAll_returnsAllUsers() {
        // Arrange
        List<User> users = new ArrayList<>();
        User user1 = new User();
        User user2 = new User();
        user1.setUser_id(1L);
        user2.setUser_id(2L);
        users.add(user1);
        users.add(user2);

        when(repo.findAll()).thenReturn(users);

        // Act
        List<User> result = service.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size(), "The list size should be 2");
        assertEquals(users, result, "The returned list should match the expected list");
        verify(repo, times(1)).findAll();
    }

    // test save()
    @Test
    void saveAdminSuccessfullyInRepository() {
        // Arrange
        User user = new User();
        user.setUser_id(2L);

        when(repo.save(any(User.class))).thenReturn(user);

        // Act
        User savedUser = service.save(user);

        // Assert
        assertNotNull(savedUser);
        assertEquals(user.getUser_id(), savedUser.getUser_id());
        verify(repo, times(1)).save(any(User.class));
    }
}