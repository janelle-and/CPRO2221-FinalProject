package com.finalProject.campusJobBoardSystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.finalProject.campusJobBoardSystem.model.User;
import com.finalProject.campusJobBoardSystem.repository.UserRepository;


@Service
public class AdminService {
    private final UserRepository repo;

    public AdminService(UserRepository repo) {
        this.repo = repo;
    }

    // find all users
    public List<User> findAll()
    {
        return repo.findAll();
    }

    // saves a user
    public User save(User user)
    {
        return repo.save(user);
    }

}
