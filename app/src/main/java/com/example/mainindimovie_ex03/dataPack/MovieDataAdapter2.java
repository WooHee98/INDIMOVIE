package com.example.mainindimovie_ex03.dataPack;

import android.content.Context;
import android.content.Intent;
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

import com.example.mainindimovie_ex03.Do.MovieDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.StaticValues;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.activitys.MovieReserActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

public class MovieDataAdapter2 extends RecyclerView.Adapter<MovieDataAdapter2.MovieDataHolder> {
    private Context context;
    private ArrayList<MovieDataDo> list;
    private onClickviewListener listener;
    private Api api;


    String m_id = "";
    String m_title = "";
    String m_age_id = "";
    int age2 = 0;
    public interface onClickviewListener {
        void onClick(View view, MovieDataDo item);
    }

    public void setOnClickviewListener(MovieDataAdapter2.onClickviewListener listener) {
        this.listener = listener;
    }


    public MovieDataAdapter2(ArrayList<MovieDataDo> list, Context context) {
        this.context = context;
        this.list = list;
    }


    @Override
    public MovieDataHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main_home_movie2, viewGroup, false);
        return new MovieDataAdapter2.MovieDataHolder(view);
    }


    public void onBindViewHolder(@NonNull MovieDataHolder movieDataHolder, int i) {
        MovieDataDo item = list.get(i);
        movieDataHolder.movie_title.setText(item.getM_title());
        ArrayList<String> tag_data = new ArrayList<String>();
        tag_data.add(item.getM_title());
        tag_data.add(item.getM_id());
        tag_data.add(item.getM_jang());
        movieDataHolder.movie_btn.setTag(tag_data);
        Picasso.get().load(api.API_URL+"/static/img/movie/"+item.getM_image_url()).into(movieDataHolder.movie_image);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class MovieDataHolder extends RecyclerView.ViewHolder {
        ImageView movie_image;
        TextView movie_title;
        Button movie_btn;

        public MovieDataHolder(final View itemview) {
            super(itemview);
            movie_image = itemview.findViewById(R.id.item_movie_image);
            movie_title = itemview.findViewById(R.id.item_movie_title);
            movie_btn = itemview.findViewById(R.id.movie_item_btn);


            if (StaticValues.u_id.length() > 0) {
                movie_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<String> data = (ArrayList<String>) view.getTag();
                        m_title = data.get(0);
                        m_id = data.get(1);
                        m_age_id = data.get(2);

                        //나이 구하기
                        Calendar calendar = Calendar.getInstance();
                        int currentYear = calendar.get(Calendar.YEAR);
                        int age1 = Integer.parseInt(StaticValues.u_age);
                        age2 = currentYear - age1 + 1;
                        Log.d("나이", age2 + "");

                        if (m_age_id.equals("전체") ) {
                            Intent intent = new Intent(context, MovieReserActivity.class);
                            intent.putExtra("selected_m_id", m_id); //영화 아이디
                            intent.putExtra("selected_m_title", m_title); //영화 제목
                            context.startActivity(intent);
                        }
                        if (m_age_id.equals("15")) {
                            int movieage = Integer.parseInt(m_age_id);

                            if (movieage > age2) {
                                Toast.makeText(context, "관람하실 수 없습니다.", Toast.LENGTH_LONG).show();
                            } else {
                                Intent intent = new Intent(context, MovieReserActivity.class);
                                intent.putExtra("selected_m_id", m_id); //영화 아이디
                                intent.putExtra("selected_m_title", m_title); //영화 제목
                                context.startActivity(intent);
                            }
                        }
                        if (m_age_id.equals("18")) {
                            int movieage11 = Integer.parseInt(m_age_id);

                            if (movieage11 > age2) {
                                Toast.makeText(context, "관람하실 수 없습니다.", Toast.LENGTH_LONG).show();

                            } else {
                                Intent intent = new Intent(context, MovieReserActivity.class);
                                intent.putExtra("selected_m_id", m_id); //영화 아이디
                                intent.putExtra("selected_m_title", m_title); //영화 제목
                                context.startActivity(intent);
                            }
                        }

                        if (m_age_id.equals("12")) {
                            int movieage11 = Integer.parseInt(m_age_id);

                            if (movieage11 > age2) {
                                Toast.makeText(context, "관람하실 수 없습니다.", Toast.LENGTH_LONG).show();

                            } else {
                                Intent intent = new Intent(context, MovieReserActivity.class);
                                intent.putExtra("selected_m_id", m_id); //영화 아이디
                                intent.putExtra("selected_m_title", m_title); //영화 제목
                                context.startActivity(intent);
                            }
                        }
                        if (m_age_id.equals("청소년관람불가")) {

                            int a = 19;
                            if (a > age2) {
                                Toast.makeText(context, "관람하실 수 없습니다.", Toast.LENGTH_LONG).show();

                            } else {
                                Intent intent = new Intent(context, MovieReserActivity.class);
                                intent.putExtra("selected_m_id", m_id); //영화 아이디
                                intent.putExtra("selected_m_title", m_title); //영화 제목
                                context.startActivity(intent);
                            }
                        }


                    }
                });
            } else {
                movie_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "로그인해주세요", Toast.LENGTH_SHORT).show();
                    }
                });
            }


            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //position을 받아온다.----------------------------------------------
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onClick(itemView, list.get(position));
                    }
                }
            });

            //      "/images/adknawd.png" << 얘는 앞에다가 http://server.com +
            //      "http://asdmlaw.com/images/aknawd.png" << 바로 넣어도 됨

            ImageView imageView = (ImageView) itemview.findViewById(R.id.item_movie_image);


        }
    }
}