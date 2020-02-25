package com.example.mainindimovie_ex03.Do;

public class MovieReviewDataDo {

    String mr_id; // 영화리뷰 번호
    String mr_mtitle; //영화 제목
    String mr_mimage; //영화 이미지
    String mr_nick; //영화리뷰 닉네임(User에서 가져와야함)
    String mr_content;// 영화리뷰 내용
    String mr_regdate; //영화 리뷰쓴 날짜
    String mr_icon;// 영화 아이콘

    public MovieReviewDataDo(String mr_id, String mr_mtitle, String mr_mimage, String mr_nick, String mr_content, String mr_regdate) {
        this.mr_mtitle = mr_mtitle;
        this.mr_mimage = mr_mimage;
        this.mr_id = mr_id;
        this.mr_nick = mr_nick;
        this.mr_content = mr_content;
        this.mr_regdate = mr_regdate;

    }

    public MovieReviewDataDo() {
        this.mr_mtitle = "None";
        this.mr_mimage = "None";
        this.mr_id = "None";
        this.mr_nick = "None";
        this.mr_content = "None";
        this.mr_regdate = "None";

    }

    public String getMr_icon() {
        return mr_icon;
    }

    public void setMr_icon(String mr_icon) {
        this.mr_icon = mr_icon;
    }

    public String getMr_id() {
        return mr_id;
    }

    public void setMr_id(String mr_id) {
        this.mr_id = mr_id;
    }

    public String getMr_mtitle() {
        return mr_mtitle;
    }

    public void setMr_mtitle(String mr_mtitle) {
        this.mr_mtitle = mr_mtitle;
    }

    public String getMr_mimage() {
        return mr_mimage;
    }

    public void setMr_mimage(String mr_mimage) {
        this.mr_mimage = mr_mimage;
    }

    public String getMr_nick() {
        return mr_nick;
    }

    public void setMr_nick(String mr_nick) {
        this.mr_nick = mr_nick;
    }

    public String getMr_content() {
        return mr_content;
    }

    public void setMr_content(String mr_content) {
        this.mr_content = mr_content;
    }

    public String getMr_regdate() {
        return mr_regdate;
    }

    public void setMr_regdate(String mr_regdate) {
        this.mr_regdate = mr_regdate;
    }
}
