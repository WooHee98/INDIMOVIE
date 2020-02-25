package com.example.mainindimovie_ex03.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mainindimovie_ex03.R;

public class ServicecenterActivity extends AppCompatActivity {
    private ImageButton delete_btn;
    private TextView admin_question;
    private TextView frequently_question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_center);

        delete_btn = findViewById(R.id.servicecenter_delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServicecenterActivity.super.onBackPressed();
            }
        });
        init();
    }

    private void init() {
        admin_question =findViewById(R.id.servicecenter_admin_question);
        frequently_question = findViewById(R.id.servicecenter_frequently_question);
        admin_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminQuestionActivity.class);
                startActivity(intent);

            }
        });
        frequently_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FrequentlyQuestionActivity.class);
                startActivity(intent);

            }
        });
    }
}
