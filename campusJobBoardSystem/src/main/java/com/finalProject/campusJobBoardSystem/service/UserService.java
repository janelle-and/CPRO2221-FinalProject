package com.finalProject.campusJobBoardSystem.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.finalProject.campusJobBoardSystem.model.User;
import com.finalProject.campusJobBoardSystem.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class UserService implements UserDetailsService {

    private final UserRepository repo;

    public UserService(UserRepository userRepository) {
        this.repo = userRepository;
    }
// finds all users

    public List<User> findAll() {
        return repo.findAll();
    }
// saves user
    @Transactional // overrides the read only
    public User save(User user) {
        return repo.save(user);
    }
// finds by ID and throws runtime exception if no user found
    public User findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("User Not Found: " + id));
    }
// finds user by email
    public User findByEmail(String email) {
        return repo.findByEmail(email);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = repo.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .disabled(user.getStatus() == User.Status.INACTIVE)
                .build();
    }


}
