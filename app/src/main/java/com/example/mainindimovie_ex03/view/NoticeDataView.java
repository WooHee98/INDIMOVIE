package com.example.mainindimovie_ex03.view;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.NoticeDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.activitys.MovieActivity;
import com.example.mainindimovie_ex03.activitys.NoticeActivity;
import com.example.mainindimovie_ex03.activitys.NoticeDetailActivity;
import com.example.mainindimovie_ex03.dataPack.NoticeDataAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class NoticeDataView extends LinearLayout {
    public Context context;
    private TextView Notice_Category_title;
    public RecyclerView recyclerView;
    public NoticeDataAdapter adapter;
    private ImageView category_button1;
    private Api api;
    private OnClickListItemListener listener;

    public void setOnClickListItemLister(final OnClickListItemListener listener) {
        this.listener = listener;
    }

    public interface OnClickListItemListener {
        void onItemSelected(View view, NoticeDataDo item);
    }

    public NoticeDataView(Context context) {
        super(context);
        this.context = context;
        setview(context);
    }

    public void goScreen(Context context) {
        Intent intent = new Intent(context, NoticeActivity.class);
        context.startActivity(intent);
    }

    private void setview(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notice_view_main_home, this, false);
        Notice_Category_title = view.findViewById(R.id.notice_data_view_title);

        category_button1 = view.findViewById(R.id.category_button1);
        category_button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                goScreen(getContext());
            }
        });


        recyclerView = view.findViewById(R.id.notice_category_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayout.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        this.addView(view);
        (new getNoticeDataTask()).execute();
    }


    public void setViewDate(String title) {
        Notice_Category_title.setText(title);

    }


    private class getNoticeDataTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ArrayList<NoticeDataDo> temp = new ArrayList<>();

            try {
                //array : movie 전체
                JSONArray array = new JSONArray(s);
                for (int i = 0; i <array.length(); i++) {
                    JSONArray notice = (JSONArray) array.get(i);
                    NoticeDataDo item = new NoticeDataDo();
                    item.setN_id((Integer) notice.get(0) + "");
                    item.setN_title((String) notice.get(1));
                    item.setN_regdate((String) notice.get(2));
                    temp.add(item);
                }
                adapter = new NoticeDataAdapter(temp);
                adapter.setonClickNoticeDataListener(new NoticeDataAdapter.onClickNoticeDataListener() {
                    @Override
                    public void onclick(View view, NoticeDataDo item) {
                        Intent intent = new Intent(getContext(), NoticeDetailActivity.class);
                        intent.putExtra("n_id", item.getN_id());
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
                HttpGet httpget = new HttpGet(api.API_URL+"/movie/getNoticeInfo");
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


