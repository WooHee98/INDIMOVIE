package com.example.mainindimovie_ex03.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.ReservationDataDo;
import com.example.mainindimovie_ex03.Main2Activity;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.StaticValues;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.dataPack.ReserMovieTimeListAdapter;
import com.example.mainindimovie_ex03.dataPack.ShowDataAdapter2;
import com.google.gson.JsonObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

public class MovieReserActivity extends AppCompatActivity {
    public final static int SIG_DATE_PICKER = 303;
    public final static int SIG_MOVIE_PICKER = 304;
    public final static int SIG_THEATER_PICKER = 305;

    private LinearLayout movielinear, spotlinear, datelinear;
    private LinearLayout reservation_date_linear;
    private TextView movieText, spotText, dateText;
    private ImageButton reservationlist_delete_btn;
    private RecyclerView recyclerView;
    private Api api;

    //영화의 번호를 넘겨준다.
    String movie_id = "";

    //MovieReserDatePickerActivity에서 넘겨온 값 변수
    String select_date = "";

    //movieDetailPresentActivity에서 받아온 id
    String movie_idd = "";

    String theater_name = "";

    //어른가격
    String adult = "";
    String kid = "";
    //상영관
    String screen1 = "";
    //나이제한하기 위해
    String ageid = "";

    int age2 = 0;

    String dd = "";

    ArrayList<String> buttons;

    Calendar nowDate = Calendar.getInstance();
    int nowYear = nowDate.get(Calendar.YEAR);
    int nowMOnth = nowDate.get(Calendar.MONTH); //+1 실제 월이 됨. 0부터 시작하기 때문
    int nowday = nowDate.get(Calendar.DAY_OF_MONTH);
    int nowWeek = nowDate.get(Calendar.DAY_OF_WEEK);// 0:일 ~7:토


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_reser);
        init();

        //리니어
        reservation_date_linear = findViewById(R.id.reservation_date_linear);

        reservationlist_delete_btn = findViewById(R.id.reservationlist_delete_btn);
        reservationlist_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieReserActivity.super.onBackPressed();
            }
        });

        //나이
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int age1 = Integer.parseInt(StaticValues.u_age);
        age2 = currentYear - age1 + 1;
        Log.d("나이", age2 + "");

    }

    private void init() {
        setDateTab();
    }

    //날짜탭
    private void setDateTab() {

        /*영화 선택 누르면*/
        movielinear = findViewById(R.id.selectmovie_layout);
        movieText = findViewById(R.id.reservation_movie_select_text);

        //영화 선택탭에서 가져온것
        String movietitle = getIntent().getStringExtra("movietitle");
        if (movietitle != null) {
            movieText.setText(movietitle);
        }


        //영화 디테일에서 예매하기 선택해서 가져온것
        String title1 = getIntent().getStringExtra("selected_m_title");
        if(title1!=null){
            movieText.setText(title1);
        }
        movie_id =getIntent().getStringExtra("selected_m_id");




        movielinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieReserSelectedActivity.class);
                startActivityForResult(intent, SIG_MOVIE_PICKER);

            }
        });
        dd = movieText.getText().toString();

        /*영화관 선택 누르면*/
        spotlinear = findViewById(R.id.selectspot_layout);
        spotText = findViewById(R.id.reservation_spot_select_text);


        spotlinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (movieText.length() == 0) {
                    //영화를 선택안했다면
                    Toast.makeText(getApplicationContext(), "영화를 먼저 선택해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), MovieReserTheaterSelectedActivity.class);
                    //받은 movie_id 정보를 MovieReserTheaterSelectedActivity에 넘긴다.
                    intent.putExtra("dd", movie_id);
                    startActivityForResult(intent, SIG_THEATER_PICKER);
                }
            }
        });


        /* 날짜 선택 누르면*/
        datelinear = findViewById(R.id.select_date_layout);
        dateText = findViewById(R.id.reservation_date_select_text);

        dateText.setText("날짜선택");
        // dateText.setText("오늘\n" + nowYear+"년"+(nowMOnth + 1) + "월" + (nowday) + "일\n" + convertWeek(nowWeek));
        datelinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieReserDatePickerActivity.class);
                //받은 movie_id 정보를 MovieReserTheaterSelectedActivity에 넘긴다.
                intent.putExtra("dd", theater_name);
                intent.putExtra("year", nowYear);
                intent.putExtra("month", nowMOnth + 1);
                intent.putExtra("day", nowday);

                int nowwmonth = nowMOnth + 1;//현재 월
                int nowwday = nowday;//현재 일

                String date = nowYear + "-" + nowwmonth + "-" + nowwday + "";


                if ((theater_name.length() == 0 && movieText.length() == 0) || (movieText.length() == 0 && theater_name.length() > 0) || (movieText.length() > 0 && theater_name.length() == 0)) {
                    Toast.makeText(getApplicationContext(), "영화/영화관을 선택해주세요", Toast.LENGTH_SHORT).show();
                    Log.d("ddd", date);
                } else {
                    startActivityForResult(intent, SIG_DATE_PICKER);
                }
                dd = movieText.getText().toString();
            }

            private void setView(Context context) {

            }
        });
    }

    private String convertWeek(int nowWeek) {
        switch (nowWeek) {
            case 0:
                return "일요일";
            case 1:
                return "월요일";
            case 2:
                return "화요일";
            case 3:
                return "수요일";
            case 4:
                return "목요일";
            case 5:
                return "금요일";
            default:
                return "일요일";
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case SIG_DATE_PICKER:
                    String selectDate = data.getStringExtra("select_date");

                    if(selectDate.equals("1")){
                        dateText.setText(nowYear + "년\n" + (nowMOnth + 2) + "월" + selectDate + "일\n");
                    }
                    else {
                        dateText.setText(nowYear + "년\n" + (nowMOnth + 1) + "월" + selectDate + "일\n");
                    }
                    //MovieReserDatePickerActivity에서 넘긴값 받음("2019-09-23")
                    select_date = data.getStringExtra("select_dbdata");




                    break;
                case SIG_MOVIE_PICKER:

                    String movietitle = data.getStringExtra("movietitle");
                    ageid = data.getStringExtra("ageid");

                    if (ageid.equals("전체") ) {
                        if (movietitle != null) {
                            movieText.setText(movietitle);
                        }
                    }
                    if (ageid.equals("15")) {
                        int movieage = Integer.parseInt(ageid);

                        if (movieage > age2) {
                            Toast.makeText(getApplicationContext(), "관람하실 수 없습니다.", Toast.LENGTH_LONG).show();
                            if (movietitle != null) {
                                movieText.setText("");
                            }
                        } else {
                            if (movietitle != null) {
                                movieText.setText(movietitle);
                            }
                        }
                    }
                    if (ageid.equals("18")) {
                        int movieage11 = Integer.parseInt(ageid);

                        if (movieage11 > age2) {
                            Toast.makeText(getApplicationContext(), "관람하실 수 없습니다.", Toast.LENGTH_LONG).show();
                            if (movietitle != null) {
                                movieText.setText("");
                            }
                        } else {
                            if (movietitle != null) {
                                movieText.setText(movietitle);
                            }
                        }
                    }

                    if (ageid.equals("12")) {
                        int movieage11 = Integer.parseInt(ageid);

                        if (movieage11 > age2) {
                            Toast.makeText(getApplicationContext(), "관람하실 수 없습니다.", Toast.LENGTH_LONG).show();
                            if (movietitle != null) {
                                movieText.setText("");
                            }
                        } else {
                            if (movietitle != null) {
                                movieText.setText(movietitle);
                            }
                        }
                    }
                    if (ageid.equals("청소년관람불가")) {

                        int a = 19;
                        if (a > age2) {
                            Toast.makeText(getApplicationContext(), "관람하실 수 없습니다.", Toast.LENGTH_LONG).show();
                            if (movietitle != null) {
                                movieText.setText("");
                            }
                        } else {

                            if (movietitle != null) {
                                movieText.setText(movietitle);
                            }
                        }
                    }

                    dd = movieText.getText().toString();

                    //moviereserSelectedActivity에서 넘긴값 받음
                    movie_id = data.getStringExtra("movieid");//영화title_id

                    break;

                case SIG_THEATER_PICKER:

                    String theatertext = data.getStringExtra("theatername");
                    if (theatertext != null) {
                        spotText.setText(theatertext);
                    }
                    //MoiveReserTheaterSelected에서  넘긴값 받음
                    theater_name = data.getStringExtra("theatername");
                    adult = data.getStringExtra("tadult");
                    kid = data.getStringExtra("tkid");
                    break;
                default:

                    super.onActivityResult(requestCode, resultCode, data);
            }


            //영화, 영화관, 날짜 선택시 api실행
            if (movieText.length() > 0 && theater_name.length() > 0 && select_date.length() > 0) {

                (new getReserTask()).execute();
            } else {


            }
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
    }


    //api사용
    private class getReserTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        //다 끝나고 나서
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ddd", s + "");

            ArrayList<ReservationDataDo> list = new ArrayList<>();
            try {
                //[[],[]] ->전체
                JSONArray array = new JSONArray(s);
                Log.d("상영관 개수", array.length() + "");
                for (int i = 0; i < array.length(); i++) {
                    //[[],[]] ->[]안의 []
                    JSONArray screen = (JSONArray) array.get(i);
                    ReservationDataDo item = new ReservationDataDo();
                    Log.d("상영시간개수", screen.length() + "");
                    //[[{},{}],[{}],[{}]]에서 각[]의 첫번째{}= get(0)
                    Log.d("상영관 이름", (String) ((JSONObject) screen.get(0)).get("st_name"));
                    item.setSt_name((String) ((JSONObject) screen.get(0)).get("st_name"));
                    screen1 = item.getst_name();
                    buttons = new ArrayList<>();
                    ArrayList<String> mtid = new ArrayList<>();
                    for (int k = 0; k < screen.length(); k++) {
                        JSONObject time = (JSONObject) screen.get(k);
                        String buttonss = (String) time.get("mt_time");

                        //trim() = >띄어쓰기 붙이는거
                        buttons.add(buttonss.trim());
                        mtid.add(((String) time.get("mt_id")).trim());
                        Log.d("상영시간", (String) time.get("mt_time"));

                    }
                    item.setSt_time(buttons);
                    item.setMt_id(mtid);
                    list.add(item);

                }
                recyclerView = findViewById(R.id.moviereser_recyclerview);
                ReserMovieTimeListAdapter adapter = new ReserMovieTimeListAdapter(getApplicationContext(), list);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                recyclerView.setHasFixedSize(true);
                adapter.setOnReserMovieTimeListListener(new ReserMovieTimeListAdapter.OnReserMovieTimeListListener() {
                    @Override
                    public void OnClicked(Button button) {
                        Intent intent = new Intent(getApplicationContext(), SeatSelectedActivity.class);
                        //getTag는 ArratList형식이라서 형변환
                        String mt_id = (String) button.getTag();
                        intent.putExtra("mt_id", mt_id);
                        intent.putExtra("t_name", theater_name);
                        intent.putExtra("t_adult", adult);
                        intent.putExtra("t_kid", kid);
                        intent.putExtra("day", select_date); //상영시간
                        String mtitle = movieText.getText().toString();
                        intent.putExtra("movietitle", mtitle);
                        intent.putExtra("movietime", button.getText() );

                        startActivity(intent);

                    }
                });
                recyclerView.setAdapter(adapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d("dsa", dd);

            try {
                HttpGet httpget = new HttpGet(api.API_URL+"/movie/getSTReserInfofo/?m_id=" + movie_id + "&t_name=" + theater_name.trim() + "&mt_day=" + select_date);

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