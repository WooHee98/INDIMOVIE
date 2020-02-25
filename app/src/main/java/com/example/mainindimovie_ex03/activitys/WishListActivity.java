package com.example.mainindimovie_ex03.activitys;

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

import com.example.mainindimovie_ex03.Do.MovieDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.StaticValues;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.dataPack.MovieWishiListAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class WishListActivity extends AppCompatActivity {

    private ImageButton backbtn;
    private RecyclerView recyclerView;
    private MovieWishiListAdapter adapter;
    private Api api;

    String getlm = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);

        (new getWishiTask()).execute();
        backbtn = findViewById(R.id.wishi_delete_btn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WishListActivity.super.onBackPressed();
            }
        });
    }

    //위시리스트 api통신
    private class getWishiTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        //다 끝나고 나서(결과값)
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ddd", s);

            ArrayList<MovieDataDo> temp = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(s);
                if (array.length() > 0) {
                    try {
                        //array : movie 전체
                        for (int i = 0; i < array.length(); i++) {
                            JSONArray array1 = (JSONArray) array.get(i);
                            MovieDataDo item = new MovieDataDo();
                            item.setLm_id((Integer) array1.get(0) + "");
                            item.setM_id((Integer) array1.get(1) + "");
                            item.setM_title((String) array1.get(2));
                            item.setM_jang((String) array1.get(3));
                            item.setM_runtime((String) array1.get(4));
                            item.setM_class((String) array1.get(5));
                            item.setM_regdate((String) array1.get(6));
                            item.setM_image_url((String) array1.get(7));
                            temp.add(item);


                        }
                        adapter = new MovieWishiListAdapter(temp, getApplicationContext());
                        adapter.setonClickWhishiListListener(new MovieWishiListAdapter.onClickWhishiListListener() {
                            @Override
                            public void onclick(View view, MovieDataDo item) {
                            }
                        });

                        recyclerView = (RecyclerView) findViewById(R.id.movie_wishi_recyclerview);
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
                HttpGet httpget = new HttpGet(api.API_URL+"/movie/getWishiView/?u_id=" + StaticValues.u_id);

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
