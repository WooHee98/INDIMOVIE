package com.example.mainindimovie_ex03.Do;

public class NoticeDataDo {
    private String n_id; //공지사항번호
    private String n_regdate; //공지사항 날짜
    private String n_title; //공지사항 제목
    private String n_content; //공지사항 내용


    public NoticeDataDo() {
        this.n_id = "None";
        this.n_regdate = "None";
        this.n_title = "None";
        this.n_content = "None";
    }

    public NoticeDataDo(String n_id, String n_regdate, String n_title , String n_content) {
        this.n_id = n_id;
        this.n_regdate = n_regdate;
        this.n_title = n_title;
        this.n_content = n_content;
    }

    public String getN_id() {
        return n_id;
    }

    public void setN_id(String n_id) {
        this.n_id = n_id;
    }

    public String getN_regdate() {
        return n_regdate;
    }

    public void setN_regdate(String n_regdate) {
        this.n_regdate = n_regdate;
    }

    public String getN_title() {
        return n_title;
    }

    public void setN_title(String n_title) {
        this.n_title = n_title;
    }

    public String getN_content() {
        return n_content;
    }

    public void setN_content(String n_content) {
        this.n_content = n_content;
    }

    @Override
    public String toString() {
        return "NoticeDataDo{" +
                "n_id='" + n_id + '\'' +
                ", n_regdate='" + n_regdate + '\'' +
                ", n_title='" + n_title + '\'' +
                ", n_content='" + n_content + '\'' +
                '}';
    }
}
