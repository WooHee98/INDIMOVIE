package com.example.mainindimovie_ex03.activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.ReservationDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.StaticValues;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.dataPack.ReservationListDataAdapter2;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ReservationDeListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ReservationListDataAdapter2 adapter;
    private SwipeRefreshLayout swipeRefreshLayout = null;
    String mt_id="";
    private Api api;

    public interface OnClickListItemListener {
        void onItemSelected(View view, ReservationDataDo item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_de_list);

        Intent  intent =  getIntent();
        mt_id= intent.getStringExtra("mt_id");

        (new getReserDeListTask()).execute();

        findViewById(R.id.reservation_list_delete_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        swipeRefreshLayout = findViewById(R.id.refresh); //refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        (new getReserDeListTask()).execute();
                    }
                }, 1000);
            }
        });



    }

    //예매리스트 api통신
    private class getReserDeListTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        //다 끝나고 나서(결과값)
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ddd", s);

            ArrayList<ReservationDataDo> temp = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(s);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject array1 = (JSONObject) array.get(i);
                    ReservationDataDo item = new ReservationDataDo();
                    item.setMt_id1((String)array1.get("mt_id"));
                    item.setM_title((String) array1.get("m_title"));
                    item.setM_image_url((String) array1.get("m_image_url"));
                    item.setMt_date((String) array1.get("mt_day"));
                    item.setT_name((String) array1.get("t_name"));
                    item.setSt_time1((String) array1.get("mt_time"));
                    item.setR_seat((String) array1.get("s_name"));
                    item.setSt_name((String) array1.get("st_name"));
                    item.setS_id((String) array1.get("id"));
                    temp.add(item);

                }

                adapter = new ReservationListDataAdapter2(temp, getApplicationContext());
                adapter.setOnClickviewListener(new ReservationListDataAdapter2.onClickviewListener() {
                    @Override
                    public void onClick(View view, ReservationDataDo item) {
                        Toast.makeText(getApplicationContext(), "ReservationList", Toast.LENGTH_SHORT).show();

                    }
                });
                recyclerView = (RecyclerView) findViewById(R.id.movie_reservation_list_recycler1);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "리스트가 없습니다.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }


        @Override
        protected String doInBackground(String... strings) {
            try {

                HttpGet httpget = new HttpGet(api.API_URL+"/movie/getreserdelistView/?mt_id="+ mt_id +"&u_id=" + StaticValues.u_id);

                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httpget);

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
