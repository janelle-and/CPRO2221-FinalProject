package com.finalProject.campusJobBoardSystem.service;

import com.finalProject.campusJobBoardSystem.model.User;
import com.finalProject.campusJobBoardSystem.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdminService implements UserDetailsService {
    private final UserRepository repo;

    public AdminService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> findAllAdmins()
    {
        return repo.findAll();
    }

    public User save(User user)
    {
        return repo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = repo.findByFullName(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getFull_name())
                .password(user.getPassword())
                .roles(user.getRole().toString().replace("ROLE_ADMIN", ""))
                .build();
    }

}
