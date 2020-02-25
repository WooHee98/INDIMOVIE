package com.example.mainindimovie_ex03.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.StaticValues;
import com.example.mainindimovie_ex03.activitiesScenario.MyFDPayInfoActivity;
import com.example.mainindimovie_ex03.activitiesScenario.MyScenarioYetStartActivity;
import com.example.mainindimovie_ex03.activitiesScenario.SponsoredScenarioActivity;

public class MyPageActivity extends AppCompatActivity {

    private ImageButton backbtn;
    private TextView idtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);


        backbtn = findViewById(R.id.reservationlist_delete_btn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        idtext = findViewById(R.id.id_text);
        idtext.setText(StaticValues.u_name);
        setNavHeader();
    }

    private void setNavHeader() {


        //개인정보수정
        findViewById(R.id.mypage_info_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserInfoUpdateActivity.class);
                startActivity(intent);

            }
        });

        //위시리스트
        findViewById(R.id.mypage_wish_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WishListActivity.class);
                startActivity(intent);

            }
        });

        //한줄평
        findViewById(R.id.mypage_oneline_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyMovieReviewActivity.class);
                startActivity(intent);

            }
        });

        //예매내역
        findViewById(R.id.mypage_reserlist_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ReservationListActivity.class);
                startActivity(intent);

            }
        });

        //본영화
        findViewById(R.id.mypage_seen_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieSeenActivity.class);
                startActivity(intent);

            }
        });

        //후원한 시나리오
        findViewById(R.id.mypage_payed_sinario_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SponsoredScenarioActivity.class);
                startActivity(intent);

            }
        });

        //내 시나리오
        findViewById(R.id.mypage_mysinario_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyScenarioYetStartActivity.class);
                startActivity(intent);

            }
        });

        //후원결제정보
        findViewById(R.id.mypage_donatepayinfo_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyFDPayInfoActivity.class);
                startActivity(intent);

            }
        });

        //관리자1:1 문의
        findViewById(R.id.mypage_admin_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminQuestionActivity.class);
                startActivity(intent);

            }
        });

        //고객센터
        //ServicecenterActivity => 1:1문의랑 자주묻는 질문 있는곳
        findViewById(R.id.mypage_center_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FrequentlyQuestionActivity.class);
                startActivity(intent);

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

       //세팅
        findViewById(R.id.setting_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });


    }
}
