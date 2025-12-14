package com.finalProject.campusJobBoardSystem.controller;

import com.finalProject.campusJobBoardSystem.model.Job;
import com.finalProject.campusJobBoardSystem.model.JobApplication;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
@ExtendWith(MockitoExtension.class)
class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JobService jobService;

    @MockitoBean
    private ApplicationService jobAppService;

    @MockitoBean
    private UserService userService;

    // test jobs()
    @Test
    @WithMockUser(roles = "STUDENT")
    void showJobs() throws Exception {
        when(jobService.findAll()).thenReturn(Arrays.asList(new Job(), new Job()));

        mockMvc.perform(get("/jobList"))
                .andExpect(status().isOk())
                .andExpect(view().name("jobList"));
    }

    // test jobDetails(id)
    @Test
    @WithMockUser(roles = "STUDENT")
    void showJobDetails_JobIdFound() throws Exception {
        Job job = new Job();
        job.setJob_id(2L);

        given(jobService.findById(2L)).willReturn(job);

        mockMvc.perform(get("/jobDetails/{id}",2L ))
                .andExpect(status().isOk())
                .andExpect(view().name("jobDetails"))
                .andExpect(model().attributeExists("job"))
                .andExpect(model().attribute("job", job));
    }

    // test jobDetails(id)
    @Test
    @WithMockUser(roles = "STUDENT")
    void showJobDetails_JobIdNotFound() throws Exception {
        mockMvc.perform(get("/jobDetails/{id}",2L ))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/jobList"));
    }

    // test apply()
    @Test
    @WithMockUser(roles = "STUDENT")
    void viewAllJobsAppliedTo() throws Exception {
        when(jobService.findAll()).thenReturn(Arrays.asList(new Job(), new Job()));

        mockMvc.perform(get("/apply"))
                .andExpect(status().isOk())
                .andExpect(view().name("apply"));
    }

    // test saveApplication()
    @Test
    @WithMockUser(roles = "STUDENT")
    void saveApplication_withCorrectInformation() throws Exception {
        Job job = new Job();
        job.setJob_id(2L);

        given(jobService.findById(2L)).willReturn(job);

        mockMvc.perform(get("/apply/{id}" , 2L ))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/jobList"));
    }
}