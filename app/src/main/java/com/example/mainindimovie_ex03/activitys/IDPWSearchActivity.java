package com.example.mainindimovie_ex03.activitys;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.dataPackTab.IDPWsearchTabAdapter;

public class IDPWSearchActivity extends AppCompatActivity {
    ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idpwsearch);

        IDPWsearchTabAdapter adapter = new IDPWsearchTabAdapter(IDPWSearchActivity.this, getSupportFragmentManager());

        button=findViewById(R.id.idpw_search_delete_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IDPWSearchActivity.super.onBackPressed();
            }
        });

        ViewPager viewPager = findViewById(R.id.idpasswordsearch_container);
        viewPager.setAdapter(adapter);
        TabLayout tabs = findViewById(R.id.idpasswordsearch_tab);
        tabs.setupWithViewPager(viewPager);

    }
}
