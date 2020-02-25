package com.example.mainindimovie_ex03.Do;

import java.util.ArrayList;

public class TheaterDataDo {

    private String t_id; // 영화관 번호
    private String t_area;//지역
    private ArrayList<String> t_name; //영화관 이름
    private String t_adult; //어른 요금
    private String t_kid;//청소년 요금
    private String t_name1;//영화관 이름
    private String t_km;//거리


    public TheaterDataDo() {
        this.t_id = "None";
        this.t_area = "None";
        this.t_name = new ArrayList<>();
    }

    public TheaterDataDo(String t_id, String t_area, ArrayList<String> t_name) {
        this.t_id = t_id;
        this.t_area = t_area;
        this.t_name = t_name;
    }

    public String getT_name1() {
        return t_name1;
    }

    public void setT_name1(String t_name1) {
        this.t_name1 = t_name1;
    }

    public String getT_km() {
        return t_km;
    }

    public void setT_km(String t_km) {
        this.t_km = t_km;
    }

    public String getT_area() {
        return t_area;
    }

    public void setT_area(String t_area) {
        this.t_area = t_area;
    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public ArrayList<String> getT_name() {
        return t_name;
    }

    public void setT_name(ArrayList<String> t_name) {
        this.t_name = t_name;
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


    @Override
    public String toString() {
        return "TheaterDataDo{" +
                "t_id='" + t_id + '\'' +
                ", t_area='" + t_area + '\'' +
                ", t_name=" + t_name +
                '}';
    }
}
