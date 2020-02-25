package com.example.mainindimovie_ex03.Do;

public class SponsoredSinarioDataDo {
    String s_id;  //후원한 시나리오 번호
    String s_jang; //후원한 시나리오 장르
    String s_title; //후원한 시나리오 제목
    String s_writer; //후원한 시나리오 작성자


    String spon_price; //후원한 시나리오 가격

    //결제 카드


    public SponsoredSinarioDataDo() {
        this.s_id = "None";
        this.s_jang = "None";
        this.s_title = "None";
        this.s_writer = "None";
        this.spon_price = "None";

    }

    public SponsoredSinarioDataDo(String s_id, String s_jang, String s_title, String s_writer, String spon_price) {
        this.s_id = s_id;
        this.s_jang = s_jang;
        this.s_title = s_title;
        this.s_writer = s_writer;
        this.spon_price = spon_price;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getS_jang() {
        return s_jang;
    }

    public void setS_jang(String s_jang) {
        this.s_jang = s_jang;
    }

    public String getS_title() {
        return s_title;
    }

    public void setS_title(String s_title) {
        this.s_title = s_title;
    }

    public String getS_writer() {
        return s_writer;
    }

    public void setS_writer(String s_writer) {
        this.s_writer = s_writer;
    }

    public String getSpon_price() {
        return spon_price;
    }

    public void setSpon_price(String spon_price) {
        this.spon_price = spon_price;
    }

    @Override
    public String toString() {
        return "SponsoredSinarioDataDo{" +
                "s_id='" + s_id + '\'' +
                ", s_jang='" + s_jang + '\'' +
                ", s_title='" + s_title + '\'' +
                ", s_writer='" + s_writer + '\'' +
                ", spon_price=" + spon_price +
                '}';
    }
}
