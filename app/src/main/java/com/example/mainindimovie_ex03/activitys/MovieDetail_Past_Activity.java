package com.example.mainindimovie_ex03.activitys;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.MovieDataDo;
import com.example.mainindimovie_ex03.Do.MovieReviewDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.dataPack.MovieDetailPhotoDataAdapter;
import com.example.mainindimovie_ex03.dataPack.MovieDetailReviewDataAdapter;

import java.util.ArrayList;

public class MovieDetail_Past_Activity extends AppCompatActivity {
    ImageButton delete_btn ,  heart_wishi;
    private RecyclerView recyclerView;
    private MovieDetailPhotoDataAdapter photoadapter;
    private MovieDetailReviewDataAdapter reviewadpter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_past);
        delete_btn = findViewById(R.id.movie_detail_delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetail_Past_Activity.super.onBackPressed();
            }
        });

        init();
        setHeart();
        setPhoto();
        setReview();
    }
    private void init() {
    }

    private void setHeart() {
        heart_wishi = findViewById(R.id.movie_detail_pa_wishi);
        heart_wishi.setOnClickListener(new View.OnClickListener() {
            boolean a = true;
            public void onClick(View v) {
                if(a == true) {
                    heart_wishi.setColorFilter(Color.RED);
                    a = false;
                }else{
                    heart_wishi.setColorFilter(Color.parseColor("#E0E0E0"));
                    a=true;
                }
            }
        });

    }

    private void setPhoto() {
        ArrayList<MovieDataDo> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            MovieDataDo item = new MovieDataDo();
            list.add(item);
        }

        photoadapter = new MovieDetailPhotoDataAdapter(list);
        photoadapter.setonClickmoviephotoListener(new MovieDetailPhotoDataAdapter.onClickmoviephotoListener() {
            @Override
            public void onClick(View view, MovieDataDo item) {
                Toast.makeText(getApplicationContext(), "Photo", Toast.LENGTH_SHORT).show();
            }
        });


        recyclerView = findViewById(R.id.movie_detail_pa_photo_Recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayout.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(photoadapter);
    }

    private void setReview() {
        ArrayList<MovieReviewDataDo> list1 = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            MovieReviewDataDo item = new MovieReviewDataDo();
            item.setMr_nick("닉네임" + i);
            item.setMr_content("내용" + i);
            list1.add(item);
        }

        reviewadpter = new MovieDetailReviewDataAdapter(list1);
        reviewadpter.setonClickmoviereviewListener(new MovieDetailReviewDataAdapter.onClickmoviereviewListener() {
            @Override
            public void onClick(View view, MovieReviewDataDo item) {
                Toast.makeText(getApplicationContext(), "Review"+item.getMr_id(), Toast.LENGTH_SHORT).show();
            }

        });


        recyclerView = findViewById(R.id.movie_detail_pa_review_Recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(reviewadpter);
    }


}
