package com.myquizapplication.quizservice.ms.model;

public class QuizDto {
    String category;
    int numOfQ;
    String title;
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getNumOfQ() {
        return numOfQ;
    }

    public void setNumOfQ(int numOfQ) {
        this.numOfQ = numOfQ;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
