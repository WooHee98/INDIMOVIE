package com.example.mainindimovie_ex03.activitys;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Main2Activity;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.StaticValues;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.activitiesScenario.ScenarioFinishActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MovieReserPayActivity extends AppCompatActivity {

    ImageButton movie_pay_delete_btn,movie_reser_alert;
    TextView text1, text2, text3, text4, text5, text6, text7, text;
    Button paybutton;
    AlertDialog customDialog;
    String mtid = "";
    String seatNum = "";
    int aktotal;
    private Api api;

    Handler timer = new Handler(); //시간 지연 위한

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_reser_pay);

        movie_pay_delete_btn = findViewById(R.id.movie_pay_delete_btn);
        movie_pay_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovieReserPayActivity.super.onBackPressed();
            }
        });
        text1 = findViewById(R.id.movie_reser_title);//제목
        text2 = findViewById(R.id.movie_reser_day);// 날짜
        text3 = findViewById(R.id.movie_reser_gotime);//시간
        text4 = findViewById(R.id.movie_reser_gotheater); //영화관
        text5 = findViewById(R.id.movie_reser_goscreen);//상영관
        text6 = findViewById(R.id.movie_reser_gopercount); //일반2, 청소년2
        text7 = findViewById(R.id.movie_reser_gomoney); //가격
        paybutton = findViewById(R.id.movie_reser_gopay);
        movie_reser_alert =findViewById(R.id.movie_reser_alert); //다이얼로그 나오기
        movie_reser_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MovieReserPayActivity.this);
                final LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                final View view1 = inflater.inflate(R.layout.alert_dialog_scenario_radio1, null);
                builder.setView(view1);
                customDialog = builder.create();
                customDialog.show();

                Button button = view1.findViewById(R.id.alert_scenario__btn);
                text = view1.findViewById(R.id.text11);
                text.setText("예매 취소/환불은 상영시간 20분 전까지 가능합니다.\n 20분이 지난 상태에서는 취소/환불이 불가능합니다.");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();

                    }
                });
            }
        });

        Intent intent = getIntent();
        String theatername = intent.getStringExtra("theatername");//영화관이름
        String stid = intent.getStringExtra("stid");//상영관
        String day = intent.getStringExtra("day");//상영날짜
        String movietitle = intent.getStringExtra("movietitle");//영화이름
        String movietime = intent.getStringExtra("movietime");//영화시간
        String adultkidcount = intent.getStringExtra("adultkidcount");//성인 청소년
        String result = intent.getStringExtra("result");//성인 청소년 가격 합
        mtid = intent.getStringExtra("mtid");//성인 청소년 가격 합
        aktotal = intent.getIntExtra("aktotal", -1);//성인 청소년 가격 합
        seatNum = intent.getStringExtra("seatNum");//성인 청소년 가격 합


        text1.setText(movietitle);
        text2.setText(day);
        text3.setText(movietime);
        text4.setText(theatername);
        text5.setText(stid);
        text6.setText(adultkidcount);
        text7.setText(result);

        paybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), ScenarioFinishActivity.class);
                startActivity(intent2);
                timer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        (new getSeatPayTask()).execute();
                        Toast.makeText(getApplicationContext(), "결제가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }, 2000);
            }
        });
    }


    //결제하기 api통신
    private class getSeatPayTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        //다 끝나고 나서(결과값)
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ddd", s);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                HttpGet httpget = new HttpGet(api.API_URL+"/movie/updateSeats/?u_id=" + StaticValues.u_id + "&mt_id=" + mtid + "&num=" + aktotal + seatNum);

                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httpget);

                // StatusLine stat = response.getStatusLine();

                //404 : page not found error
                //500 : internal server error
                //200 : 정상
                //접속 결과 코드에 따라
                int res = response.getStatusLine().getStatusCode();
                Log.d("ddd", res + "");
                if (res >= 400) {

                } else {
                    //결과내용을 문자열로 바꾼다..
                    InputStreamReader is = new InputStreamReader(response.getEntity().getContent(), "UTF-8");
                    BufferedReader reader = new BufferedReader(is);

                    String line = null;
                    String data = "";

                    while ((line = reader.readLine()) != null) {
                        data += line;
                    }
                    reader.close();
                    is.close();

                    return data;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }
    }
}
