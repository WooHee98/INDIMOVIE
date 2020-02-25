package com.example.mainindimovie_ex03.dataPack;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.MovieDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.StaticValues;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.activitys.WishListActivity;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MovieWishiListAdapter extends RecyclerView.Adapter<MovieWishiListAdapter.MovieWishiHolder> {
    private Context context;
    private ArrayList<MovieDataDo> datas1;
    private onClickWhishiListListener listener;
    int position;
    String lm_id = "";
    private Api api;

    public interface onClickWhishiListListener {
        void onclick(View view, MovieDataDo item);
    }

    public void setonClickWhishiListListener(onClickWhishiListListener listener) {
        this.listener = listener;
    }

    public MovieWishiListAdapter(ArrayList<MovieDataDo> datas, Context context) {
        this.datas1 = datas;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieWishiHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie_wishi, viewGroup, false);
        return new MovieWishiHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieWishiHolder movieWishiHolder, int i) {
        MovieDataDo item = datas1.get(i);
        movieWishiHolder.movie_title.setText(item.getM_title());
        movieWishiHolder.movie_jang.setText(item.getM_jang());
        movieWishiHolder.movie_runtime.setText(item.getM_runtime());
        movieWishiHolder.movie_class.setText(item.getM_class());
        movieWishiHolder.movie_start.setText(item.getM_regdate());
        movieWishiHolder.movie_wishi_Delete_Btn.setTag(item.getLm_id());
        Picasso.get().load(api.API_URL + "/static/img/movie/" + item.getM_image_url()).into(movieWishiHolder.movie_image);
    }

    @Override
    public int getItemCount() {
        return datas1.size();
    }


    class MovieWishiHolder extends RecyclerView.ViewHolder {
        ImageView movie_image;
        TextView movie_title, movie_jang, movie_runtime, movie_class, movie_start;
        ImageButton movie_wishi_Delete_Btn;


        public MovieWishiHolder(final View itemView) {
            super(itemView);
            movie_image = itemView.findViewById(R.id.item_movie_wishi_image);
            movie_title = itemView.findViewById(R.id.item_movie_wishi_title);
            movie_jang = itemView.findViewById(R.id.item_movie_wishi_jang);
            movie_runtime = itemView.findViewById(R.id.item_movie_wishi_runtime);
            movie_class = itemView.findViewById(R.id.item_movie_wishi_class);
            movie_start = itemView.findViewById(R.id.item_movie_wishi_start);

            movie_wishi_Delete_Btn = itemView.findViewById(R.id.movie_wishi_delete);
            movie_wishi_Delete_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lm_id = (String) v.getTag();
                    Toast.makeText(context, "삭제" + lm_id, Toast.LENGTH_SHORT).show();
                    (new getWishideleteTask()).execute();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Log.d("ddd", position + 1 + "");
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onclick(itemView, datas1.get(position));
                    }
                }
            });
        }
    }


    //위시리스트 delete api통신
    private class getWishideleteTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        //다 끝나고 나서(결과값)
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ddd", s);


        }


        @Override
        protected String doInBackground(String... strings) {
            try {
                HttpGet httpget = new HttpGet(api.API_URL+"/movie/DeleteWishiView/?lm_id=" + lm_id);

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


