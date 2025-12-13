package com.finalProject.campusJobBoardSystem.service;

import com.finalProject.campusJobBoardSystem.model.JobApplication;
import com.finalProject.campusJobBoardSystem.model.User;
import com.finalProject.campusJobBoardSystem.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repo;

    @InjectMocks
    private UserService service;

    // test findAll()
    @Test
    void findAllUsers_returnsAllUsers() {
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
    void saveUserIntoRepository_Successfully() {
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

    // test findById(id)
    @Test
    void findUserById_WhenUserExists_ReturnsUser() {
        // Arrange
        User user = new User();
        user.setUser_id(3L);

        when(repo.findById(3L)).thenReturn(Optional.of(user));

        // Act
        User result = service.findById(3L);

        // Assert
        assertNotNull(result);
        assertEquals(user.getUser_id(), result.getUser_id());
        verify(repo, times(1)).findById(3L);
    }

    // test findBy(id) throws exception
    @Test
    void findUserById_WhenUserDoesNotExists_ThrowsException() {
        // Arrange
        when(repo.findById(6L)).thenReturn(Optional.empty());

        // Act / Assert
        assertThrows(RuntimeException.class, () -> {
            service.findById(6L);
        });
    }

    // test findByEmail()
    @Test
    void findByEmail_WithEmailExists_ReturnsUser() {
        // Arrange
        User user = new User();
        user.setUser_id(3L);
        user.setEmail("email@email.com");

        when(repo.findByEmail("email@email.com")).thenReturn(user);

        // Act
        User result = service.findByEmail("email@email.com");

        // Assert
        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        verify(repo, times(1)).findByEmail("email@email.com");
    }

    // test loadUserByUsername()
    @Test
    void loadUserByUsername_WhereUsernameExists() {
        // Arrange
        User user = new User(2L,"name","email@email.com","123",User.Role.EMPLOYER,User.Status.ACTIVE,new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()));

        when(repo.findByEmail("email@email.com")).thenReturn(user);

        // Act
        UserDetails result = service.loadUserByUsername("email@email.com");

        // Assert
        assertNotNull(result);
        verify(repo, times(1)).findByEmail("email@email.com");
    }
}