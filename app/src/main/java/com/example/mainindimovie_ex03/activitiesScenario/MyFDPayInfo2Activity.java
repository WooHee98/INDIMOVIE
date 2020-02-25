package com.example.mainindimovie_ex03.activitiesScenario;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.Funding;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.StaticValues;
import com.example.mainindimovie_ex03.aApi.Api;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyFDPayInfo2Activity extends AppCompatActivity {
    ImageButton delete_btn;
    int f_id;
    String f_cardnum;
    String f_vaildity;
    String f_cardpass;
    EditText card1, card2, card3, card4;
    Button update;
    String f1, f2, f3;

    String a, b, c;

    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fdpay_info2);
        delete_btn = findViewById(R.id.myfdinfo2_delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyFDPayInfo2Activity.super.onBackPressed();
            }
        });


        card1 = findViewById(R.id.scenario_my_fdpay_card);

        card2 = findViewById(R.id.scenario_my_fdpay_card2);
        card3 = findViewById(R.id.scenario_my_fdpay_card3);
        Intent intent1 = getIntent();
        f_id = intent1.getIntExtra("f_id", 0);
        f_cardnum = intent1.getStringExtra("f_cardnum");
        f_vaildity = intent1.getStringExtra("f_vaildity");
        f_cardpass = intent1.getStringExtra("f_cardpass");


        card1.setText(f_cardnum);
        card2.setText(f_vaildity);
        card3.setText(f_cardpass);



        update = findViewById(R.id.update2_btn);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a = card1.getText().toString();
                b = card2.getText().toString();
                c = card3.getText().toString();
                (new getFundingUpdateTask()).execute();
                finish();
            }
        });

    }

    //내 펀딩 update api
    private class getFundingUpdateTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(), "펀딩정보 수정됨", Toast.LENGTH_SHORT).show();

        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                Log.d("ddd", "bye");
                Log.d("ddd", api.API_URL+"/movie/UpdateFundingView?f_cardnum=" + a + "&f_vaildity=" + b+ "&f_cardpass=" +c+ "&f_id=" +f_id);
                HttpGet httpPost = new HttpGet(api.API_URL+"/movie/UpdateFundingView?f_cardnum=" + a + "&f_vaildity=" + b+ "&f_cardpass=" +c+ "&f_id=" +f_id);
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
