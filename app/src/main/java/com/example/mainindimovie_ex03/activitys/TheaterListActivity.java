package com.example.mainindimovie_ex03.activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.TheaterDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.dataPack.TheaterListAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TheaterListActivity extends AppCompatActivity {
    ImageButton theater_delete_btn;
    private RecyclerView theaterRecycler;
    private Api api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theater_list);
        theater_delete_btn = findViewById(R.id.theater_delete_btn);
        theater_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TheaterListActivity.super.onBackPressed();
            }
        });
        init();
    }

    private void init() {
        (new getTheaterTask()).execute();
    }

    //api사용
    private class getTheaterTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        //다 끝나고 나서
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ddd", s);

            ArrayList<TheaterDataDo> list = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(s);
                for(int i=0;i<array.length();i++){
                    JSONArray area = (JSONArray) array.get(i);
                    TheaterDataDo item = new TheaterDataDo();
                    item.setT_id((Integer)((JSONArray)area.get(0)).get(0)+"");
                    item.setT_area((String)((JSONArray)area.get(0)).get(2));
                    Log.d("111", item.getT_area());
                    ArrayList<String> texts = new ArrayList<>();
                    for (int k = 0; k < area.length(); k++) {
                        JSONArray theater = (JSONArray) area.get(k);
                        Log.d("ddd", item.getT_id());
                        texts.add((String)theater.get(1));
                    }

                    item.setT_name(texts);
                    list.add(item);
                }
                theaterRecycler=findViewById(R.id.theater_recyclerview);
                TheaterListAdapter adapter = new TheaterListAdapter(getApplicationContext(), list);
                theaterRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                theaterRecycler.setHasFixedSize(true);
                adapter.setOnTheaterListTextListener(new TheaterListAdapter.OnTheaterListTextListener() {
                    @Override
                    public void OnClicked(TextView textView) {
                        Toast.makeText(getApplicationContext(), "영화관 : " + textView.getText(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), TheaterInfoActivity.class);
                        intent.putExtra("t_name", textView.getText());
                        startActivity(intent);
                    }

                });
                theaterRecycler.setAdapter(adapter);

            }catch (Exception e){
                e.printStackTrace();

            }
            Log.d("ddd", s+"");

        }

        @Override
        protected String doInBackground(String... strings) {
            try {

                HttpGet httpget = new HttpGet(api.API_URL+"/movie/getTheaterInfo/");
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httpget);

                // StatusLine stat = response.getStatusLine();

                //404 : page not found error
                //500 : internal server error
                //200 : 정상
                int res = response.getStatusLine().getStatusCode();
                Log.d("ddd", res+"");
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
                    Log.d("ddd", data);
                    is.close();

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
