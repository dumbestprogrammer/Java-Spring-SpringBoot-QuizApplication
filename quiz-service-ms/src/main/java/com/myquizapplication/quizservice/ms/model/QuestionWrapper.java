package com.myquizapplication.quizservice.ms.model;

public class QuestionWrapper {
    public int getQID() {
        return QID;
    }

    public void setQID(int QID) {
        this.QID = QID;
    }

    public String getQUESTION() {
        return QUESTION;
    }

    public void setQUESTION(String QUESTION) {
        this.QUESTION = QUESTION;
    }

    public String getOPTION_1() {
        return OPTION_1;
    }

    public void setOPTION_1(String OPTION_1) {
        this.OPTION_1 = OPTION_1;
    }

    public String getOPTION_2() {
        return OPTION_2;
    }

    public void setOPTION_2(String OPTION_2) {
        this.OPTION_2 = OPTION_2;
    }

    public String getOPTION_3() {
        return OPTION_3;
    }

    public void setOPTION_3(String OPTION_3) {
        this.OPTION_3 = OPTION_3;
    }

    public String getOPTION_4() {
        return OPTION_4;
    }

    public void setOPTION_4(String OPTION_4) {
        this.OPTION_4 = OPTION_4;
    }

    private int QID;
    private String QUESTION;
    private String OPTION_1;
    private String OPTION_2;
    private String OPTION_3;
    private String OPTION_4;
    public QuestionWrapper(int QID, String QUESTION, String OPTION_1, String OPTION_2, String OPTION_3, String OPTION_4) {
        this.QID = QID;
        this.QUESTION = QUESTION;
        this.OPTION_1 = OPTION_1;
        this.OPTION_2 = OPTION_2;
        this.OPTION_3 = OPTION_3;
        this.OPTION_4 = OPTION_4;
    }
    public QuestionWrapper() {
    }


}
