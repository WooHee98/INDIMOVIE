package com.example.mainindimovie_ex03.activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.aApi.Api;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MyMovieReviewUpActivity extends AppCompatActivity {

    ImageButton myreview_pre_btn;
    Button review_updatebtn;
    EditText content_edit;
    TextView write_text, title;
    ImageView good, normal, bad;
    int icon_id = 0;
    String content = "";
    String mr_id="";
    private Api api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_movie_review_up);
        myreview_pre_btn = findViewById(R.id.myreview_pre_btn);
        myreview_pre_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyMovieReviewUpActivity.super.onBackPressed();
            }
        });

        Intent intent =getIntent();
        mr_id =intent.getStringExtra("mr_id");

        setconten();
        setImage();
        review_updatebtn = findViewById(R.id.review_updatebtn);
        review_updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new UpdateReviewTask()).execute();
                finish();
            }
        });
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
        content_edit = findViewById(R.id.review_update_content_edit);

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

    //리뷰 update api
    private class UpdateReviewTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                HttpGet httpPost = new HttpGet(api.API_URL+"/movie/UpdateMyReviewView?mr_content=" + content + "&mr_icon=" + icon_id + "&mr_id=" + mr_id);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httpPost);

                int res = response.getStatusLine().getStatusCode();
                if (res >= 400) {

                } else {
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
