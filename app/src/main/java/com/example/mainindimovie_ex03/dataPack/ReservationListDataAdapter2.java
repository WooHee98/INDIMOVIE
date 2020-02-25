package com.example.mainindimovie_ex03.dataPack;

import android.content.Context;
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

import com.example.mainindimovie_ex03.Do.ReservationDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.StaticValues;
import com.example.mainindimovie_ex03.aApi.Api;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ReservationListDataAdapter2 extends RecyclerView.Adapter<ReservationListDataAdapter2.ReserMovieDataHolder> {

    private Context context;
    private ArrayList<ReservationDataDo> list;
    private onClickviewListener listener;
    String s_id = "";
    String mt_id = "";

    String mt_time = "";
    String mt_day = "";

    private Api api;

    public interface onClickviewListener {
        void onClick(View view, ReservationDataDo item);
    }

    public void setOnClickviewListener(onClickviewListener listener) {
        this.listener = listener;
    }


    public ReservationListDataAdapter2(ArrayList<ReservationDataDo> list, Context context) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ReserMovieDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation_list2, parent, false);
        return new ReserMovieDataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReserMovieDataHolder holder, int position) {
        ReservationDataDo item = list.get(position);

        holder.reserlist_title.setText(item.getM_title());
        holder.reserlist_theater_name.setText(item.getT_name());
        holder.reserlist_date.setText(item.getMt_date());
        holder.reserlist_seat.setText(item.getR_seat());
        holder.reserlist_time.setText(item.getSt_time1());

        ArrayList<String> tag_data = new ArrayList<String>();
        tag_data.add(item.getS_id());
        tag_data.add(item.getMt_id1());

        holder.reserlist_screentheater.setText(item.getst_name());
        holder.reservation_list_delete_btn.setTag(tag_data);
        Picasso.get().load(api.API_URL + "/static/img/movie/" + item.getM_image_url()).into(holder.reserlist_image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ReserMovieDataHolder extends RecyclerView.ViewHolder {

        ImageView reserlist_image; //이미지
        TextView reserlist_title;//제목
        TextView reserlist_date;//날짜
        TextView reserlist_time;//시간
        TextView reserlist_theater_name;//영화관
        TextView reserlist_screentheater;//상영관

        TextView reserlist_seat;//좌석

        Button reservation_list_delete_btn;

        public ReserMovieDataHolder(final View itemView) {
            super(itemView);

            reserlist_image = itemView.findViewById(R.id.item_reservation_list_image);
            reserlist_title = itemView.findViewById(R.id.item_reservation_list_title);
            reserlist_date = itemView.findViewById(R.id.item_reservation_list_date);
            reserlist_theater_name = itemView.findViewById(R.id.item_reservation_list_theater);
            reserlist_time = itemView.findViewById(R.id.item_reservation_list_time);
            reserlist_screentheater = itemView.findViewById(R.id.item_reservation_list_screentheater);

            reserlist_seat = itemView.findViewById(R.id.item_reservation_list_seat);


            reservation_list_delete_btn = itemView.findViewById(R.id.item_reservation_list_delete_btn);

            reservation_list_delete_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ArrayList<String> data = (ArrayList<String>) v.getTag();
                    s_id = data.get(0);
                    mt_id = data.get(1);
                    (new ReserCurtimeTask()).execute(mt_id);

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //position을 받아온다.----------------------------------------------
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onClick(itemView, list.get(position));

                    }

                }
            });
        }
    }

    //예매 좌석 update api 통신
    private class UpdateReserTask extends AsyncTask<String, Double, String> {
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
                HttpGet httpget = new HttpGet(api.API_URL+"/movie/UpdateReserDeView/?u_id=" + StaticValues.u_id + "&s_id=" + s_id);

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


    //예매 취소 불가
    private class ReserCurtimeTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        //다 끝나고 나서(결과값)
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray array = new JSONArray(s);
                if (array.length() > 0) {
                    mt_time = (String) array.get(1);
                    mt_day = (String) array.get(2);
                    Log.d("mttime", mt_time);
                    Log.d("mtday", mt_day);
                    TimeZone timee;
                    DateFormat time1 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA);
                    timee = TimeZone.getTimeZone("Asia/Seoul");
                    time1.setTimeZone(timee);

                    //mt_time에서 -20분 구하기
                    Calendar cal = Calendar.getInstance();
                    Date date = null;
                    date = time1.parse(mt_day + " " + mt_time + ":00");
                    cal.setTime(date);
                    cal.add(Calendar.MINUTE, -20);
                    String dd = time1.format(cal.getTime());
                    Date datea = time1.parse(dd);
                    Log.d("dfsdf", dd);

                    //현재시간
                    Calendar cal12 = Calendar.getInstance();
                    cal12.setTime(new Date());
                    String dd1 = time1.format(cal12.getTime());
                    Log.d("dfsdf", dd1);
                    Date dateb = time1.parse(dd1);

                    long duration = dateb.getTime() - datea.getTime(); // 글이 올라온시간,현재시간비교
                    long min = duration / 60000;

                    if (min>=0) { // 0분이상 지났을때/현재 시간 - mt_time의 20분전
                        Toast.makeText(context, "상영시간 20분 전에는 예매 취소가 불가능합니다.", Toast.LENGTH_LONG).show();
                        Log.d("dfsdf", "20분 이상 지남");
                    }else{
                        (new UpdateReserTask()).execute();
                        Toast.makeText(context, "예매취소되었습니다.", Toast.LENGTH_SHORT).show();
                    }

                } else {

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                HttpGet httpget = new HttpGet(api.API_URL+"/movie/getResercurtimeView/?mt_id=" + mt_id+"&s_id="+s_id);

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
