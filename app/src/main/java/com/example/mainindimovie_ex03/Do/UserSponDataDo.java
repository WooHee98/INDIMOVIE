package com.example.mainindimovie_ex03.Do;

public class UserSponDataDo {  //후원한 유저
    int user;
    int Us_id; //후원 아이디
    SinarioDataDo sinarioDataDo;   //후원한 시나리오
    int sponsored_money; //후원한 돈

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getUs_id() {
        return Us_id;
    }

    public void setUs_id(int us_id) {
        Us_id = us_id;
    }

    public SinarioDataDo getSinarioDataDo() {
        return sinarioDataDo;
    }

    public void setSinarioDataDo(SinarioDataDo sinarioDataDo) {
        this.sinarioDataDo = sinarioDataDo;
    }

    public int getSponsored_money() {
        return sponsored_money;
    }

    public void setSponsored_money(int sponsored_money) {
        this.sponsored_money = sponsored_money;
    }
}
