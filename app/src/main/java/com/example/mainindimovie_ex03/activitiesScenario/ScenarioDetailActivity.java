package com.example.mainindimovie_ex03.activitiesScenario;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.dataPackTab.ScenarioDetailTabAdapter;

public class ScenarioDetailActivity extends AppCompatActivity {
    ImageButton delete_btn;

    String selected_u_id;
    String selected_s_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario_detail);
        delete_btn = findViewById(R.id.sinario_detail_delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScenarioDetailActivity.super.onBackPressed();
            }
        });
        Intent intent = getIntent();
        selected_s_id = intent.getStringExtra("s_id");
        selected_u_id = intent.getStringExtra("u_id");

        ScenarioDetailTabAdapter adapter = new ScenarioDetailTabAdapter(ScenarioDetailActivity.this, getSupportFragmentManager());
        adapter.s_id = selected_s_id;
        adapter.u_id = selected_u_id;
        ViewPager viewPager = findViewById(R.id.sinario_detail_container);
        viewPager.setAdapter(adapter);
        TabLayout tabs = findViewById(R.id.sinario_detail_tab);
        tabs.setupWithViewPager(viewPager);
    }
}