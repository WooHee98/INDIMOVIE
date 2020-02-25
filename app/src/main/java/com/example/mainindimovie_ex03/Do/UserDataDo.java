package com.example.mainindimovie_ex03.Do;

public class UserDataDo {
    private String password;
    private String  u_id; // 회원 아이디(번호)
    private String u_idtext;//회원 아이디
    private String u_password;//회원 비밀번호
    private String u_name;//회원 이름
    private String u_birth;//회원 생년월일
    private String u_birth1;//회원 생년월일
    private String u_birth2;//회원 생년월일
    private String u_phone;//회원 핸드폰

    int userenroll; //시나리오 등록여부
    int userspon; //후원여부


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserenroll() {
        return userenroll;
    }

    public void setUserenroll(int userenroll) {
        this.userenroll = userenroll;
    }

    public int getUserspon() {
        return userspon;
    }

    public void setUserspon(int userspon) {
        this.userspon = userspon;
    }



    public String getU_birth1() {
        return u_birth1;
    }

    public void setU_birth1(String u_birth1) {
        this.u_birth1 = u_birth1;
    }

    public String getU_birth2() {
        return u_birth2;
    }

    public void setU_birth2(String u_birth2) {
        this.u_birth2 = u_birth2;
    }


    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getU_idtext() {
        return u_idtext;
    }

    public void setU_idtext(String u_idtext) {
        this.u_idtext = u_idtext;
    }

    public String getU_password() {
        return u_password;
    }

    public void setU_password(String u_password) {
        this.u_password = u_password;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_birth() {
        return u_birth;
    }

    public void setU_birth(String u_birth) {
        this.u_birth = u_birth;
    }


    public String getU_phone() {
        return u_phone;
    }

    public void setU_phone(String u_phone) {
        this.u_phone = u_phone;
    }

}
