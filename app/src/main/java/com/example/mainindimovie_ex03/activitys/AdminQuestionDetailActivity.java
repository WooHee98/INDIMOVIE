package com.example.mainindimovie_ex03.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mainindimovie_ex03.R;

public class AdminQuestionDetailActivity extends AppCompatActivity {
    private ImageButton delete_btn;
    private TextView detail_title, detail_regdate, detail_content, detail_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_question_detail);
        delete_btn = findViewById(R.id.admin_question_write_detail_delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminQuestionDetailActivity.super.onBackPressed();
            }
        });
        setDetail();
    }

    private void setDetail() {
        detail_title = findViewById(R.id.admin_question_write_detail_title);
        detail_regdate = findViewById(R.id.admin_question_write_detail_regdate);
        detail_content = findViewById(R.id.admin_question_write_detail_content);
        detail_answer = findViewById(R.id.admin_question_write_detail_answer);

        String i_regdate = getIntent().getStringExtra("regdate");
        String i_title = getIntent().getStringExtra("title");
        String i_content = getIntent().getStringExtra("content");
        String i_answer = getIntent().getStringExtra("answer");

        if (i_regdate != null && i_title != null && i_content !=null && i_answer!=null) {
            detail_title.setText(i_title);
            detail_regdate.setText(i_regdate);
            detail_content.setText(i_content);
            detail_answer.setText(i_answer);
        }

    }
}
