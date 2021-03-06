package com.example.mainindimovie_ex03.activitiesScenario;

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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.Funding;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.StaticValues;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.dataPack.SponsoredSinarioDataAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MyFDPayInfoActivity extends AppCompatActivity {
    private ImageButton delete_btn;
    private RecyclerView recyclerView;
    public SponsoredSinarioDataAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Api api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fdpay_info);
        (new getSponScenarioTask()).execute();
        delete_btn = findViewById(R.id.myfdinfo_delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyFDPayInfoActivity.super.onBackPressed();
            }
        });
        swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        (new getSponScenarioTask()).execute();
                    }
                }, 1000);
            }
        });

    }

    //api통신
    public class getSponScenarioTask extends AsyncTask<String, Double, String> {
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
                JSONArray array = new JSONArray(s);
                if (array.length() > 0) {
                    ArrayList<Funding> temp = new ArrayList<>();
                    try {
                        //array : movie 전체

                        for (int i = 0; i < array.length(); i++) {
                            JSONArray notice = (JSONArray) array.get(i);
                            Funding item = new Funding();
                            item.setS_jang((String) notice.get(3));
                            item.setS_title((String) notice.get(4));
                            item.setU_idtext((String) notice.get(6));
                            item.setF_amount((Integer) notice.get(1));
                            item.setS_id((Integer) notice.get(2)+"");
                            item.setF_id((Integer) notice.get(0));
                            item.setF_cardnum((String) notice.get(9));
                            item.setF_vaildity((String) notice.get(10));
                            item.setF_cardpass((String) notice.get(11));
                            temp.add(item);
                        }
                        adapter = new SponsoredSinarioDataAdapter(temp);
                        adapter.setonClickSponsoredSinarioDataListener(new SponsoredSinarioDataAdapter.onClickSponsoredSinarioDataListener() {
                            @Override
                            public void onclick(View view, Funding item) {
                                Log.d("ddd", item.getF_cardnum() + "++" + item.getF_vaildity() + "+++" + item.getF_cardpass());
                                Intent intent = new Intent(getApplicationContext(), MyFDPayInfo2Activity.class);
                                intent.putExtra("f_id", item.getF_id());
                                intent.putExtra("f_cardnum", item.getF_cardnum());
                                intent.putExtra("f_vaildity", item.getF_vaildity());
                                intent.putExtra("f_cardpass", item.getF_cardpass());
                                startActivity(intent);


                            }

                        });


                        recyclerView = findViewById(R.id.sponsored_sinario_recyclerview);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false));
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "시나리오가 없습니다.", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "후원시나리오가 없습니다.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), SponsoredScenarioNotActivity.class);
                    onBackPressed();
                    startActivity(intent);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        @Override
        protected String doInBackground(String... strings) {
            try {
                HttpGet httpget = new HttpGet(api.API_URL+"/movie/getMyFundingInfo/?u_id=" + StaticValues.u_id);

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
