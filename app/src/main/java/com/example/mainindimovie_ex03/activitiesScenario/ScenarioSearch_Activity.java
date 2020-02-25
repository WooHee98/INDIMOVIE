package com.example.mainindimovie_ex03.activitiesScenario;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.mainindimovie_ex03.Do.SinarioDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.dataPack.SinarioDataAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ScenarioSearch_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EditText sinario_search_edit;
    private ImageButton sinario_search_btn;
    private SinarioDataAdapter adapter;
    String edit;
    private ImageButton my_senario_delete_btn;
    private Api api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario_search_);
        sinario_search_edit = findViewById(R.id.sinario_search_edit);
        sinario_search_btn = findViewById(R.id.sinario_search_btn);

        my_senario_delete_btn =findViewById(R.id.my_senario_delete_btn);
        my_senario_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScenarioSearch_Activity.super.onBackPressed();
            }
        });
        sinario_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit = sinario_search_edit.getText().toString();
                (new getscenarioSearchTask()).execute();
            }
        });

    }

    //시나리오 api통신
    private class getscenarioSearchTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        //다 끝나고 나서(결과값)
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ArrayList<SinarioDataDo> temp = new ArrayList<>();
            try {
                //array : movie 전체
                JSONArray array = new JSONArray(s);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject array1 = (JSONObject) array.get(i);
                    SinarioDataDo item = new SinarioDataDo();
                    item.setS_id((String) array1.get("s_id"));
                    item.setS_jang((String) array1.get("s_jang"));
                    item.setS_title((String) array1.get("s_title"));
                    item.setU_id((String) array1.get("u_id_id"));
                    item.setU_name((String) array1.get("u_idtext"));
                    item.setS_regdate((String) array1.get("s_regdate"));

                    temp.add(item);
                }
                adapter = new SinarioDataAdapter(temp);
                adapter.setonClickSinarioDataListener(new SinarioDataAdapter.onClickSinarioDataListener() {
                    @Override
                    public void onclick(View view, SinarioDataDo item) {
                        Intent intent = new Intent(getApplicationContext(), ScenarioDetailActivity.class);
                        intent.putExtra("s_id", item.getS_id());
                        intent.putExtra("u_id", item.getU_id());
                        startActivity(intent);

                    }
                });
                recyclerView = findViewById(R.id.recycler);
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
                HttpGet httpget = new HttpGet(api.API_URL+"/movie/getScenarioSearch?s_title=" + edit);

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
