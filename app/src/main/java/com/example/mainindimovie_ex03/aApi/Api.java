package com.example.mainindimovie_ex03.aApi;

import com.example.mainindimovie_ex03.Do.AdminQuestionDataDo;
import com.example.mainindimovie_ex03.Do.Join;
import com.example.mainindimovie_ex03.Do.Login;
import com.example.mainindimovie_ex03.Do.UserDataDo;
import com.example.mainindimovie_ex03.Do.Funding;
import com.example.mainindimovie_ex03.Do.movieseen;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {
     public static final String API_URL = "http://10.0.2.2:8000";

    //회원가입
    @POST("/movie/join/")
    Call<Join> postJoin(@Body Join join);

    //펀딩
    @POST("/movie/funding/")
    Call<Funding> postFunding(@Body Funding funding);

    //본영화
    @POST("/movie/movieseen/")
    Call<movieseen> postmymovieseen(@Body movieseen movieseen);



    //펀딩 결제 정보 수정
    @PUT("/movie/fundingUpdate/{f_id}/")
    Call<Funding> UpdateFunding( @Path("f_id") int id ,@Body Funding funding);

    //로그인
    @POST("/movie/login/")
    Call<UserDataDo> login(@Body Login login);

    //유저 개인 정보 받아오기
    @GET("/users/{u_id}/")
    Call<UserDataDo> getUserDetail( @Path("u_id") String id);


}
