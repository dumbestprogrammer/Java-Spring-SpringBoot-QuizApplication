package com.myquizapplication.quizservice.ms.model;

import java.util.List;

public class Quiz {

    private int id ; //id of the quiz
    private String title;

    //------------------------------------------------------------------------------------------------------------------
    private List<Integer> questionIds;
    public List<Integer> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(List<Integer> questionIds) {
        this.questionIds = questionIds;
    }

//----------------------------------------------------------------------------------------------------------------------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}

