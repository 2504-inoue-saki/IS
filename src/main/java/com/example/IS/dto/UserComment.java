package com.example.IS.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserComment {

    private int id;
    private String text;
    private int userId;
    private int messageId;
    private String Account;
    private String Name;
    private int BranchId;
    private int DepartmentId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
