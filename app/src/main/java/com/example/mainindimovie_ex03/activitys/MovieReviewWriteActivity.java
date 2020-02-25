package com.example.mainindimovie_ex03.activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class MovieReviewWriteActivity extends AppCompatActivity {
    ImageButton delete_btn;
    EditText content_edit;
    TextView write_text, title;
    ImageView good, normal, bad;
    int icon_id = 0;
    String selected_m_id = "";
    String content = "";
    Button button;
    Handler timer = new Handler(); //시간 지연 위한
    private Api api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_review_write);
        delete_btn = findViewById(R.id.movie_review_write_delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieReviewWriteActivity.super.onBackPressed();
            }
        });
        init();

        Intent intent = getIntent();
        selected_m_id = intent.getStringExtra("selected_m_id"); //받아온 m_id값
        title = findViewById(R.id.title);
        title.setText(intent.getStringExtra("selected_m_title")); //받아온 title값

        button = findViewById(R.id.admin_question_write_enrol_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new insertReviewTask()).execute(); //insert
                timer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                    }
                }, 1000);

            }
        });

    }

    private void init() {
        setImage();
        setconten();
    }

    private void setImage() {
        good = findViewById(R.id.good);
        normal = findViewById(R.id.normal);
        bad = findViewById(R.id.bad);

        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                icon_id = 1;
                Toast.makeText(getApplicationContext(), "좋아요를 누르셨습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                icon_id = 2;
                Toast.makeText(getApplicationContext(), "보통입니다를 누르셨습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        bad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                icon_id = 3;
                Toast.makeText(getApplicationContext(), "별로입니다를 누르셨습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setconten() {
        final int MAX_LENGTH = 100;
        content_edit = findViewById(R.id.movie_review_write_content_edit);

        content_edit.addTextChangedListener(new TextWatcher() {
            String strCur;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strCur = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                InputFilter[] inputFilters = new InputFilter[1];
                inputFilters[0] = new InputFilter.LengthFilter(MAX_LENGTH);
                content_edit.setFilters(inputFilters);

                if (s.length() > 100) {
                    content_edit.setText(strCur);
                    content_edit.setSelection(start);
                } else {
                    write_text = findViewById(R.id.movie_review_write_text);
                    write_text.setText(String.valueOf(s.length()));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                content = content_edit.getText().toString();
            }
        });
    }

    //리뷰 insert api
    private class insertReviewTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(), "리뷰등록", Toast.LENGTH_SHORT).show();


        }

        @Override
        protected String doInBackground(String... strings) {

            try {

                Log.d("ddd", "bye");
                HttpGet httpPost = new HttpGet(api.API_URL+"/movie/InsertReviewView?mr_content=" + content + "&mr_icon=" + icon_id + "&u_id=" + StaticValues.u_id + "&m_id=" + selected_m_id);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httpPost);

                // StatusLine stat = response.getStatusLine();

                //404 : page not found error
                //500 : internal server error
                //200 : 정상
                int res = response.getStatusLine().getStatusCode();
                Log.d("ddd", res + "");
                if (res >= 400) {

                } else {
                    InputStreamReader is = new InputStreamReader(response.getEntity().getContent(), "UTF-8");
                    BufferedReader reader = new BufferedReader(is);


                    String line = null;
                    String data = "";

                    while ((line = reader.readLine()) != null) {
                        Log.d("ddd", line);
                        data += line;
                    }
                    reader.close();
                    is.close();
                    Log.d("ddd", data);

                    return data;
                }

            } catch (Exception e) {
                Log.d("ddd", e.toString());
                e.printStackTrace();
            }
            return "";
        }
    }
}
