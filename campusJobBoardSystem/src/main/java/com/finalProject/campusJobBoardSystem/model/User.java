package com.finalProject.campusJobBoardSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @NotBlank
    @Size(max=100)
    private String full_name;

    @NotBlank
    @Email
    @Size(max=100)
    private String email;

    @NotBlank
    @Size(max=255)
    private String password;


    public enum Role {
        STUDENT,
        EMPLOYER,
        ADMIN
    }

    @NotBlank
    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Status {
        ACTIVE,
        INACTIVE
    }

    @Enumerated(EnumType.STRING)
    private Status status =  Status.ACTIVE;

    @DateTimeFormat
    private Timestamp created_at =  new Timestamp(System.currentTimeMillis());

    @DateTimeFormat
    private Timestamp updated_at =   new Timestamp(System.currentTimeMillis());

    @OneToOne(mappedBy = "employer_id", cascade = CascadeType.ALL)
    private Job job;

    @ManyToMany(mappedBy = "student_id")
    private List<JobApplication> job_application = new ArrayList<>();

    public User() {
    }

    public User(Long user_id, String full_name, String email, String password, Role role, Status status, Timestamp created_at, Timestamp updated_at, Job job, List<JobApplication> job_application) {
        this.user_id = user_id;
        this.full_name = full_name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.job = job;
        this.job_application = job_application;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public List<JobApplication> getJob_application() {
        return job_application;
    }

    public void setJob_application(List<JobApplication> job_application) {
        this.job_application = job_application;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
}
