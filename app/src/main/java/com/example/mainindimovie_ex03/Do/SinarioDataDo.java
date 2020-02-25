package com.example.mainindimovie_ex03.Do;

import java.util.Date;

public class SinarioDataDo {
    String s_id;   //시나리오 번호
    String u_id;// 유저아이디
    String u_name;//유저이름
    String s_jang;  //시나리오 장르
    String s_title; //시나리오 제목
    String s_writer;//시나리오 작가
    String s_contnet;//시나리오 내용
    String s_regdate; //시나리오 작성일자

    int s_spon_money;//시나리오 총금액
    String s_spon_date;// 시나리오 날짜
    String s_account;  //계좌 번호
    String s_bank; //은행
    String s_bank_name;//예금주
    String s_amount;// 총 금액

//    ------------------------------
    String sn_id; // 새소식
    String sn_title;//새소식 제목
    String sn_content;//새소식 내용
    String sn_regdate; //새소식 작성날짜

    public SinarioDataDo() {
        this.s_id = "None";
        this.s_jang = "None";
        this.s_title = "None";
        this.s_writer = "None";
        this.s_regdate = "None";
        this.s_spon_money = 0;
        this.s_spon_date = null;

    }

    public String getSn_id() {
        return sn_id;
    }

    public void setSn_id(String sn_id) {
        this.sn_id = sn_id;
    }

    public String getSn_title() {
        return sn_title;
    }

    public void setSn_title(String sn_title) {
        this.sn_title = sn_title;
    }

    public String getSn_content() {
        return sn_content;
    }

    public void setSn_content(String sn_content) {
        this.sn_content = sn_content;
    }

    public String getSn_regdate() {
        return sn_regdate;
    }

    public void setSn_regdate(String sn_regdate) {
        this.sn_regdate = sn_regdate;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public SinarioDataDo(String s_id, String s_jang, String s_title, String s_writer, String s_contnet, String s_regdate, int s_spon_money, Date s_spon_date, String s_account, String s_bank, String s_bank_name) {
        this.s_id = s_id;
        this.s_jang = s_jang;
        this.s_title = s_title;
        this.s_writer = s_writer;
        this.s_contnet = s_contnet;
        this.s_regdate = s_regdate;
        this.s_spon_money = s_spon_money;

        this.s_account = s_account;
        this.s_bank = s_bank;
        this.s_bank_name = s_bank_name;
    }


    public String getS_amount() {
        return s_amount;
    }

    public void setS_amount(String s_amount) {
        this.s_amount = s_amount;
    }

    public String getS_writer() {
        return s_writer;
    }

    public void setS_writer(String s_writer) {
        this.s_writer = s_writer;
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

    public String getS_contnet() {
        return s_contnet;
    }

    public void setS_contnet(String s_contnet) {
        this.s_contnet = s_contnet;
    }

    public String getS_regdate() {
        return s_regdate;
    }

    public void setS_regdate(String s_regdate) {
        this.s_regdate = s_regdate;
    }

    public int getS_spon_money() {
        return s_spon_money;
    }

    public void setS_spon_money(int s_spon_money) {
        this.s_spon_money = s_spon_money;
    }


    public String getS_spon_date() {
        return s_spon_date;
    }

    public void setS_spon_date(String s_spon_date) {
        this.s_spon_date = s_spon_date;
    }

    public String getS_account() {
        return s_account;
    }

    public void setS_account(String s_account) {
        this.s_account = s_account;
    }

    public String getS_bank() {
        return s_bank;
    }

    public void setS_bank(String s_bank) {
        this.s_bank = s_bank;
    }

    public String getS_bank_name() {
        return s_bank_name;
    }

    public void setS_bank_name(String s_bank_name) {
        this.s_bank_name = s_bank_name;
    }



}
