package com.example.mainindimovie_ex03.dataPack;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.MovieReviewDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.activitys.MyMovieReviewUpActivity;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MyMReviewDataAdapter extends RecyclerView.Adapter<MyMReviewDataAdapter.MyMReviewDataHolder> {
    private ArrayList<MovieReviewDataDo> moviereviewDatas;
    private onClickMyMReviewDataListener listener;
    private Context context;
    String mr_id = "";
    private Api api;

    public interface onClickMyMReviewDataListener {
        void onclick(View view, MovieReviewDataDo item);
    }

    public void setonClickMyMReviewDataListener(onClickMyMReviewDataListener listener) {
        this.listener = listener;
    }

    public MyMReviewDataAdapter(ArrayList<MovieReviewDataDo> list, Context context) {
        this.moviereviewDatas = list;
        this.context = context;
    }


    class MyMReviewDataHolder extends RecyclerView.ViewHolder {
        TextView text_regdate;
        TextView text_title;
        TextView text_content;
        ImageView review_img, icon;
        Button delete_myreview, update_myreview;

        public MyMReviewDataHolder( final View itemView) {
            super(itemView);
            text_regdate = itemView.findViewById(R.id.my_movie_detail_review_regdate);
            text_title = itemView.findViewById(R.id.my_movie_detail_review_title);
            text_content = itemView.findViewById(R.id.my_movie_detail_review_content);
            review_img = itemView.findViewById(R.id.my_movie_detail_review_img);
            icon = itemView.findViewById(R.id.review_icon);
            delete_myreview = itemView.findViewById(R.id.delete_myreview);
            delete_myreview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mr_id=(String) view.getTag();
                    Toast.makeText(context, "삭제" + mr_id, Toast.LENGTH_SHORT).show();
                    (new deletemyreviewTask()).execute();
                }
            });
            update_myreview = itemView.findViewById(R.id.update_myreview);
            update_myreview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mr_id=(String) view.getTag();
                    Toast.makeText(context, "수정" + mr_id, Toast.LENGTH_SHORT).show();
                    goScreen(context);

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onclick(itemView, moviereviewDatas.get(position));
                    }
                }
            });
        }
    }
    @NonNull
    @Override
    public MyMReviewDataHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_movie_review, viewGroup, false);
        return new MyMReviewDataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyMReviewDataHolder myMReviewDataHolder, int i) {
        MovieReviewDataDo item = moviereviewDatas.get(i);
        myMReviewDataHolder.text_regdate.setText(item.getMr_regdate());
        myMReviewDataHolder.text_title.setText(item.getMr_mtitle());
        myMReviewDataHolder.text_content.setText(item.getMr_content());
        Picasso.get().load(api.API_URL+"/static/img/review/"+item.getMr_icon()+".jpg").into(myMReviewDataHolder.icon);
        Picasso.get().load(api.API_URL +"/static/img/movie/"+item.getMr_mimage()).into(myMReviewDataHolder.review_img);
        myMReviewDataHolder.delete_myreview.setTag(item.getMr_id());
        myMReviewDataHolder.update_myreview.setTag(item.getMr_id());

    }

    @Override
    public int getItemCount() {
        return moviereviewDatas.size();
    }

    public void goScreen(Context context) {
        Intent intent = new Intent(context, MyMovieReviewUpActivity.class);
        intent.putExtra("mr_id", mr_id);
        context.startActivity(intent);
    }

    //리뷰 delete
    private class deletemyreviewTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        //다 끝나고 나서(결과값)
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }


        @Override
        protected String doInBackground(String... strings) {
            try {
                HttpGet httpget = new HttpGet(api.API_URL+"/movie/DeleteMyReviewView/?mr_id=" + mr_id);

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

