package com.myquizapplication.quizservice.ms.feign;

import com.myquizapplication.quizservice.ms.model.QuestionWrapper;
import com.myquizapplication.quizservice.ms.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//along with annotating we have to mention what service I need to connect to i.e the name of the service
@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {   // NOW I NEED TO MENTION WHAT SERVICES(methods) from question service I WANT

    @GetMapping("/getCorrectAnswer")
    ResponseEntity<String> getCorrectAnswerByQuestionId(@RequestParam("questionId") int questionId);


//since this is an interface we won't define methods , just declare them

    @GetMapping("/question/generate")
    ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam("categoryName") String categoryName, @RequestParam("numOfQues") int numOfQues);

    @PostMapping("question/getQuestions")
    ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIdS);

    @PostMapping("question/getScore")
    ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);
}
