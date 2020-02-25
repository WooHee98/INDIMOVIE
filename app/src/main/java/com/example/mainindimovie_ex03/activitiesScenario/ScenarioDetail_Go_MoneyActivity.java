package com.example.mainindimovie_ex03.activitiesScenario;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.SinarioRewardDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.dataPack.ScenarioMoneyAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ScenarioDetail_Go_MoneyActivity extends AppCompatActivity {
    ImageButton delete_btn;
    private Api api;
    private RecyclerView recyclerView;
    private ScenarioMoneyAdapter adapter;
    String s_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario_detail_go_money);
        delete_btn = findViewById(R.id.scenario_go_money_delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScenarioDetail_Go_MoneyActivity.super.onBackPressed();
            }
        });

        Intent intent = getIntent();
        s_id = intent.getStringExtra("s_id");
        Log.d("dddd", s_id);

        (new getscenarioRewardTask()).execute();


    }

    //시나리오 api통신
    private class getscenarioRewardTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        //다 끝나고 나서(결과값)
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ddd", s);

            ArrayList<SinarioRewardDo> temp = new ArrayList<>();
            try {
                //array : movie 전체
                JSONArray array = new JSONArray(s);
                for (int i = 0; i < array.length(); i++) {
                    JSONArray scenario = (JSONArray) array.get(i);
                    SinarioRewardDo item = new SinarioRewardDo();
                    item.setMoney((Integer) scenario.get(2) + "");
                    item.setReward((String) scenario.get(3));
                    item.setDelivery((Integer) scenario.get(4) + "");


                    temp.add(item);
                }
                adapter = new ScenarioMoneyAdapter(temp);
                adapter.setonClickSinarioRewardListener(new ScenarioMoneyAdapter.onClickSinarioRewardListener() {
                    @Override
                    public void onclick(View view, SinarioRewardDo item) {
                        Log.d("ddddd", item.getDelivery());
                        if(item.getDelivery().equals("0")) {
                            Intent intent11 = getIntent();
                            String u_id1 = intent11.getStringExtra("u_id");
                            String s_id1 = intent11.getStringExtra("s_id");
                            String pretotal1 = intent11.getStringExtra("pretotal1");
                            Intent intent = new Intent(getApplicationContext(), ScenarioDetail_Go_M_PayActivity.class);
                            intent.putExtra("title", intent11.getStringExtra("title"));
                            intent.putExtra("writer", intent11.getStringExtra("writer"));
                            intent.putExtra("s_id", s_id1);
                            intent.putExtra("u_id", u_id1);
                            intent.putExtra("pretotal1", pretotal1);
                            intent.putExtra("total", item.getMoney());
                            startActivity(intent);
                        }else{
                            Intent intent11 = getIntent();
                            String u_id1 = intent11.getStringExtra("u_id");
                            String s_id1 = intent11.getStringExtra("s_id");
                            String pretotal1 = intent11.getStringExtra("pretotal1");
                            Intent intent = new Intent(getApplicationContext(), ScenarioAddressActivity.class);
                            intent.putExtra("title", intent11.getStringExtra("title"));
                            intent.putExtra("writer", intent11.getStringExtra("writer"));
                            intent.putExtra("s_id", s_id1);
                            intent.putExtra("u_id", u_id1);
                            intent.putExtra("pretotal1", pretotal1);
                            intent.putExtra("total", item.getMoney());
                            startActivity(intent);
                        }

                    }

                });
                recyclerView = findViewById(R.id.recycler1);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        protected String doInBackground(String... strings) {
            try {
                HttpGet httpget = new HttpGet(api.API_URL +"/movie/getScenarioreward?r_id=" + s_id);

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
