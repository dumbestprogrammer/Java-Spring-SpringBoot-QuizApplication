    package com.myquizapplication.quizservice.ms.service;

    import com.myquizapplication.quizservice.ms.dao.QuizDao;
    import com.myquizapplication.quizservice.ms.feign.QuizInterface;
    import com.myquizapplication.quizservice.ms.model.QuestionWrapper;
    import com.myquizapplication.quizservice.ms.model.Quiz;
    import com.myquizapplication.quizservice.ms.model.Response;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    public class QuizService {
        @Autowired
        QuizDao quizDao;
        @Autowired
        QuizInterface quizInterface;
        

            /* since it is not possible to call method , I'll call generate req.(URL) from here ( basically another microservice i.e question service
            To do this I'll have to use RestTemplate , now this is a class and a part of spring framework , using this I can hit the generate url of question-service(ms)
            But In microservices we never know where the instances are or where it is running so we cannot depend on address (like localhost8080 ) or other machine's IPs.
            now instead of hardcoding the port we can make it work using two things - feign client and search discovery [now if we use these 2 , no need to use restTemplates]
            with the help of feign we can actually request direct to service without service name.
            Feign says that instead of me handling all the ips manually , I tell it the service i need to connect to and it will do it for me.
            First i need  to create a feign interface , i will create a class in feign package , and this file which will help me connect my quiz service to question service.
            Service Registry (eureka server ) -> here different register themselves to it ( service discovery server aka eureka server )
             */




            // Retrieve questions from another microservice using Feign
               public ResponseEntity<String> createNewQuiz(String category, int numOfQ, String title) {
                   List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numOfQ).getBody();

                   System.out.println("questions:::::::::::" + questions);

            // Creating a new Quiz entity
            Quiz quiz = new Quiz();
            quiz.setTitle(title);

            // I am Saving the Quiz entity using Spring Data JDBC and getting the generated ID
            int quizId = quizDao.saveQuizAndGetId(quiz);

            // Save quiz questions in quiz_questions table
            assert questions != null;
            quizDao.saveQuizQuestions(quizId, questions);

            return new ResponseEntity<>("The Quiz Is Created", HttpStatus.CREATED);
        }



        //-----------------------------------------------------------------------------------------------------------------------------------------------
        public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
          Quiz qz= quizDao.findById(id);
          List<Integer> qstnIds = qz.getQuestionIds();

          /* I got the question ids (question service has it all), so now I'll request my question service to give me question for these ids.
             Now Since my quiz interface is acting as a proxy between the two services, I can see that I already have a method to get questions from id.
             so I'll use that*/

            ResponseEntity<List<QuestionWrapper>> qstns =quizInterface.getQuestionsFromId(qstnIds);
            return qstns;
        }

        //----------------------------------------------------------------------------------------------------------------------------------------------------
        public ResponseEntity<Integer> calculateResult(int id, List<Response> responses) {
                   ResponseEntity<Integer> score = quizInterface.getScore(responses);

            return score;
        }


    }
