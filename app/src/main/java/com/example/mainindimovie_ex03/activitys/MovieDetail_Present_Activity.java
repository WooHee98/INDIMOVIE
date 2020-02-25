package com.example.mainindimovie_ex03.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.MovieDataDo;
import com.example.mainindimovie_ex03.Do.MovieReviewDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.StaticValues;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.dataPack.MovieDetailPhotoDataAdapter;
import com.example.mainindimovie_ex03.dataPack.MovieDetailReviewDataAdapter;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

public class MovieDetail_Present_Activity extends AppCompatActivity {

    ImageButton delete_btn, review_write, heart_wishi;
    private RecyclerView recyclerView;
    private MovieDetailPhotoDataAdapter photoadapter;
    private MovieDetailReviewDataAdapter reviewadpter;
    private TextView title, director, actor, regdate, _class, jang, runtime, wnf;
    private Button reser_btn;
    private ImageView movie_image_poster;
    String poster;
    String m_id;
    String selected_m_id = "";
    String selected_m_title = "";
    boolean a = true;
    private Api api;
    String age_id="";
    int age2 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_present);
        delete_btn = findViewById(R.id.movie_detail_delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetail_Present_Activity.super.onBackPressed();
            }
        });

        Intent intent = getIntent();
        selected_m_id = intent.getStringExtra("movie_id");
        Log.d("ds", selected_m_id);
        Log.d("dsq", StaticValues.u_id);


        title = findViewById(R.id.movie_detail_title);
        director = findViewById(R.id.movie_detail_director);
        actor = findViewById(R.id.movie_detail_actor);
        regdate = findViewById(R.id.movie_detail_regdate);
        _class = findViewById(R.id.movie_detail_class);
        jang = findViewById(R.id.movie_detail_jang);
        runtime = findViewById(R.id.movie_detail_runtime);
        wnf = findViewById(R.id.movie_detail_wnf);
        reser_btn = findViewById(R.id.movie_detail_reser_btn);
        movie_image_poster = findViewById(R.id.movie_detail_image);


        (new getMovieDetailTask()).execute();
        (new getReviewTask()).execute(selected_m_id);

        init();
        setHeart();
//        setPhoto();
        setReview();

    }


    private void init() {

        reser_btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (StaticValues.u_id.length() > 0) {
                   //나이
                    Calendar calendar = Calendar.getInstance();
                    int currentYear = calendar.get(Calendar.YEAR);
                    int age1 = Integer.parseInt(StaticValues.u_age);
                    age2 = currentYear - age1 + 1;
                    Log.d("나이", age2 + "");

                    if (age_id.equals("전체") ) {
                        Intent intent = new Intent(getApplicationContext(), MovieReserActivity.class);
                        intent.putExtra("selected_m_id", selected_m_id); //영화 아이디
                        intent.putExtra("selected_m_title", selected_m_title); //영화 제목
                        startActivity(intent);
                    }
                    if (age_id.equals("15")) {
                        int movieage = Integer.parseInt(age_id);

                        if (movieage > age2) {
                            Toast.makeText(getApplicationContext(), "관람하실 수 없습니다.", Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(getApplicationContext(), MovieReserActivity.class);
                            intent.putExtra("selected_m_id", selected_m_id); //영화 아이디
                            intent.putExtra("selected_m_title", selected_m_title); //영화 제목
                            startActivity(intent);
                        }
                    }
                    if (age_id.equals("18")) {
                        int movieage11 = Integer.parseInt(age_id);

                        if (movieage11 > age2) {
                            Toast.makeText(getApplicationContext(), "관람하실 수 없습니다.", Toast.LENGTH_LONG).show();

                        } else {
                            Intent intent = new Intent(getApplicationContext(), MovieReserActivity.class);
                            intent.putExtra("selected_m_id", selected_m_id); //영화 아이디
                            intent.putExtra("selected_m_title", selected_m_title); //영화 제목
                            startActivity(intent);
                        }
                    }

                    if (age_id.equals("12")) {
                        int movieage11 = Integer.parseInt(age_id);

                        if (movieage11 > age2) {
                            Toast.makeText(getApplicationContext(), "관람하실 수 없습니다.", Toast.LENGTH_LONG).show();

                        } else {
                            Intent intent = new Intent(getApplicationContext(), MovieReserActivity.class);
                            intent.putExtra("selected_m_id", selected_m_id); //영화 아이디
                            intent.putExtra("selected_m_title", selected_m_title); //영화 제목
                            startActivity(intent);
                        }
                    }
                    if (age_id.equals("청소년관람불가")) {

                        int a = 19;
                        if (a > age2) {
                            Toast.makeText(getApplicationContext(), "관람하실 수 없습니다.", Toast.LENGTH_LONG).show();

                        } else {
                            Intent intent = new Intent(getApplicationContext(), MovieReserActivity.class);
                            intent.putExtra("selected_m_id", selected_m_id); //영화 아이디
                            intent.putExtra("selected_m_title", selected_m_title); //영화 제목
                            startActivity(intent);
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "로그인 해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setHeart() {
        heart_wishi = findViewById(R.id.movie_detail_wishi);
        heart_wishi.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (a == true) {
                    if (StaticValues.u_id.length() > 0) {
                        heart_wishi.setColorFilter(Color.RED);
                        a = false;
//                    Toast.makeText(getApplicationContext(), "위시리스트에 추가되었습니다.", Toast.LENGTH_LONG).show();
                        (new getWishiLikeTask()).execute();

                    } else {
                        Toast.makeText(getApplicationContext(), "로그인을 해주세요", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    heart_wishi.setColorFilter(Color.parseColor("#E0E0E0"));
                    a = true;
                }
            }
        });

    }

//    private void setPhoto() {
//        ArrayList<MovieDataDo> list = new ArrayList<>();
//        for (int i = 1; i <= 5; i++) {
//            MovieDataDo item = new MovieDataDo();
//            list.add(item);
//        }
//
//        photoadapter = new MovieDetailPhotoDataAdapter(list);
//        photoadapter.setonClickmoviephotoListener(new MovieDetailPhotoDataAdapter.onClickmoviephotoListener() {
//            @Override
//            public void onClick(View view, MovieDataDo item) {
//                Toast.makeText(getApplicationContext(), "Photo", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//        recyclerView = findViewById(R.id.movie_detail_photo_Recycler);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayout.HORIZONTAL, false));
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setAdapter(photoadapter);
//    }

    private void setReview() {
        review_write = findViewById(R.id.movie_detail_review1);
        review_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StaticValues.u_id.length()>0) {
                    Intent intent = new Intent(getApplicationContext(), MovieReviewWriteActivity.class);
                    intent.putExtra("selected_m_id", selected_m_id);
                    intent.putExtra("selected_m_title", selected_m_title); //영화 제목
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "로그인을 해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //위시리스트 insert api
    private class getWishiLikeTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(), "위시리스트에추가됨", Toast.LENGTH_SHORT).show();


        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                Log.d("ddd", "bye");
                HttpGet httpPost = new HttpGet(api.API_URL+"/movie/InsertWishiView?m_id=" + selected_m_id + "&u_id=" + StaticValues.u_id);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httpPost);

                // StatusLine stat = response.getStatusLine();

                //404 : page not found error
                //500 : internal server error
                //200 : 정상
                int res = response.getStatusLine().getStatusCode();
                Log.d("ddd", res + "");
                if (res >= 400) {

                } else {
                    InputStreamReader is = new InputStreamReader(response.getEntity().getContent(), "UTF-8");
                    BufferedReader reader = new BufferedReader(is);


                    String line = null;
                    String data = "";

                    while ((line = reader.readLine()) != null) {
                        Log.d("ddd", line);
                        data += line;
                    }
                    reader.close();
                    is.close();
                    Log.d("ddd", data);

                    return data;
                }

            } catch (Exception e) {
                Log.d("ddd", e.toString());
                e.printStackTrace();
            }
            return "";
        }
    }

    //영화 디테일가져오기 위한 api
    private class getMovieDetailTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                //array : movie 전체
                JSONArray array = new JSONArray(s);
                selected_m_title = (String) array.get(1);
                title.setText((String) array.get(1));
                jang.setText((String) array.get(3));
                runtime.setText((String) array.get(4));
                regdate.setText((String) array.get(5));
                _class.setText((String) array.get(6));
                age_id = (String)array.get(7);
                director.setText((String) array.get(8));
                actor.setText((String) array.get(9));
                wnf.setText((String) array.get(10));
                //피카소에서 jpg가져오기 위해 title_id추가!!!
                poster = ((String) array.get(11));
                //Log.d("aabbccdd", array.get(12).toString());
                if (StaticValues.u_id.length()>0){
                    if(((Integer)array.get(12))>0){
                        heart_wishi.setColorFilter(Color.RED);
                    }else{
                        heart_wishi.setColorFilter(Color.parseColor("#E0E0E0"));
                    }
                }
                Picasso.get().load(api.API_URL+ "/static/img/movie/" + poster).into(movie_image_poster);


            } catch (Exception e) {
                e.printStackTrace();
            }


            Log.d("ddd", s + "");


        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                Log.d("ddd", "bye");
                //로그인했으면 user_id 포함 아니면 미포함
                HttpGet httpPost;
                HttpClient httpclient;

                if(StaticValues.u_id.length()>0){
                    httpPost = new HttpGet(api.API_URL+"/movie/getMovieDetailInfo?u_id="+StaticValues.u_id+"&m_id="+selected_m_id );
                    httpclient = new DefaultHttpClient();
                }else{
                    httpPost = new HttpGet(api.API_URL+"/movie/getMovieDetailInfo?m_id=" +selected_m_id);
                    httpclient = new DefaultHttpClient();
                }
                HttpResponse response = httpclient.execute(httpPost);


                // StatusLine stat = response.getStatusLine();

                //404 : page not found error
                //500 : internal server error
                //200 : 정상
                int res = response.getStatusLine().getStatusCode();
                Log.d("ddd", res + "");
                if (res >= 400) {

                } else {
                    InputStreamReader is = new InputStreamReader(response.getEntity().getContent(), "UTF-8");
                    BufferedReader reader = new BufferedReader(is);


                    String line = null;
                    String data = "";

                    while ((line = reader.readLine()) != null) {
                        Log.d("ddd", line);
                        data += line;
                    }
                    reader.close();
                    is.close();
                    Log.d("ddd", data);

                    return data;
                }

            } catch (Exception e) {
                Log.d("ddd", e.toString());
                e.printStackTrace();
            }
            return "";
        }
    }

    //리뷰 get api통신
    private class getReviewTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        //다 끝나고 나서(결과값)
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ddd", s);

            ArrayList<MovieReviewDataDo> list1 = new ArrayList<>();
            try {
                //array : movie 전체
                JSONArray array = new JSONArray(s);
                for (int i = 0; i < array.length(); i++) {
                    JSONArray review = (JSONArray) array.get(i);
                    MovieReviewDataDo item = new MovieReviewDataDo();
                    item.setMr_id((Integer) review.get(0) + "");
                    item.setMr_content((String) review.get(1));
                    item.setMr_icon((Integer) review.get(2) + "");
                    item.setMr_nick((String) review.get(5));
                    item.setMr_regdate((String)review.get(6));
                    list1.add(item);


                }
                reviewadpter = new MovieDetailReviewDataAdapter(list1);
                reviewadpter.setonClickmoviereviewListener(new MovieDetailReviewDataAdapter.onClickmoviereviewListener() {
                    @Override
                    public void onClick(View view, MovieReviewDataDo item) {
                        Toast.makeText(getApplicationContext(), "Review" + item.getMr_id(), Toast.LENGTH_SHORT).show();
                    }

                });


                recyclerView = findViewById(R.id.movie_detail_review_Recycler1);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(reviewadpter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        protected String doInBackground(String... strings) {
            try {
                HttpGet httpget = new HttpGet(api.API_URL+"/movie/getReviewView?m_id="+ strings[0]);

                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httpget);



                // StatusLine stat = response.getStatusLine();

                //404 : page not found error
                //500 : internal server error
                //200 : 정상
                //접속 결과 코드에 따라
                int res = response.getStatusLine().getStatusCode();
                Log.d("ddd", res + "");
                if (res >= 400) {

                } else {
                    //결과내용을 문자열로 바꾼다..
                    InputStreamReader is = new InputStreamReader(response.getEntity().getContent(), "UTF-8");
                    BufferedReader reader = new BufferedReader(is);

                    String line = null;
                    String data = "";

                    while ((line = reader.readLine()) != null) {
                        data += line;
                    }
                    reader.close();
                    is.close();

                    return data;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "";
        }
    }
}
