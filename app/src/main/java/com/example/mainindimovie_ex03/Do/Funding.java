package com.example.mainindimovie_ex03.Do;

import java.util.Date;

public class Funding {
    private int f_id;
    private int f_amount;
    private Date f_created_at;
    private String f_cardnum;
    private String f_vaildity;
    private String f_cardpass;
    private String s_id;
    private String u_id;  //후원하는 사람
    private String s_title;
    private String s_jang;
    private String u_idtext; //시나리오 작성자
    private String address;

    public Funding(){
        this.f_id = 0;
        this.f_amount = 0;
        this.f_cardnum = "None";
        this.f_vaildity = "None";
        this.f_cardpass = "None";

        this.s_title = "None";
        this.s_jang = "None";
        this.u_idtext = "None";

    }


    public Funding(String f_cardnum, String f_vaildity, String f_cardpass){
        this.f_cardnum = f_cardnum;
        this.f_vaildity = f_vaildity;
        this.f_cardpass =f_cardpass;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getF_id() {
        return f_id;
    }

    public void setF_id(int f_id) {
        this.f_id = f_id;
    }

    public int getF_amount() {
        return f_amount;
    }

    public void setF_amount(int f_amount) {
        this.f_amount = f_amount;
    }

    public Date getF_created_at() {
        return f_created_at;
    }

    public void setF_created_at(Date f_created_at) {
        this.f_created_at = f_created_at;
    }

    public String getF_cardnum() {
        return f_cardnum;
    }

    public void setF_cardnum(String f_cardnum) {
        this.f_cardnum = f_cardnum;
    }

    public String getF_vaildity() {
        return f_vaildity;
    }

    public void setF_vaildity(String f_vaildity) {
        this.f_vaildity = f_vaildity;
    }

    public String getF_cardpass() {
        return f_cardpass;
    }

    public void setF_cardpass(String f_cardpass) {
        this.f_cardpass = f_cardpass;
    }


    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getS_title() {
        return s_title;
    }

    public void setS_title(String s_title) {
        this.s_title = s_title;
    }

    public String getS_jang() {
        return s_jang;
    }

    public void setS_jang(String s_jang) {
        this.s_jang = s_jang;
    }

    public String getU_idtext() {
        return u_idtext;
    }

    public void setU_idtext(String u_idtext) {
        this.u_idtext = u_idtext;
    }
}