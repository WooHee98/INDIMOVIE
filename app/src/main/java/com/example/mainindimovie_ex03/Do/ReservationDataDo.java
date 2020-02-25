package com.example.mainindimovie_ex03.Do;

import java.util.ArrayList;
import java.util.Date;

public class ReservationDataDo {
    private String st_id; //상영관 아이디
    private String r_id; //1예매번호  (Reservation table)
    private String m_title; //2영화 제목  (Movie table)
    private String t_name; //3영화관 이름  (Theater table)
    private ArrayList<String> mt_id;// 상영 아이디
    private String mt_id1;//상영 아이디 (20분 지나면 예매 취소 못하도록 추가)
    private String mt_date; // 4상영날짜   (Movie_time table)
    private ArrayList<String> st_time; ; //5 상영시간  (Movie_time table)
    private String st_name; //상영관이름
    private String t_adult; //영화관 성인
    private String t_kid; // 영화관 청소년
    private String r_seat; //6좌석    (Reservation table)
    private String m_image_url; // 영화 이미지
    private String st_time1; //상영시간
    private String r_seat_count;
    private String s_id;//좌석 아이디
    private String runtime; //런타임
    private String jang; //장르


    public ReservationDataDo() {
        this.r_id = "None";
        this.m_title = "None";
        this.t_name = "None";
        this.mt_date = "None";
        this.st_name ="None";
        this.r_seat = "None";
           this.m_image_url = "None";

    }


    public String getMt_id1() {
        return mt_id1;
    }

    public void setMt_id1(String mt_id1) {
        this.mt_id1 = mt_id1;
    }

    public String getJang() {
        return jang;
    }

    public void setJang(String jang) {
        this.jang = jang;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getR_seat_count() {
        return r_seat_count;
    }

    public void setR_seat_count(String r_seat_count) {
        this.r_seat_count = r_seat_count;
    }

    public String getSt_time1() {
        return st_time1;
    }

    public void setSt_time1(String st_time1) {
        this.st_time1 = st_time1;
    }

    public String getSt_id() {
        return st_id;
    }

    public void setSt_id(String st_id) {
        this.st_id = st_id;
    }

    public String getT_adult() {
        return t_adult;
    }

    public void setT_adult(String t_adult) {
        this.t_adult = t_adult;
    }

    public String getT_kid() {
        return t_kid;
    }

    public void setT_kid(String t_kid) {
        this.t_kid = t_kid;
    }

    public ArrayList<String> getMt_id() {
        return mt_id;
    }

    public void setMt_id(ArrayList<String> mt_id) {
        this.mt_id = mt_id;
    }

    public ArrayList<String> getSt_time() {
        return st_time;
    }



    public String getst_name() {
        return st_name;
    }

    public void setSt_name(String st_name) {
        this.st_name = st_name;
    }

    public String getR_id() {
        return r_id;
    }

    public void setR_id(String r_id) {
        this.r_id = r_id;
    }


    //2영화 제목  (Movie table)
    public String getM_title() {
        return m_title;
    }

    public void setM_title(String m_title) {
        this.m_title = m_title;
    }


    //3영화관 이름  (Theater table)
    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }


    // 4상영날짜   (Movie_time table)
    public String getMt_date() {
        return mt_date;
    }

    public void setMt_date(String mt_date) {
        this.mt_date = mt_date;
    }


    //5 상영회차(시간)  (Movie_time table)


    public ArrayList<String> getst_time() {
        return st_time;
    }

    public void setSt_time(ArrayList<String> st_time) {
        this.st_time = st_time;
    }

    public String getR_seat() {
        return r_seat;
    }

    public void setR_seat(String r_seat) {
        this.r_seat = r_seat;
    }



    public String getM_image_url() {
        return m_image_url;
    }

    public void setM_image_url(String m_image_url) {
        this.m_image_url = m_image_url;
    }



}
