package com.finalProject.campusJobBoardSystem.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import jdk.jfr.Timespan;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table
public class Job {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long job_id;

    @NotBlank
    @Size(max=100)
    private String title;

    @NotBlank
    private String description;

    @Size(max=100)
    private String location;

    private double salary;

    @Size(max=50)
    private String category;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadline;

    public enum Status{
        PENDING,
        APPROVED,
        REJECTED
    }

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;


    private Timestamp created_at =  new  Timestamp(System.currentTimeMillis());

    private Timestamp updated_at =   new Timestamp(System.currentTimeMillis());

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User employer_id;

    @OneToMany(mappedBy = "job_id", cascade = CascadeType.ALL)
    private List<JobApplication> job_applications = new ArrayList<>();

    // constructors
    public Job() {
    }

    public Job(Long job_id, String title, String description, String location, double salary, String category, Date deadline, Status status, Timestamp created_at, Timestamp updated_at, User employer_id) {
        this.job_id = job_id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.salary = salary;
        this.category = category;
        this.deadline = deadline;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.employer_id = employer_id;
    }

    // getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getJob_id() {
        return job_id;
    }

    public void setJob_id(Long job_id) {
        this.job_id = job_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
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

    public User getEmployer_id() {
        return employer_id;
    }

    public void setEmployer_id(User employer_id) {
        this.employer_id = employer_id;
    }
}
