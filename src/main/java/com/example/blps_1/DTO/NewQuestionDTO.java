package com.example.blps_1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewQuestionDTO {
    @NotBlank(message = "question title is mandatory")
    @NotEmpty(message = "question title can't be empty")
    private String questionTitle;

    @NotBlank(message = "movie description is mandatory")
    @NotEmpty(message = "movie description can't be empty")
    private String questionDescription;

    private List<Long> tags = new ArrayList<>();
}


