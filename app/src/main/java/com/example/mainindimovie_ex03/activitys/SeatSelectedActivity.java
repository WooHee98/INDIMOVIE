package com.example.mainindimovie_ex03.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SeatSelectedActivity extends AppCompatActivity {
    public final static int SIG_SEAT_SELECTED = 303;
    ImageButton seat_selected_back, seat_selected_delete;
    LinearLayout seat_selected_back_linear;
    private WebView webView; //
    private WebSettings webSettings;

    private Api api;
    int bbbb = 0;
    int bbbb1 = 0;

    String theatername = "";
    String adult = "";
    String kid = "";
    String mtid = "";
    String mt_day = "";
    String mtitle = "";
    String mtime = "";
    String result = "";//가격 합
    TextView pricetext;
    Button paybutton;

    //상영관번호
    String stid = "";

    // Javascriptinterface
    Handler handler = new Handler();

    String seatNum = "";

    //성인, 청소년 총 수
    int aktotal;

    String adultkidcount = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selected);
        (new getSeatTask()).execute(getIntent().getStringExtra("mt_id"));

        seat_selected_back = findViewById(R.id.seat_selected_back_delete_btn);
        seat_selected_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeatSelectedActivity.super.onBackPressed();
            }
        });

        seat_selected_delete = findViewById(R.id.seat_selected_delete_btn);
        seat_selected_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        Intent intent = getIntent();

        theatername = intent.getStringExtra("t_name");//영화관이름 가져오기
        adult = intent.getStringExtra("t_adult");
        kid = intent.getStringExtra("t_kid");
        mtid = intent.getStringExtra("mt_id");
        mt_day = intent.getStringExtra("day");//상영날짜 가져오기
        mtitle = intent.getStringExtra("movietitle"); //영화이름 가져오기
        mtime = intent.getStringExtra("movietime"); //영화 시간 가져오기
        Log.d("ddd", mtime + "++" + mtitle + "++" + mt_day + "++");
        Log.d("ddd", theatername);

        init();

        paybutton = findViewById(R.id.paybutton);
        paybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (seatNum.length() > 0) {
                    Toast.makeText(getApplicationContext(), "결제이동.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MovieReserPayActivity.class);
                    intent.putExtra("theatername", theatername);  //영화관
                    intent.putExtra("stid", stid); //상영관
                    intent.putExtra("day", mt_day); //상영날짜 가져오기
                    intent.putExtra("movietitle", mtitle); //영화이름 가져오기
                    intent.putExtra("movietime", mtime); //영화 시간 가져오기
                    intent.putExtra("adultkidcount", adultkidcount); //성인 청소년
                    intent.putExtra("result", result); //성인 청소년 가격 합

                    //api통신후 좌석예매
                    intent.putExtra("mtid", mtid);
                    intent.putExtra("aktotal", aktotal);
                    intent.putExtra("seatNum", seatNum);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "하단 버튼을 눌러주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init() {
        seat_selected_back_linear = findViewById(R.id.person_selected_linear);
        seat_selected_back_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SeatPersonSelected.class);
                //초기화 된 값을 보낸다.
                intent.putExtra("bbbb", bbbb);
                intent.putExtra("bbbb1", bbbb1);
                startActivityForResult(intent, 8888);
            }
        });


    }

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 8888) {
                bbbb = data.getIntExtra("bbbb", -1);
                bbbb1 = data.getIntExtra("bbbb1", -1);

                adultkidcount = "성인" + bbbb + "," + "청소년" + bbbb1;
                Log.d("ddd", adultkidcount);  //성인 수

                if (bbbb == -1 && bbbb1 == -1) {
                    Intent intent = new Intent(getApplicationContext(), SeatPersonSelected.class);
                    bbbb = 0;
                    bbbb1 = 0;
                    intent.putExtra("bbbb", bbbb);
                    intent.putExtra("bbbb1", bbbb1);
                    startActivityForResult(intent, 8888);
                } else {

                    pricetext = findViewById(R.id.pricetext);

                    //가져온 성인 값(가격)
                    int adult_int = Integer.parseInt(adult);

                    //가져온 청소년 값(가격)
                    int kid_int = Integer.parseInt(kid);

                    aktotal = bbbb + bbbb1;


                    int text1 = adult_int * bbbb;
                    int text2 = kid_int * bbbb1;
                    result = text1 + text2 + "";
                    pricetext.setText(result);

                    webView = findViewById(R.id.webview);
                    webView.setWebViewClient(new WebViewClient()); //클릭시 새창 안뜨도록
                    webView.setWebChromeClient(new WebChromeClient());
                    webSettings = webView.getSettings();//세부 세팅 등록

                    //Webview에서 자바스크립트 사용가능하도록 허용
                    webView.getSettings().setJavaScriptEnabled(true);

                    webSettings.setJavaScriptEnabled(true);//웹페이지 자바스쿨비트 허용 여부

                    webSettings.setJavaScriptEnabled(true);//웹페이지 자바스쿨비트 허용 여부
                    webSettings.setSupportMultipleWindows(false);//새창 띄우기 허용 여부
                    webSettings.setJavaScriptCanOpenWindowsAutomatically(false); //자바스크립트 새창 띄우기(멀티뷰) 허용 여부
                    webSettings.setLoadWithOverviewMode(true); // 매타태그 허용 여부
                    webSettings.setUseWideViewPort(true); //화면 사이즈 맞추기 허용 여부
                    webSettings.setSupportZoom(true);//화면 줌 허용 여부
                    webSettings.setBuiltInZoomControls(true); //화면 확대 축소 허용 여부
                    webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //컨텐츠 사이즈 맞추기
                    webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //브라우저 캐시 허용 여부
                    webSettings.setDomStorageEnabled(true);//로컬 저장소 허용 여부

                    // JavascriptInterface  이름이 indi가 되고 이걸로 통신함
                    webView.addJavascriptInterface(new SeatInterface(), "indi");


                    //상영영관가져오기
                    webView.loadUrl(api.API_URL + "/static/img/seat/" + theatername.trim() + "/" + mtid.trim() + "/" + stid + ".html?number=" + aktotal); //웹 뷰 시작

                }
            }
        }
    }

    private class SeatInterface {
        @android.webkit.JavascriptInterface
        public void testFunction(final String str) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    String[] seats = str.substring(1).split(",");
                    seatNum = str;
                    //str : &seat0=11&seat1=19....  => 이런식으로 데이터 가져온다.
                    //AsyncTask url : "~~~~~/updateSeat?u_id=~~&mt_id=~~&num=~~"+str  -> 결제버튼 누른뒤
                }
            });
        }
    }

    //좌석표 api통신
    private class getSeatTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        //다 끝나고 나서(결과값)
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ddd", s);
            ArrayList<ReservationDataDo> list = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(s);
                ReservationDataDo item = new ReservationDataDo();
                for (int i = 0; i < array.length(); i++) {
                    Log.d("상영관번호", (String) ((JSONObject) array.get(0)).get("st_id"));
                    String dd = (String) ((JSONObject) array.get(0)).get("st_id");
                    String dd1 = (String) ((JSONObject) array.get(0)).get("st_name");
                    item.setSt_name(dd1);
                    stid = item.getst_name();
                    Log.d("상영관번호", stid);

                    Log.d("번호번호", stid);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                HttpGet httpget = new HttpGet(api.API_URL+"/movie/getSeatInfo/?mt_id=" + strings[0]);

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


    //결제하기 api통신
    private class getSeatPayTask extends AsyncTask<String, Double, String> {
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
                HttpGet httpget = new HttpGet(api.API_URL+"/movie/updateSeats/?u_id=" + StaticValues.u_id + "&mt_id=" + mtid + "&num=" + aktotal + seatNum);

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
