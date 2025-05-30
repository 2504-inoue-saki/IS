package com.example.IS.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class UserForm {
    private int id;
    private String account;
    private String password;
    private String name;
    private int branchId;
    private int departmentId;
    private int isStopped;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String checkPassword;

    private String branchName;
    private String departmentName;
}
