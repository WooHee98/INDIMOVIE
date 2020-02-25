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

import com.example.mainindimovie_ex03.Do.ReservationDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.StaticValues;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.dataPack.MovieSeenDataAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MovieSeenActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieSeenDataAdapter adapter;

    private ImageButton delete_btn;
    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_seen);

        delete_btn = findViewById(R.id.movieseen_delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        (new getmovieseenList()).execute();


    }
    //본영화 리스트 통신
    public class getmovieseenList extends AsyncTask<String, Double, String> {
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
                    for (int i = 0; i < array.length(); i++) {
                        JSONArray array1 = (JSONArray) array.get(i);
                        ReservationDataDo item = new ReservationDataDo();
                        item.setM_title((String) array1.get(2));
                        item.setMt_date((String) array1.get(4));
                        item.setT_name((String) array1.get(3));
                        item.setM_image_url((String) array1.get(1));
                        temp.add(item);
                    }

                    adapter = new MovieSeenDataAdapter(temp, getApplicationContext());
                    adapter.setonClickmovieseenListener(new MovieSeenDataAdapter.onClickmovieseenListener() {
                        @Override
                        public void onClick(View view, ReservationDataDo item) {
                        }
                    });


                    recyclerView = findViewById(R.id.movie_seen_list_recycler);
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

                HttpGet httpget = new HttpGet(api.API_URL + "/movie/getmovieseenList?u_id=" + StaticValues.u_id);

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
