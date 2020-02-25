package com.example.mainindimovie_ex03.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.dataPackTab.MovieTabAdapter;


public class MovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        //MovieDataView.java에서 값 받기
        Intent intent = getIntent();
        String temp = intent.getStringExtra("initMode");

        MovieTabAdapter adapter = new MovieTabAdapter(MovieActivity.this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.container);
        viewPager.setAdapter(adapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


        //MovieDataView.java에서 받은 값에 따라 if문
        if ( temp.equals("Movie") ) {
            TabLayout.Tab tab = tabs.getTabAt(0);
            tab.select();
        } else {
            TabLayout.Tab tab = tabs.getTabAt(1);
            tab.select();
        }


        init();
    }

    private void init() {

        findViewById(R.id.movie_delete_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
