package com.example.mainindimovie_ex03.Do;

public class MovieDataDo {

    String lm_id; //위시리스트 번호

    String m_id; // 영화 번호
    String m_title; //영화 제목
    String m_title_id; //영화 제목
    String m_jang;// 영화 장르
    String m_runtime; // 상영시간
    String m_regdate; //개봉일
    String m_class;//영화 등급
    String m_class_id;
    String m_director; // 영화 감독
    String m_actor; //영화 출연자
    String m_scenario; //영화 줄거리
    String m_image_url; // 영화 이미지
    String m_url; // 영화 url


    public MovieDataDo() {
        this.m_id = "None";
        this.m_title = "None";
        this.m_jang = "None";
        this.m_runtime = "None";
        this.m_regdate = "None";
        this.m_class = "None";
        this.m_director = "None";
        this.m_actor = "None";
        this.m_scenario = "None";
        this.m_image_url = "None";
        this.m_url = "None";

    }


    public String getM_title_id() {
        return m_title_id;
    }

    public void setM_title_id(String m_title_id) {
        this.m_title_id = m_title_id;
    }

    public String getM_class_id() {
        return m_class_id;
    }

    public void setM_class_id(String m_class_id) {
        this.m_class_id = m_class_id;
    }

    public String getLm_id() {
        return lm_id;
    }

    public void setLm_id(String lm_id) {
        this.lm_id = lm_id;
    }

    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

    public String getM_title() {
        return m_title;
    }

    public void setM_title(String m_title) {
        this.m_title = m_title;
    }

    public String getM_jang() {
        return m_jang;
    }

    public void setM_jang(String m_jang) {
        this.m_jang = m_jang;
    }

    public String getM_runtime() {
        return m_runtime;
    }

    public void setM_runtime(String m_runtime) {
        this.m_runtime = m_runtime;
    }

    public String getM_regdate() {
        return m_regdate;
    }

    public void setM_regdate(String m_regdate) {
        this.m_regdate = m_regdate;
    }

    public String getM_class() {
        return m_class;
    }

    public void setM_class(String m_class) {
        this.m_class = m_class;
    }

    public String getM_director() {
        return m_director;
    }

    public void setM_director(String m_director) {
        this.m_director = m_director;
    }

    public String getM_actor() {
        return m_actor;
    }

    public void setM_actor(String m_actor) {
        this.m_actor = m_actor;
    }

    public String getM_scenario() {
        return m_scenario;
    }

    public void setM_scenario(String m_scenario) {
        this.m_scenario = m_scenario;
    }

    public String getM_image_url() {
        return m_image_url;
    }

    public void setM_image_url(String m_image_url) {
        this.m_image_url = m_image_url;
    }

    public String getM_url() {
        return m_url;
    }

    public void setM_url(String m_url) {
        this.m_url = m_url;
    }

    @Override
    public String toString() {
        return "MovieDataDo{" +
                "m_id='" + m_id + '\'' +
                ", m_title='" + m_title + '\'' +
                ", m_jang='" + m_jang + '\'' +
                ", m_runtime='" + m_runtime + '\'' +
                ", m_regdate='" + m_regdate + '\'' +
                ", m_class='" + m_class + '\'' +
                ", m_director='" + m_director + '\'' +
                ", m_actor='" + m_actor + '\'' +
                ", m_scenario='" + m_scenario + '\'' +
                ", m_image_url='" + m_image_url + '\'' +
                ", m_url='" + m_url + '\'' +
                '}';
    }
}
