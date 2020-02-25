package com.example.mainindimovie_ex03.activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.AdminQuestionDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.StaticValues;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.dataPack.AdminQuestionDataAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AdminQuestionActivity extends AppCompatActivity {
    public final static int SIG_AdminQuestion = 303;
    ImageButton delete_btn, question_edit;
    private RecyclerView recyclerView;
    private AdminQuestionDataAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout = null;
    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_question);

        delete_btn = findViewById(R.id.admin_question_delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminQuestionActivity.super.onBackPressed();
            }
        });
        question_edit = findViewById(R.id.admin_question_edit);
        question_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminQuestionWriteActivity.class);
                startActivity(intent);
            }
        });
        (new getAdminTask()).execute();
        swipeRefreshLayout = findViewById(R.id.refresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        (new getAdminTask()).execute();
                    }
                }, 1000);
            }
        });
    }


    //리뷰 get api통신
    private class getAdminTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        //다 끝나고 나서(결과값)
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ArrayList<AdminQuestionDataDo> list = new ArrayList<>();
            try {
                //array : movie 전체
                JSONArray array = new JSONArray(s);
                for (int i = 0; i < array.length(); i++) {
                    JSONArray admin = (JSONArray) array.get(i);
                    AdminQuestionDataDo item = new AdminQuestionDataDo();
                    item.setAq_regdate((String) admin.get(2));
                    item.setAq_title((String) admin.get(1));
                    item.setAq_content((String) admin.get(3));
                    item.setAq_answer((String) admin.get(4));
                    list.add(item);

                }
                adapter = new AdminQuestionDataAdapter(list);
                adapter.setonClickAdminQuestionDataListener(new AdminQuestionDataAdapter.onClickAdminQuestionDataListener() {
                    @Override
                    public void onclick(View view, AdminQuestionDataDo item) {
                        Intent intent = new Intent(getApplicationContext(), AdminQuestionDetailActivity.class);
                        intent.putExtra("regdate", item.getAq_regdate());
                        intent.putExtra("title", item.getAq_title());
                        intent.putExtra("content", item.getAq_content());
                        intent.putExtra("answer", item.getAq_answer());
                        startActivityForResult(intent, SIG_AdminQuestion);
                    }
                });


                recyclerView = findViewById(R.id.admin_question_recyclerview);
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
                HttpGet httpget = new HttpGet(api.API_URL+"/movie/getAdminQView?u_id=" + StaticValues.u_id);

                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httpget);


                // StatusLine stat = response.getStatusLine();

                //404 : page not found error
                //500 : internal server error
                //200 : 정상
                //접속 결과 코드에 따라
                int res = response.getStatusLine().getStatusCode();
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
