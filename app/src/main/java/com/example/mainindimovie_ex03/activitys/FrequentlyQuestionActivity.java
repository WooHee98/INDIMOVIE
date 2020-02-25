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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.FreQueDataDo;
import com.example.mainindimovie_ex03.Do.NoticeDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.dataPack.FreQueAdapter;
import com.example.mainindimovie_ex03.dataPack.NoticeDataAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FrequentlyQuestionActivity extends AppCompatActivity {
    private ImageButton delete_btn;
    private RecyclerView recyclerView;
    private FreQueAdapter adapter;
    private Api api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frequently_question);
        (new getFraqTask()).execute();
        delete_btn = findViewById(R.id.frequently_delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrequentlyQuestionActivity.super.onBackPressed();
            }
        });
        init();
    }

    private void init() {

    }

    //api통신
    private class getFraqTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        //다 끝나고 나서(결과값)
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ddd", s);

            ArrayList<FreQueDataDo> temp = new ArrayList<>();
            try {
                //array : movie 전체
                JSONArray array = new JSONArray(s);
                for (int i = 0; i < array.length(); i++) {
                    JSONArray notice = (JSONArray) array.get(i);
                    FreQueDataDo item = new FreQueDataDo();
                    item.setFaq_id((Integer) notice.get(0) + "");
                    item.setFaq_title((String) notice.get(1));
                    item.setFaq_content((String) notice.get(2));
                    temp.add(item);
                }
                adapter = new FreQueAdapter(temp);
                adapter.setonClickFreQueListener(new FreQueAdapter.onClickFreQueListener() {
                    @Override
                    public void onclick(View view, FreQueDataDo item) {
                        Intent intent = new Intent(getApplicationContext(), FrequentlyQuestionDetailActivity.class);
                        intent.putExtra("f_id", item.getFaq_id());
                        startActivity(intent);
                    }
                });



                recyclerView = findViewById(R.id.frequently_recyclerview);
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
                HttpGet httpget = new HttpGet(api.API_URL+"/movie/getFreQueInfo");

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
