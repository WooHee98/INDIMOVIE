package com.example.mainindimovie_ex03.activitiesRecommendation;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.ReservationDataDo;
import com.example.mainindimovie_ex03.Do.TheaterDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.dataPack.RecommKmAdapter;
import com.example.mainindimovie_ex03.dataPack.RecommMovieAdapter;
import com.google.gson.JsonObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class RecommendationListA extends AppCompatActivity {
    ImageButton delete;
    private RecyclerView recyclerView;
    private RecommMovieAdapter adapter;
    private RecommKmAdapter adapter1;
    TextView tname;
    private Api api;

    Double latitude = 0.0;
    Double longitude = 0.0;
    String latitude1 = "";
    String longitude1 = "";
    String jang = "";
    String neart_name = "";
    String mt_time = "";
    double distance=0.0;
    double theta=0.0;
    double dist=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation_list);

        delete = findViewById(R.id.recommendation_delete_btn);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecommendationListA.super.onBackPressed();
            }
        });
        tname = findViewById(R.id.tname);

        Intent intent = getIntent();
        latitude = Double.parseDouble(intent.getStringExtra("latitude"));
        longitude = Double.parseDouble(intent.getStringExtra("longitude"));
        latitude1 = latitude.toString();
        longitude1 = longitude.toString().replace("-", "");

        jang = intent.getStringExtra("jang");
        Log.d("bbb", latitude1);
        Log.d("bbb", longitude1);
        Log.d("bbb", jang + "");
        (new getKm()).execute();
        (new getRecom()).execute();
        (new getNearTh()).execute();


    }
    //km 통신
    public class getKm extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        //다 끝나고 나서(결과값)
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ArrayList<TheaterDataDo> list = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(s);
                if(array.length()>0) {
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject array1 = (JSONObject) array.get(i);
                        TheaterDataDo item = new TheaterDataDo();
                        item.setT_name1((String) array1.get("t_name"));
                        String lat = (String) array1.get("t_lat");
                        String lng = (String) array1.get("t_lng");
                        Double lat1 = Double.parseDouble(lat);
                        Double lng1 = Double.parseDouble(lng);
                        Log.d("이은주", lat1+"");
                        Log.d("이은주", lng1+"");

                        DistanceByDegree(lat1 , lng1 , latitude , longitude );
                        item.setT_km("거리  :  " + String.format("%.2f", dist) +"km");
                        list.add(item);
                    }

                    adapter1 = new RecommKmAdapter(list, getApplicationContext());
                    adapter1.setonClickRecommKmListener(new RecommKmAdapter.onClickRecommKmListener() {
                        @Override
                        public void onClick(View view, TheaterDataDo item) {
                        }
                    });

                    recyclerView = findViewById(R.id.recycler2);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false));
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(adapter1);
                }else{
                    Toast.makeText(getApplicationContext(), "데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        @Override
        protected String doInBackground(String... strings) {
            try {

                TimeZone timee;
                DateFormat time1 = new SimpleDateFormat("HH:mm", Locale.KOREA);
                timee = TimeZone.getTimeZone("Asia/Seoul");
                time1.setTimeZone(timee);

                //현재시간
                Calendar cal12 = Calendar.getInstance();
                cal12.setTime(new Date());
                mt_time = time1.format(cal12.getTime());
                Log.d("dfsdf", mt_time);

                HttpGet httpget = new HttpGet(api.API_URL+"/movie/getNearLocationList?t_lat=" + latitude1 + "&t_lng=" + longitude1 );

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
                    Log.d("ddd", data);
                    return data;

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "";
        }
    }
    //두지점(위도,경도) 사이의 거리
    public String DistanceByDegree(double _latitude1, double _longitude1, double _latitude2, double _longitude2){

        theta = _longitude1 - _longitude2;
        dist = Math.sin(DegreeToRadian(_latitude1)) * Math.sin(DegreeToRadian(_latitude2)) + Math.cos(DegreeToRadian(_latitude1))
                * Math.cos(DegreeToRadian(_latitude2)) * Math.cos(DegreeToRadian(theta));
        dist = Math.acos(dist);
        dist = RadianToDegree(dist);

        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;    // 단위 mile 에서 km 변환.

        return String.format("%.2f", dist);



    }
    //degree->radian 변환
    public double DegreeToRadian(double degree){
        return degree * Math.PI / 180.0;
    }

    //randian -> degree 변환
    public double RadianToDegree(double radian){
        return radian * 180d / Math.PI;
    }
    //추천 리스트 통신
    public class getRecom extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        //다 끝나고 나서(결과값)
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ddd", s);
            ArrayList<ReservationDataDo> list = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(s);
                if (array.length() > 0) {
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject array1 = (JSONObject) array.get(i);
                        ReservationDataDo item = new ReservationDataDo();
                        item.setMt_id1((String) array1.get("mt_id"));
                        item.setM_image_url((String) array1.get("m_image_url"));
                        item.setM_title((String) array1.get("m_title"));
                        item.setJang((String) array1.get("m_genre"));
                        item.setRuntime((String) array1.get("m_runtime"));
                        item.setT_name((String) array1.get("t_name"));
                        item.setSt_time1((String) array1.get("mt_time"));
                        item.setS_id((String) array1.get("st_id"));
                        item.setT_adult((String) array1.get("t_adult"));
                        item.setT_kid((String) array1.get("t_kid"));
                        item.setMt_date((String) array1.get("mt_day"));
                        list.add(item);
                    }

                    adapter = new RecommMovieAdapter(list, getApplicationContext());
                    adapter.setonClickRecommListener(new RecommMovieAdapter.onClickRecommListener() {
                        @Override
                        public void onClick(View view, ReservationDataDo item) {
                            Toast.makeText(getApplicationContext(), "버튼을 클릭해 주세요.", Toast.LENGTH_SHORT).show();
                        }
                    });

                    recyclerView = findViewById(R.id.recycler1);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false));
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getApplicationContext(), "데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        @Override
        protected String doInBackground(String... strings) {
            try {

                TimeZone timee;
                DateFormat time1 = new SimpleDateFormat("HH:mm", Locale.KOREA);
                timee = TimeZone.getTimeZone("Asia/Seoul");
                time1.setTimeZone(timee);

                //현재시간
                Calendar cal12 = Calendar.getInstance();
                cal12.setTime(new Date());
                mt_time = time1.format(cal12.getTime());
                Log.d("dfsdf", mt_time);

                HttpGet httpget = new HttpGet(api.API_URL + "/movie/getJangRecommdation?t_lat=" + latitude1 + "&t_lng=" + longitude1 + "&jang=" + jang +"&mt_time="+mt_time);

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
                    Log.d("ddd", data);
                    return data;

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "";
        }
    }

    //가장 가까운 영화관 가져오기
    public class getNearTh extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        //다 끝나고 나서(결과값)
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ddd", s);

            try {
                JSONObject array = new JSONObject(s);
                ReservationDataDo item = new ReservationDataDo();
                neart_name = (String) array.get("t_name");
                Log.d("t_name", neart_name);
                tname.setText(neart_name);

            } catch (
                    Exception e) {
                e.printStackTrace();
            }

        }


        @Override
        protected String doInBackground(String... strings) {
            try {
                HttpGet httpget = new HttpGet(api.API_URL + "/movie/getNearLocation?t_lat=" + latitude1 + "&t_lng=" + longitude1 + "&jang=" + jang);

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
                    Log.d("ddd", data);
                    return data;

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "";
        }
    }
}
