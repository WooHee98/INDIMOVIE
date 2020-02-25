package com.example.mainindimovie_ex03.Do;

public class SinarioUserDataDo {   //시나리오등록 user
    int user;
    int su_id;       // 시나리오등록 userid
    SinarioDataDo sinarioDataDo;   //작성한 시나리오
    Boolean certification;    //확인하기 위한


    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getSu_id() {
        return su_id;
    }

    public void setSu_id(int su_id) {
        this.su_id = su_id;
    }



    public SinarioDataDo getSinarioDataDo() {
        return sinarioDataDo;
    }

    public void setSinarioDataDo(SinarioDataDo sinarioDataDo) {
        this.sinarioDataDo = sinarioDataDo;
    }

    public Boolean getCertification() {
        return certification;
    }

    public void setCertification(Boolean certification) {
        this.certification = certification;
    }
}
