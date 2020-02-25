package com.example.mainindimovie_ex03.activitiesRecommendation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mainindimovie_ex03.GPS.GpsTracker;
import com.example.mainindimovie_ex03.MainActivity;
import com.example.mainindimovie_ex03.R;

public class RecommendationMovieActivity extends AppCompatActivity {
    ImageButton recommendation_delete_btn;
    LinearLayout jang_recommend, like_recommend, seen_recommend;
    ImageButton delete;

    String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    GpsTracker tracker;

    private boolean isLocationOn(){
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation_movie);
        delete= findViewById(R.id.recommendation_delete_btn);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecommendationMovieActivity.super.onBackPressed();
            }
        });

        recommendation_delete_btn = findViewById(R.id.recommendation_delete_btn);
        jang_recommend = findViewById(R.id.jang_recommend);
        like_recommend = findViewById(R.id.like_recommend);
        seen_recommend = findViewById(R.id.seen_recommend2);


        //1) 위치 정보가 꺼져있는 경우

        if(!isLocationOn()){
            Intent locationIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(locationIntent, 1111);
        }

        //2) 위치 권한 있는지 검사
        if(ContextCompat.checkSelfPermission(RecommendationMovieActivity.this, PERMISSIONS[0]) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(RecommendationMovieActivity.this, PERMISSIONS[1]) != PackageManager.PERMISSION_GRANTED){
            //거절한적이 있는 경우

            if(ActivityCompat.shouldShowRequestPermissionRationale(RecommendationMovieActivity.this, PERMISSIONS[0])){
                Toast.makeText(getBaseContext(), "이 앱은 영화 추천 서비스를 위해 위치 정보가 필요합니다.", Toast.LENGTH_LONG).show();
            }

            ActivityCompat.requestPermissions(RecommendationMovieActivity.this, PERMISSIONS, 2222);

            //한번도 거절한 적이 없는 경우

        }

        //위치 정보도 켜져있고, 권한도 받은 상태
        jang_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tracker = new GpsTracker(RecommendationMovieActivity.this);
                Log.d("latitude", tracker.getLatitude()+"");
                Log.d("longitude", tracker.getLongitude()+"");
                Intent intent = new Intent(getApplicationContext(), Recommendation_jang.class);
                intent.putExtra("latitude", tracker.getLatitude()+"");
                intent.putExtra("longitude", tracker.getLongitude()+"");
                startActivity(intent);

            }
        });

        like_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tracker = new GpsTracker(RecommendationMovieActivity.this);
                Intent intent1 = new Intent(getApplicationContext(), Recommendation_like.class);
                intent1.putExtra("latitude", tracker.getLatitude()+"");
                intent1.putExtra("longitude", tracker.getLongitude()+"");
                startActivity(intent1);

            }
        });
        seen_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tracker = new GpsTracker(RecommendationMovieActivity.this);
                Intent intent1 = new Intent(getApplicationContext(), Recommendation_seen.class);
                intent1.putExtra("latitude", tracker.getLatitude()+"");
                intent1.putExtra("longitude", tracker.getLongitude()+"");
                startActivity(intent1);
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 2222 && grantResults.length == PERMISSIONS.length){
            int i = 0;
            for(;i<grantResults.length;i++){
                if(grantResults[i] != PackageManager.PERMISSION_GRANTED)
                    break;
            }
            if(i < grantResults.length){
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1111){
            if(isLocationOn()){
                Toast.makeText(getBaseContext(), "Location ON!", Toast.LENGTH_LONG).show();
            }
            else{
                Intent locationIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(locationIntent, 1111);
            }
        }
    }
}
