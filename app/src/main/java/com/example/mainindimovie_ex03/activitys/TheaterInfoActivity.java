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

public class TheaterInfoActivity extends AppCompatActivity {

    ImageButton imageButton;
    private TextView theater_name, theater_content,theater_phone,theater_web,theater_place;
    private Api api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theater_info);

        imageButton = findViewById(R.id.theater_info_delete_btn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TheaterInfoActivity.super.onBackPressed();
            }
        });

        Intent intent =getIntent();
        String t_name = intent.getStringExtra("t_name");

        theater_name =findViewById(R.id.theater_info_name);
        theater_content =findViewById(R.id.theater_info_content);
        theater_phone =findViewById(R.id.theater_info_phone);
        theater_web =findViewById(R.id.theater_info_web);
        theater_place =findViewById(R.id.theater_info_place);

        (new getTheaterDetailTask()).execute(t_name);

    }
    private class getTheaterDetailTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try{
                //array : movie 전체
                JSONArray array = new JSONArray(s);
                theater_name.setText((String)array.get(1));
                theater_content.setText((String)array.get(6));
                theater_phone.setText((String)array.get(4));
                theater_web.setText((String)array.get(5));
                theater_place.setText((String)array.get(3));

            }catch (Exception e){
                e.printStackTrace();
            }


            Log.d("ddd", s+"");


        }

        @Override
        protected String doInBackground(String... strings) {

            try{
                Log.d("ddd", "bye");
                HttpGet httpPost = new HttpGet(api.API_URL+"/movie/getTheaterDetailInfo/?t_name=" + strings[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httpPost);

                // StatusLine stat = response.getStatusLine();

                //404 : page not found error
                //500 : internal server error
                //200 : 정상
                int res = response.getStatusLine().getStatusCode();
                Log.d("ddd", res+"");
                if(res >= 400){

                }
                else{
                    InputStreamReader is = new InputStreamReader(response.getEntity().getContent(), "UTF-8");
                    BufferedReader reader = new BufferedReader(is);


                    String line = null;
                    String data = "";

                    while((line = reader.readLine())!=null){
                        Log.d("ddd", line);
                        data += line;
                    }
                    reader.close();
                    is.close();
                    Log.d("ddd", data);

                    return data;
                }

            }catch (Exception e){
                Log.d("ddd", e.toString());
                e.printStackTrace();
            }
            return "";
        }
    }
}
