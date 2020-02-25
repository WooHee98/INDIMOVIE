package com.example.mainindimovie_ex03.activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mainindimovie_ex03.Do.SinarioDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.aApi.Api;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ScenarioNewsDActivity extends AppCompatActivity {
    TextView title, regdate, content;
    ImageButton button;
    private Api api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario_news_d);
        button  = findViewById(R.id.news_detail_delete_btn);
        title  = findViewById(R.id.news_detail_title);
        regdate = findViewById(R.id.news_detail_regdate);
        content =findViewById(R.id.news_detail_text);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScenarioNewsDActivity.super.onBackPressed();
            }
        });
        Intent intent  = getIntent();
        intent.getStringExtra("sn_id");
        (new getScenrionewsDetailTask()).execute( intent.getStringExtra("sn_id"));
    }

    private class getScenrionewsDetailTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ArrayList<SinarioDataDo> temp = new ArrayList<>();

            try{
                //array : movie 전체
                JSONArray array = new JSONArray(s);
                title.setText((String) array.get(1));
                regdate.setText((String) array.get(3));
                content.setText((String) array.get(2));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                HttpGet httpPost = new HttpGet(
                        api.API_URL+"/movie/getScenarioNewsDetailInfo?sn_id="+ strings[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httpPost);

                int res = response.getStatusLine().getStatusCode();
                if(res >= 400){
                }
                else{
                    InputStreamReader is = new InputStreamReader(response.getEntity().getContent(), "UTF-8");
                    BufferedReader reader = new BufferedReader(is);

                    String line = null;
                    String data = "";

                    while((line = reader.readLine())!=null){

                        data += line;
                    }
                    reader.close();
                    is.close();

                    return data;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return "";
        }
    }
}
