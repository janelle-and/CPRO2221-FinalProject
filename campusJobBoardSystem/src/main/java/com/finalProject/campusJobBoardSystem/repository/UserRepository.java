package com.finalProject.campusJobBoardSystem.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalProject.campusJobBoardSystem.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // find user by email
    User findByEmail(String email);
}
