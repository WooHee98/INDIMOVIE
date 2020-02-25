package com.example.mainindimovie_ex03.activitiesScenario;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.mainindimovie_ex03.R;

public class MyScenarioBankEnrolActivity extends AppCompatActivity {

    static final String KEY_DATA = "KEY_DATA";

    public final static int SIG_BANK_PICKER = 303;
    ImageButton delete_btn;
    Spinner bank_enrol_spinner;
    EditText bank_name_edit, bank_num_edit;
    Button bank_enrol_btn;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_scenario_bank_enrol);
        delete_btn = findViewById(R.id.my_scenario_bank_enrol_delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyScenarioBankEnrolActivity.super.onBackPressed();
            }
        });

        init();

    }


    private void init() {


        //스피너 연결
        adapter = ArrayAdapter.createFromResource(this, R.array.bank, android.R.layout.simple_spinner_dropdown_item);
        bank_enrol_spinner.setAdapter(adapter);


        bank_enrol_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                bank_enrol_spinner = findViewById(R.id.my_scenario_bank_enrol_spinner);
                bank_name_edit = findViewById(R.id.my_scenario_bank_name_edit);
                bank_num_edit = findViewById(R.id.my_scenario_bank_num_edit);
                bank_enrol_btn = findViewById(R.id.my_scenario_bank_enrol_btn);


                final String bankname = bank_name_edit.getText().toString();
                final String banknum = bank_num_edit.getText().toString();

                Intent intent = new Intent(MyScenarioBankEnrolActivity.this, MyScenarioStartWriteActivity.class);
                intent.putExtra("name", bankname);
                intent.putExtra("num", banknum);
                startActivityForResult(intent, SIG_BANK_PICKER);
            }
        });


    }
}
