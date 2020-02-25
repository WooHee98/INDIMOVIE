package com.example.mainindimovie_ex03.activitiesScenario;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainindimovie_ex03.ApplicationController;
import com.example.mainindimovie_ex03.Do.Funding;
import com.example.mainindimovie_ex03.Main2Activity;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.StaticValues;
import com.example.mainindimovie_ex03.aApi.Api;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScenarioDetail_Go_M_PayActivity extends AppCompatActivity {
    ImageButton delete_btn;
    Button next_btn;
    TextView title, writer, money, text;
    EditText card1, card2, card3, card4, day, password, birth;
    RadioButton radiobtn1, radiobtn2;
    ImageButton btn1, btn2;
    AlertDialog customDialog;
    String u_id1, s_id1;
    Handler timer = new Handler(); //시간 지연 위한
    String tototal = "";
    String address="";
    private Api api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario_detail_go_m_pay);
        delete_btn = findViewById(R.id.scenario_go_pay_delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScenarioDetail_Go_M_PayActivity.super.onBackPressed();
            }
        });
        title = findViewById(R.id.go_m_pay_title);
        writer = findViewById(R.id.go_m_pay_writer);
        money = findViewById(R.id.scenario_go_m_pay_text);

        card1 = findViewById(R.id.scenario_go_m_pay_card1);
        card2 = findViewById(R.id.scenario_go_m_pay_card2);
        card3 = findViewById(R.id.scenario_go_m_pay_card3);
        card4 = findViewById(R.id.scenario_go_m_pay_card4);
        day = findViewById(R.id.scenario_go_m_pay_card5);
        password = findViewById(R.id.scenario_go_m_pay_card6);
        birth = findViewById(R.id.scenario_go_m_pay_card7);
        radiobtn1 = findViewById(R.id.scenario_go_m_pay_radio1);
        radiobtn2 = findViewById(R.id.scenario_go_m_pay_radio2);
        btn1 = findViewById(R.id.dialog1);
        btn2 = findViewById(R.id.dialog2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ScenarioDetail_Go_M_PayActivity.this);
                final LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                final View view1 = inflater.inflate(R.layout.alert_dialog_scenario_radio1, null);
                builder.setView(view1);
                customDialog = builder.create();
                customDialog.show();


                Button button = view1.findViewById(R.id.alert_scenario__btn);
                text = view1.findViewById(R.id.text11);
                text.setText("이은주이은주");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();

                    }
                });
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ScenarioDetail_Go_M_PayActivity.this);
                final LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                final View view1 = inflater.inflate(R.layout.alert_dialog_scenario_radio1, null);
                builder.setView(view1);
                customDialog = builder.create();
                customDialog.show();


                Button button = view1.findViewById(R.id.alert_scenario__btn);
                text = view1.findViewById(R.id.text11);
                text.setText("안녕하십니까");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();

                    }
                });
            }
        });

        Intent intent11 = getIntent();
        u_id1 = intent11.getStringExtra("u_id");
        s_id1 = intent11.getStringExtra("s_id");
        String pretotal1 = intent11.getStringExtra("pretotal1");
        String total = intent11.getStringExtra("total");
        tototal = (Integer.parseInt(pretotal1) + Integer.parseInt(total)) + "";
        address = intent11.getStringExtra("Address");

        final Intent intent = getIntent();
        title.setText(intent.getStringExtra("title"));
        writer.setText(intent.getStringExtra("writer"));
        money.setText(intent.getStringExtra("total"));


        next_btn = findViewById(R.id.go_money_complete_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new updateAmountTask()).execute();

                int fmoney = Integer.parseInt(money.getText().toString());
                String fcard = card1.getText().toString() + card2.getText().toString()+card3.getText().toString()+card4.getText().toString();
                String fvaildity = day.getText().toString();
                String fcardpass = password.getText().toString();

                //card1
                if (card1.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "카드번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    card1.requestFocus();
                    return;
                }
                //card2
                if (card2.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "카드번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    card2.requestFocus();
                    return;
                }
                //card3
                if (card3.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "카드번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    card3.requestFocus();
                    return;
                }
                //card4
                if (card4.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "카드번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    card4.requestFocus();
                    return;
                }
                //birth
                if (birth.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "생년월일을 입력해주세요", Toast.LENGTH_SHORT).show();
                    birth.requestFocus();
                    return;
                }
                //day
                if (day.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "유효기간을 입력해주세요", Toast.LENGTH_SHORT).show();
                    day.requestFocus();
                    return;
                }
                //password
                if (password.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "카드비밀번호 2자리를 입력해주세요", Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                    return;
                }

                if (radiobtn1.isChecked() && radiobtn2.isChecked()) {


                    Funding funding = new Funding();
                    funding.setF_amount(fmoney);
                    funding.setF_cardnum(fcard);
                    funding.setF_vaildity(fvaildity);
                    funding.setF_cardpass(fcardpass);
                    funding.setS_id(s_id1);
                    funding.setU_id(StaticValues.u_id);
                    funding.setAddress(address);

                    Log.d("ddd",fmoney+",,,,"+ fcard+",,,"+fvaildity+",,,"+Integer.parseInt(s_id1)+",,,"+Integer.parseInt(StaticValues.u_id));

                    Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.API_URL).addConverterFactory(GsonConverterFactory.create()).build();
                    Api api = retrofit.create(Api.class);
                    api.postFunding(funding).enqueue(new Callback<Funding>() {
                        @Override
                        public void onResponse(Call<Funding> call, Response<Funding> response) {
                            int StatusCode = response.code();
                            Log.i(ApplicationController.TAG, "Status Code : " + StatusCode);
                            if (response.isSuccessful()) {
                                Log.d("1", response.isSuccessful() + "");

                                Intent intent2 = new Intent(getApplicationContext(), ScenarioFinishActivity.class);
                                startActivity(intent2);

                                timer.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                }, 1000);


                            } else {
                                Log.d("2", "no");
                                Toast.makeText(getApplicationContext(), "후원실패", Toast.LENGTH_LONG).show();
                                Intent intent1 = new Intent(getApplicationContext(), ScenarioFinishActivity.class);
                                startActivity(intent1);


                                if (StatusCode == 400) {
                                    Log.d("3", "no");
                                    Toast.makeText(getApplicationContext(), "후원실패", Toast.LENGTH_LONG).show();
                                    Intent intent2 = new Intent(getApplicationContext(), ScenarioFinishActivity.class);
                                    startActivity(intent2);
                                } else {
                                    Log.d("4", "no");
                                    Toast.makeText(getApplicationContext(), "후원실패", Toast.LENGTH_LONG).show();
                                    Intent intent3 = new Intent(getApplicationContext(), ScenarioFinishActivity.class);
                                    startActivity(intent3);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Funding> call, Throwable t) {
                            Log.d("dddd", "실패");
                            Toast.makeText(getApplicationContext(), "후원실패", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "라디오버튼 모두 체크해주세요", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //시나리오 디테일
    private class updateAmountTask extends AsyncTask<String, Double, String> {
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
                HttpGet httpPost = new HttpGet(api.API_URL+"/movie/UpdateAmountView/?s_amount=" +tototal +"&s_id="+s_id1);
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
