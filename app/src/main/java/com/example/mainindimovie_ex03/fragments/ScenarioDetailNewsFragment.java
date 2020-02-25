package com.example.mainindimovie_ex03.fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mainindimovie_ex03.Do.SinarioDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.activitys.ScenarioNewsDActivity;
import com.example.mainindimovie_ex03.dataPack.ScenarioNewsAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ScenarioDetailNewsFragment extends Fragment {
    View view;
    private RecyclerView recyclerView;
    private ScenarioNewsAdapter adapter;
    private Api api;
    public ScenarioDetailNewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sinario_detail_news, container, false);
        Bundle bundle = getArguments();
        (new getScenrionewsTask()).execute(bundle.getString("s_id"));
        return view;

    }
    private class getScenrionewsTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ArrayList<SinarioDataDo> temp = new ArrayList<>();

            try{
                //array : movie 전체
                JSONArray array = new JSONArray(s);
                for(int i=0;i<array.length();i++){
                    JSONArray  movie = (JSONArray)array.get(i);
                    SinarioDataDo item = new SinarioDataDo();
                    item.setSn_id((Integer) movie.get(0)+"");
                    item.setSn_title((String) movie.get(1));
                    item.setSn_regdate((String)movie.get(3));
                    temp.add(item);
                }
                adapter = new ScenarioNewsAdapter(temp);
                adapter.setonClicScenarionewsListener(new ScenarioNewsAdapter.onClicScenarionewsListener() {
                    @Override
                    public void onclick(View view, SinarioDataDo item) {
                        Intent intent = new Intent(getContext(), ScenarioNewsDActivity.class);
                        intent.putExtra("sn_id", item.getSn_id());
                        startActivity(intent);
                    }
                });

                recyclerView = (RecyclerView) view.findViewById(R.id.news_recylcer);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            try{
                HttpGet httpPost = new HttpGet(api.API_URL+"/movie/getScenarioNewsInfo?s_id="+ strings[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httpPost);

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