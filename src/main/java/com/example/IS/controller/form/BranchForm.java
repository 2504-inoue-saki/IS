package com.example.IS.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class BranchForm {
    private int id;
    private String name;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
}
