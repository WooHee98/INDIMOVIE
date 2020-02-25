package com.example.mainindimovie_ex03.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainindimovie_ex03.R;

public class SeatPersonSelected extends AppCompatActivity {
    private Button seat_comlete_btn;
    private TextView seat_adult_per, seat_youth_per;
    private ImageButton adult_remove_btn, adult_add_btn;
    private ImageButton youth_remove_btn, youth_add_btn;
    private int count = 0;
    private int count1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_person_selected);
        seat_adult_per = findViewById(R.id.adult_per);
        seat_youth_per = findViewById(R.id.youth_per);
        adult_remove_btn = findViewById(R.id.adult_remove);
        adult_add_btn = findViewById(R.id.adult_add);
        youth_remove_btn = findViewById(R.id.youth_remove);
        youth_add_btn = findViewById(R.id.youth_add);



        seat_adult_per.setText(Integer.toString(getIntent().getIntExtra("bbbb",0)));
        seat_youth_per.setText(Integer.toString(getIntent().getIntExtra("bbbb1",0)));
        add_remove();

    }


    private void add_remove() {

        final int adult_per_int = Integer.parseInt(seat_adult_per.getText().toString());
        final int youth_per_int = Integer.parseInt(seat_youth_per.getText().toString());

        String aaaa = seat_adult_per.getText().toString();
        int bbbb = Integer.parseInt(aaaa);


        //성인 인원 추가
        adult_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                seat_adult_per.setText(Integer.toString(adult_per_int + count));
            }
        });

        //성인 인원 감소
        adult_remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aaaa = seat_adult_per.getText().toString();
                int bbbb = Integer.parseInt(aaaa);
                if (bbbb == 0) {
                    Toast.makeText(getApplicationContext(), "클릭불가", Toast.LENGTH_SHORT).show();

                } else {
                    count--;
                    seat_adult_per.setText(Integer.toString(adult_per_int + count));

                }
            }
        });


        //청소년 인원 추가
        youth_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count1++;
                seat_youth_per.setText(Integer.toString(youth_per_int + count1));
            }
        });

        //청소년 인원 감소
        youth_remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aaaa = seat_youth_per.getText().toString();
                int bbbb = Integer.parseInt(aaaa);
                if (bbbb == 0) {
                    Toast.makeText(getApplicationContext(), "클릭불가", Toast.LENGTH_SHORT).show();

                } else {
                    count1--;
                    seat_youth_per.setText(Integer.toString(youth_per_int + count1));

                }
            }
        });


        seat_comlete_btn = findViewById(R.id.seat_person_selected_complete);
        seat_comlete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aaaa = seat_adult_per.getText().toString();
                int bbbb = Integer.parseInt(aaaa);

                String aaaa1 = seat_youth_per.getText().toString();
                int bbbb1 = Integer.parseInt(aaaa1);

                if (bbbb == 0 && bbbb1 == 0) {
                    Toast.makeText(getApplicationContext(), "선택해주세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent  = new Intent(getApplicationContext(), SeatPersonSelected.class);
                    intent.putExtra("bbbb", bbbb);
                    intent.putExtra("bbbb1", bbbb1);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

    }
}
