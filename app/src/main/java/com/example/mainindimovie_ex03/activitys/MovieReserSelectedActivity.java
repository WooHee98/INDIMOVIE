package com.example.mainindimovie_ex03.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.MovieDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.dataPack.ShowDataAdapter2;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MovieReserSelectedActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView recyclerView;

    private ShowDataAdapter2 adapter;

    private ImageButton movie_selected_delete_btn;

    private OnClickShowItemListener listener;
    private Api api;

    private void setOnClickShowItemListener(final OnClickShowItemListener listener) {
        this.listener = listener;

    }

    public interface OnClickShowItemListener {
        void onshowItemSelected(View view, MovieDataDo item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_selected);

        movie_selected_delete_btn = findViewById(R.id.movie_selected_delete_btn);
        movie_selected_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieReserSelectedActivity.super.onBackPressed();
            }
        });
        (new getMovieTask()).execute();

    }


    //api사용
    private class getMovieTask extends AsyncTask<String, Double, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        //다 끝나고 나서
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ArrayList<MovieDataDo> temp = new ArrayList<>();
            try{
                //s를 자동으로 array로 바꿔준다.
                //데이터설정
                //array : movie 전체
                JSONArray array = new JSONArray(s);
                for(int i=0;i<array.length();i++){
                    JSONArray  movie = (JSONArray)array.get(i);
                    MovieDataDo item = new MovieDataDo();

                    item.setM_id((Integer) movie.get(0)+"");
                    item.setM_title((String) movie.get(1));
                    item.setM_title_id((String) movie.get(2));
                    item.setM_class_id((String)movie.get(7));
                    item.setM_image_url((String)movie.get(11));
                    Log.d("ddd", item.getM_class_id());
                    temp.add(item);
                }
                //데이터를 리스트업하는 부분
                adapter = new ShowDataAdapter2(temp, context);
                adapter.setonClickShowViewListener(new ShowDataAdapter2.onClickShowViewListener() {
                    @Override
                    public void onclick(View view, MovieDataDo item) {

                        //자신을 선택한 액티비티를 가져온다.
                        Intent intent = new Intent();

                        //movie_id : 위에서 Integer에서 String으로 바꿨음,moviereserActiity로 넘김
                        intent.putExtra("movieid", item.getM_id());
                        intent.putExtra("movietitleid", item.getM_title_id());
                        String aa = item.getM_title();
                        intent.putExtra("movietitle", aa.trim());
                        intent.putExtra("movieimage", item.getM_image_url());
                        intent.putExtra("ageid", item.getM_class_id());
                        Log.d("ddd", item.getM_class_id());
                        setResult(RESULT_OK, intent);

                        finish();
                    }
                });

                recyclerView = (RecyclerView) findViewById(R.id.movie_selected_recyclerview);
                recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);


            }catch (Exception e){
                e.printStackTrace();

            }
            Log.d("ddd", s+"");
        }

        @Override
        protected String doInBackground(String... strings) {
            try {

                HttpGet httpget = new HttpGet(api.API_URL+"/movie/getMovieInfo");

                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httpget);

                // StatusLine stat = response.getStatusLine();

                //404 : page not found error
                //500 : internal server error
                //200 : 정상
                int res = response.getStatusLine().getStatusCode();
                Log.d("ddd", res+"");
                if (res >= 400) {


                } else {
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
