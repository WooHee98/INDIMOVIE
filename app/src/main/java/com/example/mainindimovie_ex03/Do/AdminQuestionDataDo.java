package com.example.mainindimovie_ex03.Do;

public class AdminQuestionDataDo {
    private int aq_id;
    String aq_title;
    String aq_regdate;
    String aq_content;
    String aq_answer;
    String u_id;


    public String getAq_regdate() {
        return aq_regdate;
    }

    public void setAq_regdate(String aq_regdate) {
        this.aq_regdate = aq_regdate;
    }

    public String getAq_title() {
        return aq_title;
    }

    public void setAq_title(String aq_title) {
        this.aq_title = aq_title;
    }

    public String getAq_content() {
        return aq_content;
    }

    public void setAq_content(String aq_content) {
        this.aq_content = aq_content;
    }

    public String getAq_answer() {
        return aq_answer;
    }

    public void setAq_answer(String aq_answer) {
        this.aq_answer = aq_answer;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }


    public int getAq_id() {
        return aq_id;
    }

    public void setAq_id(int aq_id) {
        this.aq_id = aq_id;
    }
}
