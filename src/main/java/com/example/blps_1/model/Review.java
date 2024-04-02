package com.example.blps_1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;


import java.sql.Timestamp;

@Entity
@Table(name = "review")
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "author")
    private String author;

    @Column(name = "text")
    @Size(max = 254, message = "text too long")
    private String text;

    @Column(name = "timestamp")
    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    @Column(name = "rating")
    private int rating;

    @JsonIgnore
    @Column(name = "question_id")
    long questionId;
}
