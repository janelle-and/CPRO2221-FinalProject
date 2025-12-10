package com.finalProject.campusJobBoardSystem.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.finalProject.campusJobBoardSystem.model.User;
import com.finalProject.campusJobBoardSystem.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository repo;

    public UserService(UserRepository userRepository) {
        this.repo = userRepository;
    }

    public List<User> findAll() {
        return repo.findAll();
    }

    public User save(User user) {
        return repo.save(user);
    }

    public User findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("User Not Found: " + id));
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = repo.findByFullName(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        // edit in future
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getFullName())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .disabled(user.getStatus() == User.Status.INACTIVE)
                .build();
    }


}
