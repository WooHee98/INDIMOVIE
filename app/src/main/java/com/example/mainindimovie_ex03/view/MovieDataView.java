package com.example.mainindimovie_ex03.view;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.MovieDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.activitys.MovieActivity;
import com.example.mainindimovie_ex03.activitys.MovieDetail_Present_Activity;
import com.example.mainindimovie_ex03.dataPack.MovieDataAdapter;
import com.example.mainindimovie_ex03.fragments.MoviePresentFragment;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MovieDataView extends LinearLayout {

    private Button button;
    private Context context;
    private TextView Movie_Category_title;
    private RecyclerView recyclerView;
    private MovieDataAdapter adapter;
    private ImageView category_button;
    private Api api;

    private OnClickListItemListener listener;

    public void setOnClickListItemLister(final OnClickListItemListener listener) {
        this.listener = listener;
    }

    public interface OnClickListItemListener {
        void onItemSelected(View view, MovieDataDo item);
    }

    public MovieDataView(Context context) {
        super(context);
        this.context = context;
        setview(context);
    }

    public void goScreen(Context context) {
        //movie라고 intent 주고 MovieActivity.class에 넘기기
        Intent intent = new Intent(context, MovieActivity.class);
        intent.putExtra("initMode", "Movie");
        context.startActivity(intent);
    }

    private void setview(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie_view_main_home, this, false);
        Movie_Category_title = view.findViewById(R.id.movie_data_view_title);

        //+버튼
        category_button = view.findViewById(R.id.category_button);
        category_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //Fragment로 intent하기
                goScreen(getContext());
            }
        });

        recyclerView = view.findViewById(R.id.movie_category_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayout.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        this.addView(view);
        (new getMovieDataTask()).execute();
    }


    public void setViewDate(String title, ArrayList<MovieDataDo> templist) {
        Movie_Category_title.setText(title);
    }

    private class getMovieDataTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ArrayList<MovieDataDo> temp = new ArrayList<>();

            try{
                //array : movie 전체
                JSONArray array = new JSONArray(s);
                for(int i=0;i<array.length();i++){
                    JSONArray  movie = (JSONArray)array.get(i);
                    MovieDataDo item = new MovieDataDo();
//                    item.setM_id((Integer)movie.get("m_id") + "");
//                    item.setM_title((String)movie.get("m_title"));
                    item.setM_id((Integer) movie.get(0)+"");
                    item.setM_title((String)movie.get(1));
                    item.setM_image_url((String)movie.get(11));
                    item.setM_jang((String)movie.get(7));
                    temp.add(item);
                }
                adapter = new MovieDataAdapter(temp, getContext());
                adapter.setOnClickviewListener(new MovieDataAdapter.onClickviewListener() {
                    @Override
                    public void onClick(View view, MovieDataDo item) {
                        Intent intent = new Intent(getContext(), MovieDetail_Present_Activity.class);
                        intent.putExtra("movie_id", item.getM_id());
                        intent.putExtra("movietitle", item.getM_title());
                        Log.d("eee", item.getM_title());
                        context.startActivity(intent);
                    }
                });

                recyclerView.setAdapter(adapter);

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            try{
                HttpGet httpPost = new HttpGet(api.API_URL+"/movie/getPreMovieInfo");
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httpPost);

                // StatusLine stat = response.getStatusLine();

                //404 : page not found error
                //500 : internal server error
                //200 : 정상
                int res = response.getStatusLine().getStatusCode();
                if(res >= 400){

                }
                else{
                    InputStreamReader is = new InputStreamReader(response.getEntity().getContent(), "UTF-8");
                    BufferedReader reader = new BufferedReader(is);

                    String line = null;
                    String data = "";

                    while((line = reader.readLine())!=null){

                        data += line;
                    }
                    reader.close();
                    is.close();

                    return data;
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            return "";
        }
    }


}
