package com.finalProject.campusJobBoardSystem.repository;


import com.finalProject.campusJobBoardSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByFullName(String full_name);
}
