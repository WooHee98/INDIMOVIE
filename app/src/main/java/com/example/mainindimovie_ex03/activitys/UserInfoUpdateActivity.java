package com.example.mainindimovie_ex03.activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.StaticValues;
import com.example.mainindimovie_ex03.aApi.Api;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class UserInfoUpdateActivity extends AppCompatActivity {
    private ImageButton backbtn;

    private TextView name, year, month, day, phone, id;
    private Api api;
    Button passwordachange;

    String name1 = "";
    String idyear1 = "";
    String idmonth1 = "";
    String idday1 = "";
    String idphone1 = "";
    String idtext1 = "";
    String password="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_update);
        name = (TextView) findViewById(R.id.name); //이름
        year = (TextView) findViewById(R.id.year);  // 년도
        month = (TextView) findViewById(R.id.month); //월
        day = (TextView) findViewById(R.id.day); //일
        phone = (TextView) findViewById(R.id.phone); //폰
        id = (TextView) findViewById(R.id.id); //아이디값

        (new UserInfoUpdateTask()).execute();

        name.setText(name1.trim());
        year.setText(idyear1.trim());
        month.setText(idmonth1.trim());
        day.setText(idday1.trim());
        phone.setText(idphone1.trim());
        id.setText(idtext1.trim());

        passwordachange = findViewById(R.id.passwordchange_btn);
        passwordachange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PasswordChangeActivity.class);
                intent.putExtra("password", password );
                startActivity(intent);
                finish();
            }
        });

        backbtn = findViewById(R.id.userinfo_delete_btn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoUpdateActivity.super.onBackPressed();
            }
        });

    }

    //개인정보 수정  api통신
    private class UserInfoUpdateTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            try {

                JSONArray array = new JSONArray(s);
                for (int i = 0; i < array.length(); i++) {
                    JSONArray userupdate = (JSONArray) array.get(i);
                    name1 = (String) userupdate.get(1);
                    idyear1 = (String) userupdate.get(2);
                    idmonth1 = (String) userupdate.get(3);
                    idday1 = (String) userupdate.get(4);
                    idphone1 = (String) userupdate.get(5);
                    idtext1 = (String) userupdate.get(6);
                    password = (String)userupdate.get(7);

                    name.setText(name1.trim());
                    year.setText(idyear1.trim());
                    month.setText(idmonth1.trim());
                    day.setText(idday1.trim());
                    phone.setText(idphone1.trim());
                    id.setText(idtext1.trim());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("ddd", s + "");
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                HttpGet httpPost = new HttpGet(api.API_URL+"/movie/UserInfoupdateView/?u_id=" + StaticValues.u_id);
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
