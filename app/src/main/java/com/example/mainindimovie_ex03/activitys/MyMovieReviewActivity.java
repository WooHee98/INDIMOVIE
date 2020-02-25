package com.example.mainindimovie_ex03.activitys;

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

import com.example.mainindimovie_ex03.Do.MovieReviewDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.StaticValues;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.dataPack.MyMReviewDataAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MyMovieReviewActivity extends AppCompatActivity {
    ImageButton delete_btn;
    private RecyclerView recyclerView;
    private MyMReviewDataAdapter myreviewadapter;
    private SwipeRefreshLayout swipeRefreshLayout = null;

    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_movie_review);
        delete_btn = findViewById(R.id.my_movie_review_delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyMovieReviewActivity.super.onBackPressed();
            }
        });
        (new getMyReviewTask()).execute();


        swipeRefreshLayout = findViewById(R.id.refresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        (new getMyReviewTask()).execute();
                    }
                }, 1000);
            }
        });
    }



    //리뷰 get api통신
    private class getMyReviewTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        //다 끝나고 나서(결과값)
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ArrayList<MovieReviewDataDo> list1 = new ArrayList<>();
            try {
                //array : movie 전체
                JSONArray array = new JSONArray(s);
                for (int i = 0; i < array.length(); i++) {
                    JSONArray review = (JSONArray) array.get(i);
                    MovieReviewDataDo item = new MovieReviewDataDo();
                    item.setMr_id((Integer) review.get(0) + "");
                    item.setMr_content((String) review.get(1));
                    item.setMr_icon((Integer) review.get(2) + "");
                    item.setMr_regdate((String)review.get(5));
                    item.setMr_mtitle((String)review.get(7));
                    item.setMr_mimage((String)review.get(8));
                    list1.add(item);


                }
                myreviewadapter = new MyMReviewDataAdapter(list1, getApplicationContext());
                myreviewadapter.setonClickMyMReviewDataListener(new MyMReviewDataAdapter.onClickMyMReviewDataListener() {
                    @Override
                    public void onclick(View view, MovieReviewDataDo item) {
                        Toast.makeText(getApplicationContext(), "MyReview", Toast.LENGTH_SHORT).show();
                    }

                });


                recyclerView = findViewById(R.id.my_movie_review_recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(myreviewadapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        protected String doInBackground(String... strings) {
            try {
                HttpGet httpget = new HttpGet(api.API_URL+"/movie/getMyReviewView?u_id="+ StaticValues.u_id);

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