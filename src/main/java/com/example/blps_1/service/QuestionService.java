package com.example.blps_1.service;

import com.example.blps_1.DTO.NewQuestionDTO;
import com.example.blps_1.DTO.UpdateQuestionDTO;
import com.example.blps_1.model.Question;
import com.example.blps_1.model.Tag;
import com.example.blps_1.repository.QuestionRepository;
import com.example.blps_1.repository.TagRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final TagRepository tagRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, TagRepository tagRepository) {
        this.questionRepository = questionRepository;
        this.tagRepository = tagRepository;
    }

    public List<Question> allQuestions() {
        return questionRepository.findAll();
    }

    public Question findById(long id) {
        var question = questionRepository.findById(id);
        return question.orElse(null);
    }

    @Transactional
    public Map<String, String> saveQuestion(NewQuestionDTO newquestion) {
        newquestion.setQuestionTitle(newquestion.getQuestionTitle().strip());
        newquestion.setQuestionDescription(newquestion.getQuestionDescription().strip());

        var questionFromDB = questionRepository.findByQuestionTitle(newquestion.getQuestionTitle());
        if (questionFromDB != null) {
            return Map.of("message", "question already exist");
        }

        if (newquestion.getTags() == null) {
            newquestion.setTags(new ArrayList<>());
        }

        var questionTags = checkTags(newquestion.getTags());
        if (questionTags == null) {
            return Map.of("message", "invalid tags list");
        }

        var question = new Question();
        question.setQuestionTitle(newquestion.getQuestionTitle());
        question.setQuestionDescription(newquestion.getQuestionDescription());
        question.setTags(questionTags);

        if (question.getReviews() == null) {
            question.setReviews(new ArrayList<>());
        }

        questionRepository.save(question);
        return Map.of("message", "success");
    }

    @Transactional
    public Map<String, String> updateQuestion(long id, UpdateQuestionDTO updatequestion) {
        var questionFromDB = questionRepository.findById(id).orElse(null);
        if (questionFromDB == null) {
            return Map.of("message", "no such question");
        }

        if (updatequestion.getQuestionDescription() != null) {
            questionFromDB.setQuestionDescription(updatequestion.getQuestionDescription());
        }

        if (updatequestion.getTags() != null) {
            var questionTags = checkTags(updatequestion.getTags());
            if (questionTags == null) {
                return Map.of("message", "some tags are incorrect");
            }
            questionFromDB.setTags(questionTags);
        }

        questionRepository.save(questionFromDB);
        return Map.of("message", "success");
    }

    @Transactional
    public Map<String, String> updateQuestionRating(long id, boolean flag){
        var questionFromDB = questionRepository.findById(id).orElse(null);
        if (questionFromDB == null) {
            return Map.of("message", "no such question");
        }

        if(flag){
            questionFromDB.setRating(questionFromDB.getRating()+1);
        }
        else {
            questionFromDB.setRating(questionFromDB.getRating()-1);
        }

        questionRepository.save(questionFromDB);
        return Map.of("message", "success");
    }

    private List<Tag> checkTags(List<Long> tags) {
        List<Tag> questionTags = new ArrayList<>();
        for (var tagId : tags) {
            var tagFromDB = tagRepository.findById(tagId).orElse(null);
            questionTags.add(tagFromDB);
            if (tagFromDB == null) {
                return null;
            }
        }
        return questionTags;
    }

    @Transactional
    public void deleteById(long id) {
        questionRepository.deleteById(id);
    }
}