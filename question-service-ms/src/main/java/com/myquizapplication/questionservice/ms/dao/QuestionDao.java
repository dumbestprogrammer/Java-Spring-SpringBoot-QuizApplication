package com.myquizapplication.questionservice.ms.dao;

import com.myquizapplication.questionservice.ms.model.Question;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class QuestionDao {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate; // here NamedParameterJdbcTemplate is a spring class hat simplifies database access and allows me to execute SQL queries

    public QuestionDao(DataSource dataSource){
        this.namedParameterJdbcTemplate=new NamedParameterJdbcTemplate(dataSource);
    }
    public List<Question> getAllQuestions(){
            String sql = "SELECT * FROM questiondb.questions";
            return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Question.class));
    }

    public List<Question> getQuestionsByCategory(String category) {
        String sql = "SELECT * FROM questiondb.questions WHERE CATEGORY = :category";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("category", category);
        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Question.class));
    }

    public void saveQuestion(Question question) {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("CATEGORY", question.getCATEGORY());
        arguments.put("QUESTION", question.getQUESTION());
        arguments.put("OPTION_1", question.getOPTION_1());
        arguments.put("OPTION_2", question.getOPTION_2());
        arguments.put("OPTION_3", question.getOPTION_3());
        arguments.put("OPTION_4", question.getOPTION_4());
        arguments.put("CORRECT_ANSWER", question.getCORRECT_ANSWER());
        arguments.put("DIFFICULTY_LEVEL", question.getDIFFICULTY_LEVEL());

        namedParameterJdbcTemplate.update(
                "INSERT INTO questiondb.questions (CATEGORY, QUESTION, OPTION_1, OPTION_2, OPTION_3, OPTION_4, CORRECT_ANSWER, DIFFICULTY_LEVEL) " +
                        "VALUES (:CATEGORY, :QUESTION, :OPTION_1, :OPTION_2, :OPTION_3, :OPTION_4, :CORRECT_ANSWER, :DIFFICULTY_LEVEL)", arguments );
    }



    public Question fetchRequestedQuestion(int id, String category) {
        String sql = "SELECT * FROM questiondb.questions WHERE CATEGORY = :category AND QID = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("category", category);

        return namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(Question.class));
    }

    public void saveUpdatedQuestion(Question existingQuestion) {
        Map<String, Object> argu = new HashMap<>();
        argu.put("updatedQuestion", existingQuestion.getQUESTION());
        argu.put("updatedOption1", existingQuestion.getOPTION_1());
        argu.put("updatedOption2", existingQuestion.getOPTION_2());
        argu.put("updatedOption3", existingQuestion.getOPTION_3());
        argu.put("updatedOption4", existingQuestion.getOPTION_4());
        argu.put("updatedCorrectAnswer", existingQuestion.getCORRECT_ANSWER());
        argu.put("updatedDifficultyLevel", existingQuestion.getDIFFICULTY_LEVEL());
        argu.put("id", existingQuestion.getQID()); // Adding QID because if i dont then while updating question , all the questions in th db will become same

        namedParameterJdbcTemplate.update("UPDATE questiondb.questions " +
                "SET " +
                "QUESTION = :updatedQuestion, " +
                "OPTION_1 = :updatedOption1, " +
                "OPTION_2 = :updatedOption2, " +
                "OPTION_3 = :updatedOption3, " +
                "OPTION_4 = :updatedOption4, " +
                "CORRECT_ANSWER = :updatedCorrectAnswer, " +
                "DIFFICULTY_LEVEL = :updatedDifficultyLevel " +
                "WHERE QID = :id", argu);
    }

    public void deleteQuestion(int id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        namedParameterJdbcTemplate.update("DELETE FROM questiondb.questions WHERE QID = :id", params);
    }

    public List<Integer> findRandomQuestionsByCategoryFromDao(String category, int numOfQ) {
        String sql = "SELECT QID FROM questiondb.questions WHERE CATEGORY = :category ORDER BY RAND() LIMIT :numOfQ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("category", category);
        params.addValue("numOfQ", numOfQ);

        return namedParameterJdbcTemplate.queryForList(sql, params, Integer.class);
    }

    // Adding a method to fetch questions by quiz ID
    public List<Question> findQuestionsByQuizId(int quizId) {
        String sql = "SELECT * FROM questiondb.questions WHERE QID IN (SELECT QUESTION_ID FROM questiondb.quiz_questions WHERE QUIZ_ID = :quizId)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("quizId", quizId);

        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Question.class));
    }

    public Question findByID(Integer id) {
        String sql = "SELECT * FROM questiondb.questions WHERE QID = :id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        List<Question> questions = namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Question.class));

        if (!questions.isEmpty()) {
            // Assuming ID is unique, return the first question found (if any)
            return questions.get(0);
        } else {
            // No question found with the specified ID
            return null;
        }
    }

}
