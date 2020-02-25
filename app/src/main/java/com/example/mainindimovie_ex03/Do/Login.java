package com.example.mainindimovie_ex03.Do;

public class Login {
    private String u_id;
    private String u_idtext;
    private String u_password;


    //암호 및 사용자 모델은 서버에서 다시 획득
    public Login(String u_idtext, String u_password) {
    this.u_password = u_password;
    this.u_password = u_password;
    }

    public String getU_id() {
        return u_id;
    }

    public String getU_idtext() {
        return u_idtext;
    }

    public void setU_idtext(String u_idtext) {
        this.u_idtext = u_idtext;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;

    }
}
