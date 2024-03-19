package com.myquizapplication.quizservice.ms.controller;

import com.myquizapplication.quizservice.ms.model.QuestionWrapper;
import com.myquizapplication.quizservice.ms.model.QuizDto;
import com.myquizapplication.quizservice.ms.model.Response;
import com.myquizapplication.quizservice.ms.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {
   @Autowired
   QuizService quizService;

    /*@PathVariable is for capturing parts of the URL path, while @RequestParam is for retrieving query parameters or form data from the URL.
     I'm using @RequestParam because that'll form the requested data(a randomly generated quiz)from URL*/
    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){
        /*                                     In JSON I'll post it like -
                                                                   {
                                                                     "category": "C",
                                                                     "numOfQ" : "5",
                                                                     "title" : "new"
                                                                      }
    via POST> http://localhost:8083/quiz-service/quiz/create                                                       */

        return quizService.createNewQuiz(quizDto.getCategory(), quizDto.getNumOfQ(), quizDto.getTitle());
    }

    @GetMapping("/get/{id}")   //http://localhost:8083/quiz-service/quiz/get/36
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(@PathVariable int id){ //now I need to create a question wrapper (in model package) that has all the columns except the correct answers column
       return quizService.getQuizQuestions(id);
    }

    @PostMapping("/submit/{id}")  //http://localhost:8083/quiz-service/quiz/submit/37
    public ResponseEntity<Integer> submitQuiz(@PathVariable int id , @RequestBody List<Response> responses){
        return  quizService.calculateResult(id,responses);
    }
}
// HERE localhost:8083 Is the API Gateway's server port,quiz-service is the route defined in gateway and rest is mapping.