package com.example.mainindimovie_ex03;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.MovieDataDo;
import com.example.mainindimovie_ex03.Do.NoticeDataDo;
import com.example.mainindimovie_ex03.Do.SinarioDataDo;
import com.example.mainindimovie_ex03.Do.movieseen;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.activitiesRecommendation.RecommendationMovieActivity;
import com.example.mainindimovie_ex03.activitiesScenario.ScenarioActivity;
import com.example.mainindimovie_ex03.activitiesScenario.ScenarioDetailActivity;
import com.example.mainindimovie_ex03.activitys.FrequentlyQuestionActivity;
import com.example.mainindimovie_ex03.activitys.MovieActivity;
import com.example.mainindimovie_ex03.activitys.MovieReserActivity;
import com.example.mainindimovie_ex03.activitys.MovieSeenActivity;
import com.example.mainindimovie_ex03.activitys.MyPageActivity;
import com.example.mainindimovie_ex03.activitys.NoticeActivity;
import com.example.mainindimovie_ex03.activitys.NoticeDetailActivity;
import com.example.mainindimovie_ex03.activitys.ReservationListActivity;
import com.example.mainindimovie_ex03.activitys.SettingActivity;
import com.example.mainindimovie_ex03.activitys.TheaterListActivity;
import com.example.mainindimovie_ex03.activitys.WishListActivity;
import com.example.mainindimovie_ex03.dataPack.NoticeDataAdapter;
import com.example.mainindimovie_ex03.dataPack.SinarioDataAdapter;
import com.example.mainindimovie_ex03.view.MovieDataView;
import com.example.mainindimovie_ex03.view.NoticeDataView;
import com.example.mainindimovie_ex03.view.ScenarioDataView;
import com.example.mainindimovie_ex03.view.ShowDataView;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private LinearLayout main_moive_parent, main_show_parent, main_scenario_parent, main_home_notice_view;
    private SwipeRefreshLayout swipeRefreshLayout = null;
    private LinearLayout linearLayout = null;
    private Api api;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    Toolbar toolbar;
    ImageButton toggle_home_btn;
    MovieDataView view1;
    NoticeDataView view4;
    TextView toggle_wish_text, toggel_reser_list_text, reserlistcount,movieseen;
    String wishicount = "";
    String resrlistcount = "";
    String movieseencount = "";
    ScenarioDataView view3;

    String mt_id = "";
    String mt_time = "";
    String mt_day = "";
    String m_id = "";
    String m_title = "";
    String m_image_url = "";
    String t_name = "";
    String m_genre = "";

    CarouselView webCarouselView;
    String[] webImageURLs = {
            "banner_(1).jpg",  "banner_(3).jpg", "banner_(5).jpg"

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toggle_home_btn = findViewById(R.id.toggle_home_btn);
        main_moive_parent = findViewById(R.id.main_home_movie_view);
        main_show_parent = findViewById(R.id.main_home_show_view);
        main_scenario_parent = findViewById(R.id.main_home_scenario_view);
        main_home_notice_view = findViewById(R.id.main_home_notice_view);
        reserlistcount = findViewById(R.id.reserlistcount); //메인 동그라미(예매 리스트 수)


        //위시리스트 갯수
        toggle_wish_text = findViewById(R.id.toggle_wish_text);
        (new getWishiTask()).execute();

        toggel_reser_list_text = findViewById(R.id.toggle_reservationlist_text);
        (new getResrListTask()).execute();
        (new getMyseenTask()).execute();
        (new getmovieseenTask()).execute();
        movieseen = findViewById(R.id.toggle_seenmovie_text);


        view1 = new MovieDataView(getApplicationContext());

        ShowDataView view2 = new ShowDataView(getApplicationContext());
        view2.setViewDate("상영 예정 영화");
        main_show_parent.addView(view2);

        view3 = new ScenarioDataView(getApplicationContext());
        view3.setViewDate("신작 시나리오");
        main_scenario_parent.addView(view3);

        view4 = new NoticeDataView(getApplicationContext());
        view4.setViewDate("공지사항");
        main_home_notice_view.addView(view4);

        initLayout();


        setNavHeader();
        (new getMovieTask()).execute();


        initView();

        swipeRefreshLayout = findViewById(R.id.refresh); //refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        (new getWishiTask()).execute();
                        (new getNoticeTask()).execute();
                        (new getResrListTask()).execute();
                        (new getScenarioDataTask()).execute();
                        (new getMyseenTask()).execute();
                        reserlistcount.setText(resrlistcount.trim());
                        (new getmovieseenTask()).execute();
                        movieseen.setText(movieseencount.trim());
                    }
                }, 3000);
            }
        });


    }


    private void initView() {
        webCarouselView = findViewById(R.id.main_banner_view2);
        webCarouselView.setPageCount(3);
        webCarouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Picasso.get().load(api.API_URL + "/static/img/banner/" + webImageURLs[position]).fit().centerCrop().into(imageView);
            }
        });
        webCarouselView.setIndicatorGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);

    }

    private void setNavHeader() {
        findViewById(R.id.toggle_home_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);


            }
        });

        findViewById(R.id.toggle_settings_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);

            }
        });

        findViewById(R.id.toggle_close_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);

            }
        });

        findViewById(R.id.toggle_wish_linear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WishListActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.toggle_seenmovie_linear1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieSeenActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.toggle_reservationlist_linear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReservationListActivity.class);
                startActivity(intent);
            }
        });


        setNavContent();
    }

    private void setNavContent() {

        findViewById(R.id.nav_content_btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieReserActivity.class);
                startActivity(intent);

            }
        });
        findViewById(R.id.nav_content_btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NoticeActivity.class);
                startActivity(intent);
            }
        });

        setContentsMutiTab();


    }

    private void setContentsMutiTab() {
        //영화
        findViewById(R.id.nav_content_multi_btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieActivity.class);
                intent.putExtra("initMode", "Movie");
                startActivity(intent);

            }
        });
        //영화관
        findViewById(R.id.nav_content_multi_btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TheaterListActivity.class);
                startActivity(intent);
            }
        });
        //마이인디
        findViewById(R.id.nav_content_multi_btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                startActivity(intent);

            }
        });
        //맞춤추천
        findViewById(R.id.nav_content_multi_btn12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecommendationMovieActivity.class);
                startActivity(intent);
            }
        });
        //시나리오 후원
        findViewById(R.id.nav_content_multi_btn5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScenarioActivity.class);
                startActivity(intent);
            }
        });
        //고객센터
        findViewById(R.id.nav_content_multi_btn6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FrequentlyQuestionActivity.class);
                startActivity(intent);
            }
        });


    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    private void initLayout() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        drawerLayout = (DrawerLayout) findViewById(R.id.dl_main_drawer_root);

        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        );
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    //영화
    private class getMovieTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        //다 끝나고 나서
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ArrayList<MovieDataDo> temp = new ArrayList<>();
//            for (int i = 0; i <= 10; i++) {
            try {
                //s를 자동으로 array로 바꿔준다.
                JSONArray array = new JSONArray(s);
                for (int i = 0; i < array.length(); i++) {
                    JSONArray movie = (JSONArray) array.get(i);
                    MovieDataDo item = new MovieDataDo();
                    item.setM_id((Integer) movie.get(0) + "");
                    item.setM_title((String) movie.get(1));
                    item.setM_image_url((String) movie.get(10));
                    temp.add(item);
                }
                view1.setViewDate("상영 예매", temp);
                main_moive_parent.addView(view1);
            } catch (Exception e) {

            }
//            }

        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                HttpGet httpget = new HttpGet(api.API_URL+"/movie/getMovieInfo/");
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

    //시나리오 api통신
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
                } else {
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
                view3.setViewDate("신작 시나리오");

                view3.adapter = new SinarioDataAdapter(temp);
                view3.adapter.setonClickSinarioDataListener(new SinarioDataAdapter.onClickSinarioDataListener() {
                    @Override
                    public void onclick(View view, SinarioDataDo item) {
                        Intent intent = new Intent(view3.getContext(), ScenarioDetailActivity.class);
                        intent.putExtra("s_id", item.getS_id());
                        intent.putExtra("u_id", item.getU_id());
                        view3.context.startActivity(intent);
                    }

                });
                view3.recyclerView.setAdapter(view3.adapter);

                main_scenario_parent.removeAllViews();
                main_scenario_parent.addView(view3);

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

    //공지사항 api통신
    private class getNoticeTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        //다 끝나고 나서(결과값)
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //데이터를 생성
            //array: movie 전체
            ArrayList<NoticeDataDo> temp = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(s);
                for (int i = 0; i < array.length(); i++) {
                    JSONArray notice = (JSONArray) array.get(i);
                    NoticeDataDo item = new NoticeDataDo();
                    item.setN_id((Integer) notice.get(0) + "");
                    item.setN_title((String) notice.get(1));
                    item.setN_regdate((String) notice.get(2));
                    temp.add(item);
                }
                view4.setViewDate("공지사항");
                view4.adapter = new NoticeDataAdapter(temp);
                view4.adapter.setonClickNoticeDataListener(new NoticeDataAdapter.onClickNoticeDataListener() {
                    @Override
                    public void onclick(View view, NoticeDataDo item) {
                        Toast.makeText(view4.context, "notice" + item.getN_id(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(view4.getContext(), NoticeDetailActivity.class);
                        intent.putExtra("n_id", item.getN_id());
                        view4.context.startActivity(intent);
                    }

                });
                view4.recyclerView.setAdapter(view4.adapter);

                main_home_notice_view.removeAllViews();
                main_home_notice_view.addView(view4);
            } catch (Exception e) {
                e.printStackTrace();

            }
            Log.d("ddd", s + "");
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


    //위시리스트 api통신
    private class getWishiTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        //다 끝나고 나서(결과값)
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ddd", s);

            ArrayList<MovieDataDo> temp = new ArrayList<>();
            try {
                //array : movie 전체
                JSONArray array = new JSONArray(s);
                Log.d("위시리스트1", array.length() + "");
                wishicount = array.length() + "";
                int wishicount1 = Integer.parseInt(wishicount);
                if (wishicount1 > 0) {
                    toggle_wish_text.setText(wishicount.trim());
                } else {
                    toggle_wish_text.setText("0");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        protected String doInBackground(String... strings) {
            try {
                HttpGet httpget = new HttpGet(api.API_URL+"/movie/getWishiView/?u_id=" + StaticValues.u_id);

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


    //예매내역 api통신
    private class getResrListTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        //다 끝나고 나서(결과값)
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ddd", s);

            ArrayList<MovieDataDo> temp = new ArrayList<>();
            try {
                //array : movie 전체
                JSONArray array = new JSONArray(s);
                Log.d("예매리스트", array.length() + "");
                resrlistcount = array.length() + "";
                reserlistcount.setText(resrlistcount.trim());
                int wishicount1 = Integer.parseInt(resrlistcount);
                if (wishicount1 > 0) {
                    toggel_reser_list_text.setText(resrlistcount.trim());
                } else {
                    toggel_reser_list_text.setText("0");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        protected String doInBackground(String... strings) {
            try {
                HttpGet httpget = new HttpGet(api.API_URL+"/movie/getreserlistView/?u_id=" + StaticValues.u_id);

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

    //20분 지난 건 내가 본 영화에 포함
    private class getMyseenTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        //다 끝나고 나서(결과값)
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ddd", s);

            try {
                //array : movie 전체
                JSONArray array = new JSONArray(s);
                for (int i = 0; i < array.length(); i++) {
                    JSONArray myseen = (JSONArray) array.get(i);
                    mt_time = (String) myseen.get(1);
                    mt_day = (String) myseen.get(2);




                    TimeZone timee;
                    DateFormat time1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
                    timee = TimeZone.getTimeZone("Asia/Seoul");
                    time1.setTimeZone(timee);

                    //현재 상영 시간 format
                    Calendar cal = Calendar.getInstance();
                    Date date = null;
                    date = time1.parse(mt_day + " " + mt_time + ":00");
                    cal.setTime(date);
                    String dd = time1.format(cal.getTime());
                    Date datea = time1.parse(dd);
                    Log.d("dfsdf", dd);

                    //지금 시간 format
                    Calendar cal12 = Calendar.getInstance();
                    cal12.setTime(new Date());
                    String dd1 = time1.format(cal12.getTime());
                    Log.d("dfsdf", dd1);
                    Date dateb = time1.parse(dd1);

                    long duration = dateb.getTime() - datea.getTime(); // 글이 올라온시간,현재시간비교
                    long min = duration / 60000;
                    Log.d("dddd", duration+"");
                    Log.d("dddd", min+"");


                    if (min >= 0) { // 지금 시간 > 현재 상영시간
                        //테이블 저장
                        mt_id = (Integer) myseen.get(0) + "";
                        m_id = (Integer) myseen.get(3) + "";
                        (new insertmovieseenTask()).execute();
                        Log.d("dfsdf", "테이블에 저장");
                        //수 가져오기
                        (new getmovieseenTask()).execute();

                    } else {

                        Log.d("dfsdf", "테이블에 저장 못함");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        protected String doInBackground(String... strings) {
            try {
                HttpGet httpget = new HttpGet(api.API_URL+"/movie/getMtSeenMovieView/?u_id=" + StaticValues.u_id);

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

    //movieseen insert api
    private class insertmovieseenTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                HttpGet httpPost = new HttpGet(api.API_URL+"/movie/InsertMtSeenMovieView?mt_id=" + mt_id + "&m_id=" + m_id +  "&u_id="+StaticValues.u_id);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httpPost);

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
                        Log.d("ddd", line);
                        data += line;
                    }
                    reader.close();
                    is.close();
                    Log.d("ddd", data);

                    return data;
                }

            } catch (Exception e) {
                Log.d("ddd", e.toString());
                e.printStackTrace();
            }
            return "";
        }
    }
    //movieseen 숫자 가져오기
    private class getmovieseenTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                //array : movie 전체
                JSONArray array = new JSONArray(s);
                movieseencount = array.length() + "";
                movieseen.setText(movieseencount.trim());
                int movieseen1 = Integer.parseInt(movieseencount);
                if (movieseen1 > 0) {
                    movieseen.setText(movieseencount.trim());
                } else {
                    movieseen.setText("0");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                HttpGet httpPost = new HttpGet(api.API_URL+"/movie/getmovieseenView?u_id=" + StaticValues.u_id);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httpPost);

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
                        Log.d("ddd", line);
                        data += line;
                    }
                    reader.close();
                    is.close();
                    Log.d("ddd", data);

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