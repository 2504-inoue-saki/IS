package com.example.IS.controller.form;

import com.example.IS.groups.AddGroup;
import com.example.IS.groups.EditGroup;
import com.example.IS.groups.LoginGroup;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static com.example.IS.constFolder.ErrorMessage.*;

@Getter
@Setter

public class UserForm {
    private int id;

    @NotEmpty(message = E0001, groups = { LoginGroup.class })
    @NotEmpty(message = E0013, groups = { AddGroup.class, EditGroup.class })
    @Size(min = 6, max = 20, message = E0014, groups = { AddGroup.class, EditGroup.class })
    @Pattern(regexp="^[0-9a-zA-Z]+$", message = E0014, groups = { AddGroup.class, EditGroup.class })
    private String account;

    @NotEmpty(message = E0002, groups = { LoginGroup.class })
    @NotEmpty(message = E0016, groups = { AddGroup.class })
    @Size(min = 6, max = 20, message = E0017, groups = { AddGroup.class })
    @Pattern(regexp="^[!-~]+$", message = E0017, groups = { AddGroup.class })
    private String password;

    @NotEmpty(message = E0019, groups = { AddGroup.class, EditGroup.class })
    @Size(max = 10, message = E0020, groups = { AddGroup.class, EditGroup.class })
    private String name;

    @Min(value = 1, message = E0021, groups = { AddGroup.class, EditGroup.class })
    private int branchId;

    @Min(value = 1, message = E0022, groups = { AddGroup.class, EditGroup.class })
    private int departmentId;

    private int isStopped;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private String checkPassword;

    private String branchName;

    private String departmentName;
    private String checkId;
}
