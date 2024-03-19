package com.myquizapplication.questionservice.ms.service;

import com.myquizapplication.questionservice.ms.dao.QuestionDao;
import com.myquizapplication.questionservice.ms.model.Question;
import com.myquizapplication.questionservice.ms.model.QuestionWrapper;
import com.myquizapplication.questionservice.ms.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;
    public ResponseEntity<List<Question>> getAllQuestions() {//service here is not doing any processing or like logic , its simply fetching data from DAO layer
        try {
            return new ResponseEntity<>(questionDao.getAllQuestions(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

   //----------------------------------------------------------------------------------------------------------------------------------------
    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>( questionDao.getQuestionsByCategory(category),HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    //----------------------------------------------------------------------------------------------------------------------------------------

    public ResponseEntity<String> addQuestion(Question question) {
        questionDao.saveQuestion(question);
        return new ResponseEntity<>("Question Added Successfully",HttpStatus.CREATED);
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------

    public ResponseEntity<String> updateQuestion(int id, String category, Question updatedQuestion) {
        Question existingQuestion = questionDao.fetchRequestedQuestion(id , category);
        if (existingQuestion == null) { // in case there is no question then it should return a message
            return new ResponseEntity<>("Question not found",HttpStatus.NOT_FOUND);
        }
        existingQuestion.setQUESTION(updatedQuestion.getQUESTION());
        existingQuestion.setOPTION_1(updatedQuestion.getOPTION_1());
        existingQuestion.setOPTION_2(updatedQuestion.getOPTION_2());
        existingQuestion.setOPTION_3(updatedQuestion.getOPTION_3());
        existingQuestion.setOPTION_4(updatedQuestion.getOPTION_4());
        existingQuestion.setCORRECT_ANSWER(updatedQuestion.getCORRECT_ANSWER());
        existingQuestion.setDIFFICULTY_LEVEL(updatedQuestion.getDIFFICULTY_LEVEL());

        questionDao.saveUpdatedQuestion(existingQuestion);
        return new ResponseEntity<>("Question updated successfully",HttpStatus.CREATED);
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------

    public ResponseEntity<String> deleteQuestion(int id) {
        questionDao.deleteQuestion(id);
        return new ResponseEntity<>("Question Deleted Successfully",HttpStatus.OK);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, int numOfQues) {
        List<Integer> questions = questionDao.findRandomQuestionsByCategoryFromDao(categoryName, numOfQues);
        return new ResponseEntity<>(questions,HttpStatus.OK);
    }


public ResponseEntity<List<QuestionWrapper>> getQuestionsFromIdS(List<Integer> questionIdS) {
    List<QuestionWrapper> wrappers = new ArrayList<>(); //This is because I don't want to send answers along with questions
    if (questionIdS.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    for (Integer id : questionIdS) {
        Question question = questionDao.findByID(id);
        if (question != null) {
            QuestionWrapper wrap = new QuestionWrapper();
            wrap.setQID(question.getQID());
            wrap.setQUESTION(question.getQUESTION());
            wrap.setOPTION_1(question.getOPTION_1());
            wrap.setOPTION_2(question.getOPTION_2());
            wrap.setOPTION_3(question.getOPTION_3());
            wrap.setOPTION_4(question.getOPTION_4());
            wrappers.add(wrap);
        }
    }
    return new ResponseEntity<>(wrappers, HttpStatus.OK);
}

    public ResponseEntity<Integer> getScore(List<Response> responses) {

        // 3. Iterate through each response and compare with the correct answer
        int correctCount = 0;

        for (Response response : responses) {
            Integer questionId = response.getId();
            Question question = questionDao.findByID(questionId);

            if (question != null) {
                // Extract the part after the prefix (e.g., "A. ", "B. ") from the correct answer
                String correctAnswer = question.getCORRECT_ANSWER().substring(3); // Assuming the prefix is always 3 characters long

                // Compare the submitted response with the correct answer (ignoring case and leading/trailing spaces)
                if (response.getResponse().trim().equalsIgnoreCase(correctAnswer.trim())) {
                    correctCount++;
                }
            }
        }

        // 4. Return the total number of correct responses as the result
        return new ResponseEntity<>(correctCount, HttpStatus.OK);
    }

}