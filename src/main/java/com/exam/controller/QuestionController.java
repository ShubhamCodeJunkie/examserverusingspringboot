package com.exam.controller;

import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
import com.exam.service.QuestionService;
import com.exam.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/question")
@CrossOrigin("*")
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private  QuizService quizService;

    @PostMapping("/")
    public ResponseEntity<?> addquestion(@RequestBody Question question){
        Question question1 = this.questionService.addQuestion(question);
        return ResponseEntity.ok(question1);
    }

    @GetMapping("/{questionId}")
    public Question getquestion(@PathVariable("questionId") Long questionId){
        return this.questionService.getQuestion(questionId);
    }

    @GetMapping("/")
    public ResponseEntity<?> getquestions(){
        return  ResponseEntity.ok(this.questionService.getQuestions());
    }
    @PutMapping("/")
    public Question updatequation(@RequestBody Question question){
        return this.questionService.updateQuestion(question);
    }

    @GetMapping("/quiz/{qid}")
    public ResponseEntity<?> getQuestionsOfQuiz(@PathVariable("qid") Long qid){
        Quiz quiz = this.quizService.getQuiz(qid);
        Set<Question> questions = quiz.getQuestions();
        List list = new ArrayList(questions);
        if(list.size() > Integer.parseInt(quiz.getNumberOfQuestions())){
            list = list.subList(0,Integer.parseInt(quiz.getNumberOfQuestions()+1));
        }
        Collections.shuffle(list);
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{questionId}")
    public void deleteCategory(@PathVariable("questionId") Long questionId)
    {
        this.questionService.deleteQuestion(questionId);
    }
}
