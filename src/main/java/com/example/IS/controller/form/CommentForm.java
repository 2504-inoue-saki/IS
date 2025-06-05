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

public class CommentForm {
    private int id;

    @NotBlank(message = E0004)
    @Size(max = 500, message = E0005)
    private String text;
    private int userId;
    private int messageId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
