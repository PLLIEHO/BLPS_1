package com.example.blps_1.service;


import com.example.blps_1.model.Review;
import com.example.blps_1.repository.QuestionRepository;
import com.example.blps_1.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final QuestionRepository questionRepository;


    @Autowired
    public ReviewService(ReviewRepository reviewRepository, QuestionRepository questionRepository) {
        this.reviewRepository = reviewRepository;
        this.questionRepository = questionRepository;
    }

    public Map<String, String> saveReview(long questionId, Review review) {

        var questionFromDB = questionRepository.findById(questionId).orElse(null);
        if (questionFromDB == null) {
            return Map.of("message", "question not found");
        }

        review.setQuestionId(questionFromDB.getId());
        reviewRepository.save(review);

        return Map.of("message", "success");
    }

    @Transactional
    public Map<String, String> updateReviewRating(long id, boolean flag){
        var reviewFromDB = reviewRepository.findById(id).orElse(null);
        if (reviewFromDB == null) {
            return Map.of("message", "no such review");
        }

        if(flag){
            reviewFromDB.setRating(reviewFromDB.getRating()+1);
        }
        else {
            reviewFromDB.setRating(reviewFromDB.getRating()-1);
        }

        reviewRepository.save(reviewFromDB);
        return Map.of("message", "success");
    }
}

