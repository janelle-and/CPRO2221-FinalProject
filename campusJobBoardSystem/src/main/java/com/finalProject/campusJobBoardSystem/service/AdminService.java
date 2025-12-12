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

    public List<User> findAllAdmins()
    {
        return repo.findAll();
    }

    public User save(User user)
    {
        return repo.save(user);
    }

}
