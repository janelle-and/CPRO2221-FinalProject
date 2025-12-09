package com.finalProject.campusJobBoardSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class JobApplication {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long application_id;

    @NotBlank
    public enum Status{
        SUBMITTED,
        APPROVED,
        REJECTED
    }

    @Enumerated(EnumType.STRING)
    private Status status=Status.SUBMITTED;

    private Timestamp start_time =  new Timestamp(System.currentTimeMillis());

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job_id;

    @ManyToMany
    @JoinTable(
            name = "students",
            joinColumns = @JoinColumn(name = "application_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> student_id = new ArrayList<>();

    // constructors
    public JobApplication() {
    }

    public JobApplication(Long application_id, Status status, Timestamp start_time, Job job_id, List<User> student_id) {
        this.application_id = application_id;
        this.status = status;
        this.start_time = start_time;
        this.job_id = job_id;
        this.student_id = student_id;
    }

    // getters and setters
    public Long getApplication_id() {
        return application_id;
    }

    public void setApplication_id(Long application_id) {
        this.application_id = application_id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Timestamp getStart_time() {
        return start_time;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    public Job getJob_id() {
        return job_id;
    }

    public void setJob_id(Job job_id) {
        this.job_id = job_id;
    }

    public List<User> getStudent_id() {
        return student_id;
    }

    public void setStudent_id(List<User> student_id) {
        this.student_id = student_id;
    }
}
