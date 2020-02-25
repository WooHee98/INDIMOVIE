package com.example.mainindimovie_ex03.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mainindimovie_ex03.R;

public class PassSearchDetailActivity extends AppCompatActivity {
    ImageButton deletebutton;
    Button loginbtn;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_search_detail);
        deletebutton = (ImageButton)findViewById(R.id.passwordsearch_delete_btn);
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassSearchDetailActivity.super.onBackPressed();
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
        text=findViewById(R.id.password);

        Intent intent = getIntent();
        String password = intent.getStringExtra("password");
        Log.d("ddd", password);
        text.setText(password);

    }
    public void preActivity(View v) {
        onBackPressed();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}
