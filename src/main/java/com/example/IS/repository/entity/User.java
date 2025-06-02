package com.example.IS.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(updatable = false)
    private String account;

    @Column
    private String password;

    @Column(updatable = false)
    private String name;

    @Column(updatable = false)
    private int branchId;

    @Column(updatable = false)
    private int departmentId;

    @Column
    private int isStopped;

    @Column(updatable = false)
    private LocalDateTime createdDate;

    @Column(updatable = false)
    private LocalDateTime updatedDate;
}
