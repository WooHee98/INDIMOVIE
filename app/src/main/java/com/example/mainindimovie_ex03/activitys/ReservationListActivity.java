package com.example.mainindimovie_ex03.activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.ReservationDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.StaticValues;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.dataPack.ReservationListDataAdapter;
import com.example.mainindimovie_ex03.dataPackTab.ReservaionListTabAdapter;
import com.example.mainindimovie_ex03.fragments.ReservationListFragment;
import com.google.gson.JsonObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ReservationListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ReservationListDataAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout = null;
    private Api api;
    //현재 시간
    String nowtime="";
    //현재시간 +20분
    String nowtimeadd="";
    //sttime 변수
    String st_time="";


    public interface OnClickListItemListener {
        void onItemSelected(View view, ReservationDataDo item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation__list);
        (new getReserListTask()).execute();


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
                        (new getReserListTask()).execute();
                    }
                }, 1000);
            }
        });

    }


    //예매리스트 api통신
    private class getReserListTask extends AsyncTask<String, Double, String> {
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
                if (array.length() > 0) {
                    try {
                        for (int i = 0; i < array.length(); i++) {
                            JSONArray array1 = (JSONArray) array.get(i);
                            ReservationDataDo item = new ReservationDataDo();
                            item.setR_id((Integer)array1.get(0)+"");
                            item.setM_title((String) array1.get(1));
                            item.setM_image_url((String) array1.get(2));
                            item.setMt_date((String) array1.get(3));
                            item.setT_name((String) array1.get(4));
                            st_time = (String) array1.get(5);
                            item.setSt_time1((String) array1.get(5));
                            item.setR_seat_count((Integer) array1.get(8)+"");
                            item.setSt_name((String)array1.get(7));
                            temp.add(item);


                        }
                        adapter = new ReservationListDataAdapter(temp,getApplicationContext());
                        adapter.setOnClickviewListener(new ReservationListDataAdapter.onClickviewListener() {
                            @Override
                            public void onClick(View view, ReservationDataDo item) {
                                Intent intent = new Intent(getApplicationContext(), ReservationDeListActivity.class);
                                intent.putExtra("mt_id", item.getR_id());
                                startActivity(intent);
                            }
                        });
                        recyclerView = (RecyclerView) findViewById(R.id.movie_reservation_list_recycler);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false));
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "리스트가 없습니다.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "리스트가 없습니다.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }


        @Override
        protected String doInBackground(String... strings) {
            try {
                HttpGet httpget = new HttpGet(
                        api.API_URL+"/movie/getreserlistView/?u_id=" + StaticValues.u_id);

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
