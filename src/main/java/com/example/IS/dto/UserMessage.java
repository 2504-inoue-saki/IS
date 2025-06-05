package com.example.IS.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class UserMessage {
    private int id;
    private String title;
    private String text;
    private String category;
    private int userId;
    private String Account;
    private String Name;
    private int BranchId;
    private int DepartmentId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}