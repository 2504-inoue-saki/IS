package com.example.IS.controller.form;

import com.example.IS.groups.NewGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static com.example.IS.constFolder.ErrorMessage.*;

@Getter
@Setter

public class MessageForm {
    private int id;

    @NotBlank(message = E0006, groups = { NewGroup.class })
    @Size(max = 30, message = E0009, groups = { NewGroup.class })
    private String title;

    @NotBlank(message = E0007, groups = { NewGroup.class })
    @Size(max = 1000, message = E0010, groups = { NewGroup.class })
    private String text;

    @NotBlank(message = E0008, groups = { NewGroup.class })
    @Size(max = 10, message = E0011, groups = { NewGroup.class })
    private String category;

    private int userId;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
}
