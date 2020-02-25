package com.example.mainindimovie_ex03.activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.aApi.Api;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FrequentlyQuestionDetailActivity extends AppCompatActivity {
    private ImageButton delete_btn;
    private TextView detail_title,  detail_content;
    private Api api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freque_detail);
        delete_btn = findViewById(R.id.faq_detail_delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrequentlyQuestionDetailActivity.super.onBackPressed();
            }
        });
        detail_title = findViewById(R.id.faq_detail_title);
        detail_content = findViewById(R.id.faq_detail_text);
        init();
    }
    private void init() {
        Intent intent =getIntent();
        String selected_f_id = intent.getStringExtra("f_id");
        (new getFraqTask()).execute(selected_f_id);
    }

    private class getFraqTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                //array : movie 전체
                JSONArray array = new JSONArray(s);
                detail_title.setText((String) array.get(1));
                detail_content.setText((String) array.get(2));

            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("ddd", s + "");
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                Log.d("ddd", "bye");
                HttpGet httpPost = new HttpGet(api.API_URL+"/movie/getFreQueDetailInfo/?faq_id=" + strings[0]);
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
