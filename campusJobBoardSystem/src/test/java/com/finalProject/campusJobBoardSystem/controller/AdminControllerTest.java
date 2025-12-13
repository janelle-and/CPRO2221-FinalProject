package com.finalProject.campusJobBoardSystem.controller;

import com.finalProject.campusJobBoardSystem.model.Job;
import com.finalProject.campusJobBoardSystem.model.User;
import com.finalProject.campusJobBoardSystem.service.JobService;
import com.finalProject.campusJobBoardSystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JobService jobService;

    @MockitoBean
    private UserService userService;

    // test users()
    @Test
    @WithMockUser(roles = "ADMIN")
    void showAllUsers() throws Exception {
        List<User> users = Arrays.asList(new User(), new User(), new User());

        given(userService.findAll()).willReturn(users);

        mockMvc.perform(get("/userManagement"))
                .andExpect(status().isOk())
                .andExpect(view().name("userManagement"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", users));
    }

    // test activateUser()
    @Test
    @WithMockUser(roles = "ADMIN")
    void activateUser_redirectsToUserManagement() throws Exception {
        User user = new User();
        user.setUser_id(2L);

        given(userService.findById(2L)).willReturn(user);

        mockMvc.perform(get("/userManagement/activate/{id}",2L ))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/userManagement"));
    }

    // test deactivateUser()
    @Test
    @WithMockUser(roles = "ADMIN")
    void deactivateUser_redirectsToUserManagement() throws Exception {
        User user = new User();
        user.setUser_id(2L);

        given(userService.findById(2L)).willReturn(user);

        mockMvc.perform(get("/userManagement/activate/{id}",2L ))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/userManagement"));
    }

    // test jobs()
    @Test
    @WithMockUser(roles = "ADMIN")
    void showAllJobs() throws Exception {
        List<Job> jobs = Arrays.asList(new Job(), new Job(), new Job());

        given(jobService.findAll()).willReturn(jobs);

        mockMvc.perform(get("/jobApproval"))
                .andExpect(status().isOk())
                .andExpect(view().name("jobApproval"))
                .andExpect(model().attributeExists("jobs"))
                .andExpect(model().attribute("jobs", jobs));
    }

    // test approveJob()
    @Test
    @WithMockUser(roles = "ADMIN")
    void approveJob_redirectsToJobApproval() throws Exception {
        Job job = new Job();
        job.setJob_id(2L);

        given(jobService.findById(2L)).willReturn(job);

        mockMvc.perform(get("/jobApproval/approve/{id}",2L ))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/jobApproval"));
    }

    // test rejectJob()
    @Test
    @WithMockUser(roles = "ADMIN")
    void rejectJob_redirectsToJobApproval() throws Exception {
        Job job = new Job();
        job.setJob_id(2L);

        given(jobService.findById(2L)).willReturn(job);

        mockMvc.perform(get("/jobApproval/reject/{id}",2L ))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/jobApproval"));
    }
}