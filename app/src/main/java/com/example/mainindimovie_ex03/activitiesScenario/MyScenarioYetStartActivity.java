package com.example.mainindimovie_ex03.activitiesScenario;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.SinarioDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.StaticValues;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.dataPack.MySinarioDataAdapter;
import com.example.mainindimovie_ex03.dataPack.SinarioDataAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

public class MyScenarioYetStartActivity extends AppCompatActivity {

    ImageButton delete_btn;
    private RecyclerView recyclerView;
    private MySinarioDataAdapter adapter;
    private Api api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_scenario_yet_start);
        (new getmyscenarioTask()).execute();
        delete_btn = findViewById(R.id.my_senario_yet_start_delete_btn);

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyScenarioYetStartActivity.super.onBackPressed();
            }
        });


    }
    public void onBackPressed() {
        super.onBackPressed();
    }

    //시나리오 api통신
    private class getmyscenarioTask extends AsyncTask<String, Double, String> {
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
//                JSONArray array1 = new JSONArray(s);
                JSONArray arr = new JSONArray(s);
                if (arr.length() > 0) {
                    ArrayList<SinarioDataDo> temp = new ArrayList<>();
                    try {
                        JSONArray array = new JSONArray(s);
                        for (int i = 0; i < array.length(); i++) {
                            JSONArray scenario = (JSONArray) array.get(i);
                            SinarioDataDo item = new SinarioDataDo();
                            item.setS_id((Integer) scenario.get(0) + "");
                            item.setS_jang((String) scenario.get(1));
                            item.setS_title((String) scenario.get(2));
                            item.setU_id((Integer) scenario.get(8) + "");
                            item.setU_name((String) scenario.get(10));
                            item.setS_amount((Integer) scenario.get(7) + "");
                            item.setS_spon_date((String) scenario.get(6));

                            temp.add(item);
                        }
                        adapter = new MySinarioDataAdapter(temp);
                        adapter.setonClickMYSinarioDataListener(new MySinarioDataAdapter.onClickMYSinarioDataListener() {
                            @Override
                            public void onclick(View view, SinarioDataDo item) {
                                Intent intent = new Intent(getApplicationContext(), ScenarioDetailActivity.class);
                                intent.putExtra("s_id", item.getS_id());
                                intent.putExtra("u_id", item.getU_id());
                                startActivity(intent);

                            }
                        });
                        recyclerView = findViewById(R.id.sinario_recyclerview11);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false));
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "시나리오가 없습니다.", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "시나리오가 없습니다.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), MyScenarioNotActivity.class);
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
                HttpGet httpget = new HttpGet(api.API_URL+"/movie/getMyScenarioInfo/?u_id=" + StaticValues.u_id);

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
