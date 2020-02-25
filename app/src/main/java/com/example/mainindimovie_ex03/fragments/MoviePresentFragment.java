package com.example.mainindimovie_ex03.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.MovieDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.activitys.MovieDetail_Present_Activity;
import com.example.mainindimovie_ex03.dataPack.MovieDataAdapter2;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviePresentFragment extends Fragment {

    View view;
    private RecyclerView recyclerView;
    private MovieDataAdapter2 adapter;
    private OnClickListItemListener listener;
    private Api api;
    public void setOnClickListItemLister(final OnClickListItemListener listener) {
        this.listener = listener;
    }

    public interface OnClickListItemListener {
        void onItemSelected(View view, MovieDataDo item);
    }


    public MoviePresentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = (View) inflater.inflate(R.layout.fragment_movie_present, container, false);

        (new getMovieTask()).execute();

        return view;


    }

    private class getMovieTask extends AsyncTask<String, Double, String> {
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
                    item.setM_id((Integer) movie.get(0)+"");
                    item.setM_title((String) movie.get(1));
                    item.setM_image_url((String)movie.get(11));
                    item.setM_jang((String)movie.get(7));
                    temp.add(item);
                }
                adapter = new MovieDataAdapter2(temp, getContext());
                adapter.setOnClickviewListener(new MovieDataAdapter2.onClickviewListener() {
                    @Override
                    public void onClick(View view, MovieDataDo item) {
                        Toast.makeText(getContext(), "PresentMovie" + item.getM_id(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), MovieDetail_Present_Activity.class);
                        intent.putExtra("movie_id", item.getM_id());
                        intent.putExtra("movietitle", item.getM_title());
                        startActivity(intent);
                    }
                });

                recyclerView = (RecyclerView) view.findViewById(R.id.movie_present_list);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                recyclerView.setHasFixedSize(true);
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
