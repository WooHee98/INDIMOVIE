package com.example.mainindimovie_ex03.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.DatePickerDataDo;
import com.example.mainindimovie_ex03.Do.ReservationDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.dataPack.MovieReserDatePickerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieReserDatePickerActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MovieReserDatePickerAdapter adapter;
    private ArrayList<Button> dateButton;

    private TextView date_picker_year;
    private TextView date_picker_month;
    int day=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_reser_date_picker);

        //defaultValue =>  아무값도 받지 못했다면(-1)
        final int i_year = getIntent().getIntExtra("year", -1);
        final int i_month = getIntent().getIntExtra("month", -1);
        final int i_day = getIntent().getIntExtra("day",-1);


        //year값이 있다면
        if (i_year != -1 && i_month != -1) {
            date_picker_year = findViewById(R.id.select_date_picker_year);
            date_picker_month = findViewById(R.id.select_date_picker_month);
            //+""하면 auto로 텍스트로 바꿔준다.
            date_picker_year.setText(i_year + "");
            date_picker_month.setText(i_month + "");
        }
        ArrayList<DatePickerDataDo> temp = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            DatePickerDataDo item = new DatePickerDataDo();
            item.setDate(i_day+i);
            day = i_day+i;
            if(day==31){
                item.setDate(01);
            }
            if(day==32){
                item.setDate(02);
            }
            temp.add(item);
        }
        adapter = new MovieReserDatePickerAdapter(temp);
        adapter.setonClickTimeViewListener(new MovieReserDatePickerAdapter.onClickTimeViewListener() {
            @Override
            public void onclick(View view, DatePickerDataDo item) {
                String dd="";
                if(item.getDate()==01 || item.getDate()==02){
                     dd = i_year+"-"+12 +"-"+item.getDate();
                }else{
                     dd = i_year+"-"+11 +"-"+item.getDate();
                }
                Intent intent = new Intent(getApplicationContext(), MovieReserActivity.class);
                intent.putExtra("select_date", item.getDate() + "");
                intent.putExtra("select_dbdata", dd);
                setResult(RESULT_OK, intent);
                finish();
            }


        });

        recyclerView = (RecyclerView) findViewById(R.id.date_picker_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        init();
    }

    private void init() {
    }
}


