package com.finalProject.campusJobBoardSystem.controller;

import com.finalProject.campusJobBoardSystem.model.Job;
import com.finalProject.campusJobBoardSystem.model.JobApplication;
import com.finalProject.campusJobBoardSystem.model.User;
import com.finalProject.campusJobBoardSystem.repository.JobRepository;
import com.finalProject.campusJobBoardSystem.service.ApplicationService;
import com.finalProject.campusJobBoardSystem.service.JobService;
import com.finalProject.campusJobBoardSystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
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

    // test jobs()
    @Test
    @WithMockUser(roles = "EMPLOYER")
    void jobsListDisplay() throws Exception {
        when(jobService.findAll()).thenReturn(Arrays.asList(new Job(), new Job()));

        mockMvc.perform(get("/myJobs"))
                .andExpect(status().isOk())
                .andExpect(view().name("myJobs"))
                .andExpect(model().attributeExists("jobs"));
    }

    // test showForm()
    @Test
    @WithMockUser(roles = "EMPLOYER")
    void showNewForm() throws Exception {
        mockMvc.perform(get("/myJobs/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("editJob"));
    }

    // test saveJob()
    @Test
    @WithMockUser(roles = "EMPLOYER")
    void saveJobWithIncorrectData() throws Exception {
        mockMvc.perform(post("/myJobs/save")
                .param("title", "title")
                .param("description", "description")
                .param("employer_id","employer")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("editJob"));
    }

    // test editJob()
    @Test
    @WithMockUser(roles = "EMPLOYER")
    void saveJobWithCorrectData() throws Exception {
        User user = new User();
        user.setUser_id(1L);

        mockMvc.perform(post("/myJobs/save")
                        .param("title", "title")
                        .param("description", "description")
                        .flashAttr("user", user)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/myJobs"));
    }

    // test editJob()
    @Test
    @WithMockUser(roles = "EMPLOYER")
    void editJobForm_JobIdFound() throws Exception {
        Job job = new Job();
        job.setJob_id(2L);

        given(jobService.findById(2L)).willReturn(job);

        mockMvc.perform(get("/myJobs/edit/{id}",2L ))
                .andExpect(status().isOk())
                .andExpect(view().name("editJob"))
                .andExpect(model().attributeExists("job"))
                .andExpect(model().attribute("job", job));
    }

    // test editJob()
    @Test
    @WithMockUser(roles = "EMPLOYER")
    void editJobForm_NoJobIdFound() throws Exception {
        mockMvc.perform(get("/myJobs/edit/{id}",2L ))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/myJobs"));
    }

    // test deleteJob()
    @Test
    @WithMockUser(roles = "EMPLOYER")
    void deleteAJobByIdSuccessfully() throws Exception {
        Job job = new Job();
        job.setJob_id(2L);

        given(jobService.findById(2L)).willReturn(job);

        mockMvc.perform(get("/myJobs/delete/{id}",2L )
                .param("_method", "delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/myJobs"));;
    }

    // test jobApplications()
    @Test
    @WithMockUser(roles = "EMPLOYER")
    void showStudentsWhoFilledOutAJobApplications() throws Exception {
        mockMvc.perform(get("/viewApplicants"))
                .andExpect(status().isOk())
                .andExpect(view().name("viewApplicants"))
                .andExpect(model().attributeExists("students"));
    }
}