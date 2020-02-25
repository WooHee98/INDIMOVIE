package com.example.mainindimovie_ex03.activitiesScenario;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mainindimovie_ex03.R;

public class MyScenarioStartWriteActivity extends AppCompatActivity {
    private ImageButton delete_btn;
    private TextView writer_edit, bank_text, bank_name_text, bank_num_text;
    private EditText title_edit, content_edit, money_edit, year_edit, month_edit, day_edit;
    private Spinner jang_spinner;
    private LinearLayout bank_enrol_linear;
    private Button review_btn;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_scenario_start_write);
        delete_btn = findViewById(R.id.my_scenario_start_write_delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyScenarioStartWriteActivity.super.onBackPressed();
            }
        });

        init();
        setDate();
        setBank();
    }


    private void init() {
        title_edit = findViewById(R.id.my_scenario_write_title_edit);
        writer_edit = findViewById(R.id.my_scenario_write_writer_edit);
        jang_spinner = findViewById(R.id.my_scenario_write_jang_spinner);
        content_edit = findViewById(R.id.my_scenario_write_content_edit);
        money_edit = findViewById(R.id.my_scenario_write_money_edit);

        review_btn = findViewById(R.id.my_scenario_wite_review_btn);

        //스피너 연결
        adapter = ArrayAdapter.createFromResource(this, R.array.jang, android.R.layout.simple_spinner_dropdown_item);
        jang_spinner.setAdapter(adapter);

    }

    private void setDate() {
        year_edit = findViewById(R.id.my_scenario_write_year_edit);
        month_edit = findViewById(R.id.my_scenario_write_month_edit);
        day_edit = findViewById(R.id.my_scenario_write_day_edit);

    }

    private void setBank() {
        bank_enrol_linear = findViewById(R.id.my_scenario_write_bank_enrol_linear);
        bank_text = findViewById(R.id.my_scenario_bank_text);
        bank_name_text = findViewById(R.id.my_scenario_bank_name_text);
        bank_num_text = findViewById(R.id.my_scenario_bank_num_text);


        //계좌 등록하기 레이아웃
        bank_enrol_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyScenarioBankEnrolActivity.class);
                startActivity(intent);
            }
        });

        String name = getIntent().getStringExtra("name");
        String num = getIntent().getStringExtra("num");

        if (name != null && num != null) {
            bank_name_text.setText(name);
            bank_num_text.setText(num);
        }

    }

}
