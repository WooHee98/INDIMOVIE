package com.example.mainindimovie_ex03;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.Sampler;
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
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.MovieDataDo;
import com.example.mainindimovie_ex03.Do.NoticeDataDo;
import com.example.mainindimovie_ex03.Do.SinarioDataDo;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.aApi.HttpRequestBuilder;
import com.example.mainindimovie_ex03.activitiesRecommendation.RecommendationMovieActivity;
import com.example.mainindimovie_ex03.activitiesScenario.ScenarioActivity;
import com.example.mainindimovie_ex03.activitiesScenario.ScenarioDetailActivity;
import com.example.mainindimovie_ex03.activitys.AdminQuestionActivity;
import com.example.mainindimovie_ex03.activitys.FrequentlyQuestionActivity;
import com.example.mainindimovie_ex03.activitys.IDPWSearchActivity;
import com.example.mainindimovie_ex03.activitys.JoinActivity;
import com.example.mainindimovie_ex03.activitys.LoginActivity;
import com.example.mainindimovie_ex03.activitys.MovieActivity;
import com.example.mainindimovie_ex03.activitys.MovieReserActivity;
import com.example.mainindimovie_ex03.activitys.MyPageActivity;
import com.example.mainindimovie_ex03.activitys.NoticeActivity;
import com.example.mainindimovie_ex03.activitys.NoticeDetailActivity;
import com.example.mainindimovie_ex03.activitys.SettingActivity;
import com.example.mainindimovie_ex03.activitys.TheaterListActivity;
import com.example.mainindimovie_ex03.dataPack.NoticeDataAdapter;
import com.example.mainindimovie_ex03.dataPack.SinarioDataAdapter;
import com.example.mainindimovie_ex03.view.MovieDataView;
import com.example.mainindimovie_ex03.view.NoticeDataView;
import com.example.mainindimovie_ex03.view.ScenarioDataView;
import com.example.mainindimovie_ex03.view.ShowDataView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;


import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.synnapps.carouselview.ViewListener;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Api api;

    private LinearLayout main_moive_parent, main_show_parent, main_scenario_parent, main_home_notice_view;
    MovieDataView view1;
    NoticeDataView view4;
    ScenarioDataView view3;
    private SwipeRefreshLayout swipeRefreshLayout = null;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    Toolbar toolbar;
    ImageButton toggle_home_btn;

    CarouselView webCarouselView;
    String[] webImageURLs = {
            "banner_(1).jpg", "banner_(3).jpg","banner_(5).jpg"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toggle_home_btn = findViewById(R.id.toggle_home_btn);
        main_moive_parent = findViewById(R.id.main_home_movie_view);
        main_show_parent = findViewById(R.id.main_home_show_view);
        main_scenario_parent = findViewById(R.id.main_home_scenario_view);
        main_home_notice_view = findViewById(R.id.main_home_notice_view);

        ArrayList<MovieDataDo> temp = new ArrayList<>();
        view1 = new MovieDataView(getApplicationContext());
        view1.setViewDate("상영 예매", temp);
        main_moive_parent.addView(view1);

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


        initView();

        swipeRefreshLayout = findViewById(R.id.refresh);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        (new getNoticeTask()).execute();
//                        (new getScenarioDataTask()).execute();
                        initView();
                    }
                }, 3000);
            }
        });

    }

    private void initView() {
        webCarouselView = findViewById(R.id.main_banner_view);
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


        findViewById(R.id.toggle_login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

            }
        });

        findViewById(R.id.toggle_join_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "회원가입 선택", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });


        findViewById(R.id.toggle_IdSearch_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "아이디비번 찾기 선택", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), IDPWSearchActivity.class);
                startActivity(intent);

            }
        });


        setNavContent();
    }

    private void setNavContent() {

        //영화예매
        findViewById(R.id.nav_content_btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StaticValues.u_id.length() == 0) {
                    Toast.makeText(getApplicationContext(), "로그인을 해주세요", Toast.LENGTH_LONG).show();
                    Intent intent11 = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent11);
                } else {
                    Intent intent = new Intent(getApplicationContext(), MovieReserActivity.class);
                    startActivity(intent);
                }

            }
        });
        //공지사항
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
                if (StaticValues.u_id.length() == 0) {
                    Toast.makeText(getApplicationContext(), "로그인을 해주세요", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                    startActivity(intent);
                }

            }
        });
        //맞춤추천
        findViewById(R.id.nav_content_multi_btn12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StaticValues.u_id.length() == 0) {
                    Toast.makeText(getApplicationContext(), "로그인을 해주세요", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), RecommendationMovieActivity.class);
                    startActivity(intent);
                }

            }
        });
        //시나리오
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

//    //시나리오 api통신
//    private class getScenarioDataTask extends AsyncTask<String, Double, String> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            ArrayList<SinarioDataDo> temp = new ArrayList<>();
//            try {
//                //array : movie 전체
//                JSONArray array = new JSONArray(s);
//                if (array.length() > 5) {
//                    for (int i = 0; i < 5; i++) {
//                        JSONArray scenario = (JSONArray) array.get(i);
//                        SinarioDataDo item = new SinarioDataDo();
//                        item.setS_id((Integer) scenario.get(0) + "");
//                        item.setS_jang((String) scenario.get(1));
//                        item.setS_title((String) scenario.get(2));
//                        item.setU_id((Integer) scenario.get(5) + "");
//                        item.setU_name((String) scenario.get(7));
//                        item.setS_regdate((String) scenario.get(3));
//
//                        temp.add(item);
//                    }
//                } else {
//                    for (int i = 0; i < array.length(); i++) {
//                        JSONArray scenario = (JSONArray) array.get(i);
//                        SinarioDataDo item = new SinarioDataDo();
//                        item.setS_id((Integer) scenario.get(0) + "");
//                        item.setS_jang((String) scenario.get(1));
//                        item.setS_title((String) scenario.get(2));
//                        item.setU_id((Integer) scenario.get(5) + "");
//                        item.setU_name((String) scenario.get(7));
//                        item.setS_regdate((String) scenario.get(3));
//
//                        temp.add(item);
//                    }
//                }
//                view3.setViewDate("신작 시나리오");
//
//                view3.adapter = new SinarioDataAdapter(temp);
//                view3.adapter.setonClickSinarioDataListener(new SinarioDataAdapter.onClickSinarioDataListener() {
//                    @Override
//                    public void onclick(View view, SinarioDataDo item) {
//                        Intent intent = new Intent(view3.getContext(), ScenarioDetailActivity.class);
//                        intent.putExtra("s_id", item.getS_id());
//                        intent.putExtra("u_id", item.getU_id());
//                        view3.context.startActivity(intent);
//                    }
//
//                });
//                view3.recyclerView.setAdapter(view3.adapter);
//
//                main_scenario_parent.removeAllViews();
//                main_scenario_parent.addView(view3);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//
//            try {
//                HttpGet httpget = new HttpGet("http://10.0.2.2:8000/movie/getScenarioInfo");
//                HttpClient httpclient = new DefaultHttpClient();
//                HttpResponse response = httpclient.execute(httpget);
//
//                // StatusLine stat = response.getStatusLine();
//
//                //404 : page not found error
//                //500 : internal server error
//                //200 : 정상
//                int res = response.getStatusLine().getStatusCode();
//                if (res >= 400) {
//
//                } else {
//                    InputStreamReader is = new InputStreamReader(response.getEntity().getContent(), "UTF-8");
//                    BufferedReader reader = new BufferedReader(is);
//
//                    String line = null;
//                    String data = "";
//
//                    while ((line = reader.readLine()) != null) {
//
//                        data += line;
//                    }
//                    reader.close();
//                    is.close();
//
//                    return data;
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return "";
//        }
//    }

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

}





