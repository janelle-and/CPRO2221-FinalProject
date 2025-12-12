package com.finalProject.campusJobBoardSystem.controller;

import com.finalProject.campusJobBoardSystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest(StudentController.class)
@ExtendWith(MockitoExtension.class)
class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;
//    @MockBean
    private UserService service;

    @Test
    void jobs() {
    }

    @Test
    void jobDetails() {
    }

    @Test
    void apply() {
    }

    @Test
    void saveApplication() {
    }
}