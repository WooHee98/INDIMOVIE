package com.example.mainindimovie_ex03.view;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.SinarioDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.activitiesScenario.ScenarioActivity;
import com.example.mainindimovie_ex03.activitiesScenario.ScenarioDetailActivity;
import com.example.mainindimovie_ex03.activitys.MovieActivity;
import com.example.mainindimovie_ex03.dataPack.SinarioDataAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ScenarioDataView extends LinearLayout {
    public Context context;
    public TextView Scenario_Category_title;
    public RecyclerView recyclerView;
    public SinarioDataAdapter adapter;
    private ImageView category_button;
    private Api api;
    private OnClickListItemListener listener;

    public void setOnClickListItemLister(final OnClickListItemListener listener) {
        this.listener = listener;
    }

    public interface OnClickListItemListener {
        void onItemSelected(View view, SinarioDataDo item);
    }

    public ScenarioDataView(Context context) {
        super(context);
        this.context = context;
        setview(context);
    }

    public void goScreen(Context context) {
        //movie라고 intent 주고 MovieActivity.class에 넘기기
        Intent intent = new Intent(context, ScenarioActivity.class);
        context.startActivity(intent);
    }

    private void setview(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_scenario_view_main_home, this, false);
        Scenario_Category_title = view.findViewById(R.id.scenario_data_view_title);


        //+버튼
        category_button = view.findViewById(R.id.category_button1);
        category_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                goScreen(getContext());
            }
        });


        recyclerView = view.findViewById(R.id.scenario_category_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayout.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        this.addView(view);
        (new getScenarioDataTask()).execute();
    }


    public void setViewDate(String title) {
        Scenario_Category_title.setText(title);


    }

    private class getScenarioDataTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ArrayList<SinarioDataDo> temp = new ArrayList<>();
            try {
                //array : movie 전체
                JSONArray array = new JSONArray(s);
                if (array.length() > 5) {
                    for (int i = 0; i < 5; i++) {
                        JSONArray scenario = (JSONArray) array.get(i);
                        SinarioDataDo item = new SinarioDataDo();
                        item.setS_id((Integer) scenario.get(0) + "");
                        item.setS_jang((String) scenario.get(1));
                        item.setS_title((String) scenario.get(2));
                        item.setU_id((Integer) scenario.get(5) + "");
                        item.setU_name((String) scenario.get(7));
                        item.setS_regdate((String) scenario.get(3));

                        temp.add(item);
                    }
                }else{
                    for (int i = 0; i < array.length(); i++) {
                        JSONArray scenario = (JSONArray) array.get(i);
                        SinarioDataDo item = new SinarioDataDo();
                        item.setS_id((Integer) scenario.get(0) + "");
                        item.setS_jang((String) scenario.get(1));
                        item.setS_title((String) scenario.get(2));
                        item.setU_id((Integer) scenario.get(5) + "");
                        item.setU_name((String) scenario.get(7));
                        item.setS_regdate((String) scenario.get(3));

                        temp.add(item);
                    }
                }
                adapter = new SinarioDataAdapter(temp);
                adapter.setonClickSinarioDataListener(new SinarioDataAdapter.onClickSinarioDataListener() {
                    @Override
                    public void onclick(View view, SinarioDataDo item) {
                        Intent intent = new Intent(getContext(), ScenarioDetailActivity.class);
                        intent.putExtra("s_id", item.getS_id());
                        intent.putExtra("u_id", item.getU_id());
                        context.startActivity(intent);
                    }

                });
                recyclerView.setAdapter(adapter);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                HttpGet httpget = new HttpGet(api.API_URL+"/movie/getScenarioInfo");
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httpget);

                // StatusLine stat = response.getStatusLine();

                //404 : page not found error
                //500 : internal server error
                //200 : 정상
                int res = response.getStatusLine().getStatusCode();
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

