package com.example.mainindimovie_ex03.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mainindimovie_ex03.R;

public class IdSearchDetailActivity extends AppCompatActivity {
    ImageButton deletebutton;
    Button loginbtn;
    String name ,id;

    TextView nametext, idtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_search_detail);

        deletebutton = (ImageButton)findViewById(R.id.idsearch_delete_btn);
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IdSearchDetailActivity.super.onBackPressed();
            }
        });

        loginbtn = (Button)findViewById(R.id.logingo_btn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                preActivity(view);
                startActivity(intent);
            }
        });

        nametext = findViewById(R.id.username);
        idtext = findViewById(R.id.userid);

        Intent intent = getIntent();
        name = intent.getStringExtra("uname");
        id= intent.getStringExtra("uid");
        nametext.setText(name);
        idtext.setText(id);

    }
    public void preActivity(View v) {
        onBackPressed();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

}
