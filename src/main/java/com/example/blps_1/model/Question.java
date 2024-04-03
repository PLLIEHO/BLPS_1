package com.example.blps_1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    @NotBlank(message = "question title is mandatory")
    @NotEmpty(message = "question title can't be empty")
    private String questionTitle;

    @Column(name = "description")
    @NotBlank(message = "question description is mandatory")
    @NotEmpty(message = "question description can't be empty")
    private String questionDescription;

    @Column(name = "rating")
    private int rating;

    @ManyToMany
    List<Tag> tags;

    @OneToMany
    @JoinColumn(name = "question_id")
    List<Review> reviews = new ArrayList<>();
}
