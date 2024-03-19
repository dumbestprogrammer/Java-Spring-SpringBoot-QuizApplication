package com.myquizapplication.questionservice.ms.controller;
import com.myquizapplication.questionservice.ms.model.Question;
import com.myquizapplication.questionservice.ms.model.QuestionWrapper;
import com.myquizapplication.questionservice.ms.model.Response;
import com.myquizapplication.questionservice.ms.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/*This is my question controller.This annotation tells the springboot that this particular class will handle the incoming request ,and
It'll generate some response back to client . An application can've multiple controllers for different types of request. */

/* But how does it actually process the request ?
To do that we need to map the method and for that we use @RequestMapping , can be used both at class level and method level .
we use it bind the web request onto a method or onto a class .
With the help of this annotation we define a URL pattern.
 */
@RestController
@RequestMapping("question")
public class QuestionController {
    /*Now we've defined a URL pattern but how do we actually bind this method of returning all questions to a certain URL pattern ?
    For that we use  @GetMapping Annotation. It is used on the method to bind or map it to a certain URL pattern and HTTP method
     */

    /*Service layer handles the business logic and DAO layer is the once that actually talks to the database.
    So controller will ask service layer for all the questions and the service layer will forward that to that DAO layer .
    DAO will actually fetch all the questions from the database , returns it to the service layer.
    For service layer we use the annotation- @Service , for DAO we use annotation-@Repository and we autowire them using annotation-@Autowired.
     */
    @Autowired
    QuestionService questionService;

    @GetMapping("/allquestions")
    public ResponseEntity<List<Question>> getAllQuestions(){ // list because there are a lot of questions
        return questionService.getAllQuestions() ;
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    @GetMapping("/category/{category}")// since we want category, and it will change from time to time so getting a value is better than writing it manually
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);// how will service know what category I am talking about
    }


    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    //Using post method client makes the post request to this API and API will create a new resource. And to post request we use @PostMapping .
    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){ //http://localhost:8080/question
        return questionService.addQuestion(question);
    }


    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    @PutMapping("/update/{category}/{id}")//for updating we use @putMapping. Now here I am making a user to update a question oh his choice by getting the category and particular question ID
    public ResponseEntity<String> updateQuestion(@PathVariable String category, @PathVariable int id, @RequestBody Question updatedQuestion) {
        return questionService.updateQuestion(id, category, updatedQuestion);
    }


    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

     @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable int id){
        return questionService.deleteQuestion(id);
     }


     @GetMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName , @RequestParam int numOfQues){
        return questionService.getQuestionsForQuiz(categoryName,numOfQues);
     }

     @PostMapping("/getQuestions")
     public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIdS){
        return questionService.getQuestionsFromIdS(questionIdS);
     }

     //now In get score I have to calculation , the quiz service will send the responses of the quiz ( responses it got from the users) to the question service
    @PostMapping("/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }

}
