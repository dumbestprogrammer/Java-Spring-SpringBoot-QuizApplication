package com.myquizapplication.quizservice.ms.dao;

import com.myquizapplication.quizservice.ms.model.QuestionWrapper;
import com.myquizapplication.quizservice.ms.model.Quiz;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class QuizDao {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public QuizDao(DataSource dataSource){
        this.namedParameterJdbcTemplate=new NamedParameterJdbcTemplate(dataSource);
    }
    public void saveQuiz(Quiz quiz) {
        Map<String, Object> params = new HashMap<>();
        params.put("title", quiz.getTitle());

        namedParameterJdbcTemplate.update("INSERT INTO questiondb.quiz (TITLE) VALUES (:title)", params);
    }
//----------------------------------------------------------------------------------------------------------------------
    public void saveQuizQuestions(int quizId, List<Integer> questionIds) {
        for (Integer questionId : questionIds) {
            System.out.println("Inserting:::: " + questionId);
            Map<String, Object> params = new HashMap<>();
            params.put("quiz_id", quizId);
            params.put("question_id", questionId); // Ensure these parameter names match your table column names.

            try {
                namedParameterJdbcTemplate.update(
                        "INSERT INTO quizdb.quiz_questions (quiz_id, question_id) VALUES (:quiz_id, :question_id)",
                        params
                );
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
//----------------------------------------------------------------------------------------------------------------------
    public int saveQuizAndGetId(Quiz quiz) {
        Map<String, Object> params = new HashMap<>();
        params.put("title", quiz.getTitle());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update("INSERT INTO quizdb.quiz (TITLE) VALUES (:title)",
                new MapSqlParameterSource(params), keyHolder);

        return keyHolder.getKey().intValue();
    }
//----------------------------------------------------------------------------------------------------------------------
public Quiz findById(int id) {
    String sql = "SELECT q.id, q.title, qq.question_id FROM quiz q " +
            "LEFT JOIN quiz_questions qq ON q.id = qq.quiz_id " +
            "WHERE q.id = :id";
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("id", id);
    List<Quiz> quizzes = namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> {
        Quiz quiz = new Quiz();
        quiz.setId(rs.getInt("id"));
        quiz.setTitle(rs.getString("title"));

        // I am Initializing the questionIds list
        List<Integer> questionIds = new ArrayList<>();

        // now I'll Process each row in the result set
        do {
            questionIds.add(rs.getInt("question_id"));
        } while (rs.next());

        quiz.setQuestionIds(questionIds);
        return quiz;
    });

    if (!quizzes.isEmpty()) {
        // Assuming ID is unique, return the first quiz found (if any)
        return quizzes.get(0);
    } else {
        // No quiz found with the specified ID
        return null;
    }
}

//----------------------------------------------------------------------------------------------------------------------
    public List<QuestionWrapper> findQuestionsByQuizId(int id) {
        // Modify the SQL query to join quiz_questions and questions tables to get the questions
        String sql = "SELECT q.QID, q.QUESTION, q.OPTION_1, q.OPTION_2, q.OPTION_3, q.OPTION_4 " +
                "FROM questiondb.questions q " +
                "JOIN quizdb.quiz_questions qq ON q.QID = qq.question_id " +
                "WHERE qq.quiz_id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(QuestionWrapper.class));
    }
}

