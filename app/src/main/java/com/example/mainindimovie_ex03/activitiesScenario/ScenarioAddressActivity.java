package com.example.mainindimovie_ex03.activitiesScenario;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.aApi.Api;

public class ScenarioAddressActivity extends AppCompatActivity {
    private WebView webView;
    private TextView txt_address;
    private Handler handler;
    private Api api1;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario_address);
        txt_address = findViewById(R.id.txt_address);

        // WebView 초기화
        init_webView();

        // 핸들러를 통한 JavaScript 이벤트 반응
        handler = new Handler();

        button = findViewById(R.id.go_money_complete_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent11 = getIntent();
                String u_id1 = intent11.getStringExtra("u_id");
                String s_id1 = intent11.getStringExtra("s_id");
                String pretotal1 = intent11.getStringExtra("pretotal1");
                String money = intent11.getStringExtra("total");
                Intent intent = new Intent(getApplicationContext(), ScenarioDetail_Go_M_PayActivity.class);
                intent.putExtra("title", intent11.getStringExtra("title"));
                intent.putExtra("writer", intent11.getStringExtra("writer"));
                intent.putExtra("s_id", s_id1);
                intent.putExtra("u_id", u_id1);
                intent.putExtra("pretotal1", pretotal1);
                intent.putExtra("total", money);
                intent.putExtra("Address", txt_address.getText().toString());
                Log.d("Dfsdf",txt_address.getText().toString());
                startActivity(intent);
            }
        });
    }

    public void init_webView() {
        // WebView 설정
        webView = (WebView) findViewById(R.id.webView_address);

        // JavaScript 허용
        webView.getSettings().setJavaScriptEnabled(true);

        // JavaScript의 window.open 허용
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);


        // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌
        webView.addJavascriptInterface(new AndroidBridge(), "TestApp");

        // web client 를 chrome 으로 설정
        webView.setWebChromeClient(new WebChromeClient());

        // webview url load. php 파일 주소
        webView.loadUrl(api1.API_URL + "/static/img/api.html"); //웹 뷰 시작

    }


    private class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    txt_address.setText(String.format("(%s) %s %s", arg1, arg2, arg3));

                    // WebView를 초기화 하지않으면 재사용할 수 없음
                    init_webView();
                }
            });
        }
    }
}


