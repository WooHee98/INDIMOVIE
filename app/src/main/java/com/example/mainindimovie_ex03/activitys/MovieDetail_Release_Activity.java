package com.example.mainindimovie_ex03.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.MovieDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.StaticValues;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.dataPack.MovieDetailPhotoDataAdapter;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MovieDetail_Release_Activity extends AppCompatActivity {
    ImageButton delete_btn, heart_wishi;
    private RecyclerView recyclerView;
    private MovieDetailPhotoDataAdapter photoadapter;
    String selected_m_id = "";

    private TextView title, director, actor, regdate, _class, jang, runtime, wnf;
    private ImageView movie_image_poster;
    boolean a = true;
    String poster;
    private Api api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_release);
        delete_btn = findViewById(R.id.movie_detail_delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetail_Release_Activity.super.onBackPressed();
            }
        });

        Intent intent = getIntent();
        selected_m_id = intent.getStringExtra("movie_id");

        title = findViewById(R.id.movie_detail_re_title);
        director = findViewById(R.id.movie_detail_re_director);
        actor = findViewById(R.id.movie_detail_re_actor);
        regdate = findViewById(R.id.movie_detail_re_regdate);
        _class = findViewById(R.id.movie_detail_re_class);
        jang = findViewById(R.id.movie_detail_re_jang);
        runtime = findViewById(R.id.movie_detail_re_runtime);
        wnf = findViewById(R.id.movie_datail_re_content);
        movie_image_poster = findViewById(R.id.movie_detail_re_image);
        (new getMovieDetailTask()).execute(selected_m_id);
        init();
        setHeart();
//        setPhoto();
    }
    private void init() {
    }

    private void setHeart() {
        heart_wishi = findViewById(R.id.movie_detail_re_wishi);
        heart_wishi.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (a == true) {
                    if (StaticValues.u_id.length() > 0) {
                        heart_wishi.setColorFilter(Color.RED);
                        a = false;
                        (new getWishiLikeTask()).execute();

                    }else {
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
//        recyclerView = findViewById(R.id.movie_detail_re_photo_Recycler);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayout.HORIZONTAL, false));
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setAdapter(photoadapter);
//    }

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
                title.setText((String) array.get(1));
                jang.setText((String) array.get(3));
                runtime.setText((String) array.get(4));
                regdate.setText((String) array.get(5));
                _class.setText((String) array.get(6));
                director.setText((String) array.get(8));
                actor.setText((String) array.get(9));
                wnf.setText((String) array.get(10));
                //피카소에서 jpg가져오기 위해 title_id추가!!!
                poster = ((String) array.get(11));
                if (StaticValues.u_id.length()>0){
                    if(((Integer)array.get(12))>0){
                        heart_wishi.setColorFilter(Color.RED);
                    }else{
                        heart_wishi.setColorFilter(Color.parseColor("#E0E0E0"));
                    }
                }
                Picasso.get().load(api.API_URL + "/static/img/movie/" + poster).into(movie_image_poster);


            } catch (Exception e) {
                e.printStackTrace();
            }


            Log.d("ddd", s + "");


        }

        @Override
        protected String doInBackground(String... strings) {

            try {
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

}

