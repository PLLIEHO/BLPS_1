package com.example.blps_1.controller;

import com.example.blps_1.DTO.NewQuestionDTO;
import com.example.blps_1.DTO.UpdateQuestionDTO;
import com.example.blps_1.model.Question;
import com.example.blps_1.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/question")
    public Map<String, String> addNewQuestion(@Valid @RequestBody NewQuestionDTO question) {
        return questionService.saveQuestion(question);
    }

    @GetMapping("/question")
    public List<Question> getAll() {
        return questionService.allQuestions();
    }


    @GetMapping("/question/{id}")
    public Question getById(@PathVariable("id") long id) {
        return questionService.findById(id);
    }

    @PatchMapping("/question/{id}")
    public Map<String, String> updateById(@PathVariable("id") long id, @RequestBody UpdateQuestionDTO question) {
        return questionService.updateQuestion(id, question);
    }

    @PatchMapping("/question/{id}/{rate}")
    public Map<String, String> rateById(@PathVariable("id") long id, @PathVariable("rate") boolean flag) {
        return questionService.updateQuestionRating(id, flag);
    }

    @DeleteMapping("/question/{id}")
    public Map<String, String> delete(@PathVariable("id") long id) {
        questionService.deleteById(id);
        return Map.of("message", "success");
    }
}

