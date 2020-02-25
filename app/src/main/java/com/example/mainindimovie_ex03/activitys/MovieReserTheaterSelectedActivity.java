package com.example.mainindimovie_ex03.activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.TheaterDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.dataPack.TheaterListAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MovieReserTheaterSelectedActivity extends AppCompatActivity {

    private ImageButton theater_selected_delete_btn;
    private RecyclerView theaterreserRecycler;
    private Api api;
    String adult;
    String kid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_reser_theater_selected);

        theater_selected_delete_btn = findViewById(R.id.theater_selected_delete_btn);
        theater_selected_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieReserTheaterSelectedActivity.super.onBackPressed();
            }
        });
        init();
    }

    private void init() {
        (new getTheaterTask()).execute(getIntent().getStringExtra("dd"));
    }

    //api사용
    private class getTheaterTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        //다 끝나고 나서
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("dd11d", s);

            try {
//                JSONArray array1 = new JSONArray(s);
                JSONArray arr = new JSONArray(s);
                if (arr.length() > 0) {

                    Log.d("ddd", "s는 null이 아니다");
                    ArrayList<TheaterDataDo> list = new ArrayList<>();

                    try {
                        JSONArray array = new JSONArray(s);
                        for (int i = 0; i < array.length(); i++) {
                            JSONArray area = (JSONArray) array.get(i);
                            TheaterDataDo item = new TheaterDataDo();
                            JSONArray mList = (JSONArray) area.get(0);
                            item.setT_area(mList.getString(2));
                            item.setT_adult(mList.getString(3));
                            item.setT_kid(mList.getString(4));
                            adult  =item.getT_adult();
                            kid = item.getT_kid();
                            Log.d("111", adult+kid);
                            ArrayList<String> texts = new ArrayList<>();
                            for (int k = 0; k < area.length(); k++) {
                                JSONArray theater = (JSONArray) area.get(k);
                                Log.d("ddd", theater.toString());
                                texts.add((String) theater.get(1));
                            }

                            item.setT_name(texts);
                            list.add(item);
                        }

                        theaterreserRecycler = findViewById(R.id.theater_selected_recyclerview);
                        TheaterListAdapter adapter = new TheaterListAdapter(getApplicationContext(), list);
                        theaterreserRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                        theaterreserRecycler.setHasFixedSize(true);
                        adapter.setOnTheaterListTextListener(new TheaterListAdapter.OnTheaterListTextListener() {
                            @Override
                            public void OnClicked(TextView textView) {
                                Intent intent = new Intent();
                                intent.putExtra("theatername", (String) textView.getText());
                                intent.putExtra("tadult", adult);
                                intent.putExtra("tkid", kid);
                                setResult(RESULT_OK, intent);     //startActivityForResult(intent, SIG_Theater_select); 대신 setResult쓰기!!!
                                finish();
                            }
                        });
                        theaterreserRecycler.setAdapter(adapter);

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "상영하는 영화관이 없습니다.", Toast.LENGTH_LONG).show();
                        e.printStackTrace();

                    }
                } else {
                    Log.d("ddd", "s는 null");
                    Toast.makeText(getApplicationContext(), "상영하는 영화관이 없습니다.", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("ddd", s + "");

        }

        @Override
        protected String doInBackground(String... strings) {
            try {

                HttpGet httpget = new HttpGet(api.API_URL+"/movie/getTheaterReserInfo?m_id=" + strings[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httpget);

                // StatusLine stat = response.getStatusLine();

                //404 : page not found error
                //500 : internal server error
                //200 : 정상
                int res = response.getStatusLine().getStatusCode();
                Log.d("ddd", res + "");
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
                    Log.d("ddd", data);
                    is.close();

                    return data;
                }

            } catch (Exception e) {
                Log.d("ddd", e.toString());
                e.printStackTrace();
            }
            return "";
        }
    }
}
