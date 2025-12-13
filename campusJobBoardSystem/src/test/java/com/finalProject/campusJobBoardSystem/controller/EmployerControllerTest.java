package com.finalProject.campusJobBoardSystem.controller;

import com.finalProject.campusJobBoardSystem.model.Job;
import com.finalProject.campusJobBoardSystem.model.User;
import com.finalProject.campusJobBoardSystem.service.ApplicationService;
import com.finalProject.campusJobBoardSystem.service.JobService;
import com.finalProject.campusJobBoardSystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployerController.class)
class EmployerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JobService jobService;

    @MockitoBean
    private ApplicationService jobAppService;

    @MockitoBean
    private UserService userService;

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void jobsListDisplay() throws Exception {
        Mockito.when(jobService.findAll()).thenReturn(Arrays.asList(new Job(), new Job()));

        mockMvc.perform(get("/myJobs"))
                .andExpect(status().isOk())
                .andExpect(view().name("myJobs"))
                .andExpect(model().attributeExists("jobs"));
    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void showNewForm() throws Exception {
        mockMvc.perform(get("/myJobs/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("editJob"));
    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void saveJobWithIncorrectData() throws Exception {
        User user = new User();

        mockMvc.perform(post("/myJobs/save")
                .param("title", "title")
                .param("description", "description")
                .param("employer_id",user.toString())
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("editJob"));
    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void editJob() throws Exception {
    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void deleteJob() throws Exception {
    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void jobApplications() throws Exception {
    }
}