package com.example.mainindimovie_ex03.activitiesRecommendation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.mainindimovie_ex03.R;

public class Recommendation_jang extends AppCompatActivity {
    ImageButton delete;
    Button romance, drama, family, ani, Thriller, Mystery,  Docu, Comedy;
    String jang="";
    String latitude="";
    String longitude="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation_jang);
        delete=findViewById(R.id.recommendation_delete_btn);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Recommendation_jang.super.onBackPressed();
            }
        });
        romance= findViewById(R.id.romance);
        drama= findViewById(R.id.drama);
        family= findViewById(R.id.family);
        ani= findViewById(R.id.ani);
        Thriller= findViewById(R.id.Thriller);
        Mystery= findViewById(R.id.Mystery);
        Docu= findViewById(R.id.Docu);
        Comedy= findViewById(R.id.Comedy);

        romance.setOnClickListener(bListener);
        drama.setOnClickListener(bListener);
        family.setOnClickListener(bListener);
        ani.setOnClickListener(bListener);
        Thriller.setOnClickListener(bListener);
        Mystery.setOnClickListener(bListener);
        Docu.setOnClickListener(bListener);
        Comedy.setOnClickListener(bListener);

        Intent intent1 = getIntent();
        latitude = intent1.getStringExtra("latitude");
        longitude= intent1.getStringExtra("longitude");

    }
    Button.OnClickListener bListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.romance:
                    jang = "로맨스";
                    Intent intent= new Intent(getApplicationContext(), RecommendationListA.class);
                    intent.putExtra( "latitude",latitude); //위치
                    intent.putExtra("longitude",longitude); // 위치
                    intent.putExtra("jang",jang); //장르
                    startActivity(intent);
                    break;
                case R.id.drama:
                    jang = "드라마";
                    Intent intent1= new Intent(getApplicationContext(), RecommendationListA.class);
                    intent1.putExtra( "latitude",latitude); //위치
                    intent1.putExtra("longitude",longitude); // 위치
                    intent1.putExtra("jang",jang); //장르
                    startActivity(intent1);
                    break;
                case R.id.family:
                    jang = "가족";
                    Intent intent2= new Intent(getApplicationContext(), RecommendationListA.class);
                    intent2.putExtra( "latitude",latitude); //위치
                    intent2.putExtra("longitude",longitude); // 위치
                    intent2.putExtra("jang",jang); //장르
                    startActivity(intent2);
                    break;
                case R.id.ani:
                    jang = "애니";
                    Intent intent3= new Intent(getApplicationContext(), RecommendationListA.class);
                    intent3.putExtra( "latitude",latitude); //위치
                    intent3.putExtra("longitude",longitude); // 위치
                    intent3.putExtra("jang",jang); //장르
                    startActivity(intent3);
                    break;
                case R.id.Thriller:
                    jang = "스릴러";
                    Intent intent4= new Intent(getApplicationContext(), RecommendationListA.class);
                    intent4.putExtra( "latitude",latitude); //위치
                    intent4.putExtra("longitude",longitude); // 위치
                    intent4.putExtra("jang",jang); //장르
                    startActivity(intent4);
                    break;
                case R.id.Mystery:
                    jang = "미스";
                    Intent intent5= new Intent(getApplicationContext(), RecommendationListA.class);
                    intent5.putExtra( "latitude",latitude); //위치
                    intent5.putExtra("longitude",longitude); // 위치
                    intent5.putExtra("jang",jang); //장르
                    startActivity(intent5);
                    break;
                case R.id.Docu:
                    jang = "다큐";
                    Intent intent6= new Intent(getApplicationContext(), RecommendationListA.class);
                    intent6.putExtra( "latitude",latitude); //위치
                    intent6.putExtra("longitude",longitude); // 위치
                    intent6.putExtra("jang",jang); //장르
                    startActivity(intent6);
                    break;
                case R.id.Comedy:
                    jang = "코미디";
                    Intent intent7= new Intent(getApplicationContext(), RecommendationListA.class);
                    intent7.putExtra( "latitude",latitude); //위치
                    intent7.putExtra("longitude",longitude); // 위치
                    intent7.putExtra("jang",jang); //장르
                    startActivity(intent7);
                    break;
            }
        }
    };
}
