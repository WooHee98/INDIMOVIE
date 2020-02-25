package com.example.mainindimovie_ex03.activitys;


import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;


import com.example.mainindimovie_ex03.ApplicationController;
import com.example.mainindimovie_ex03.Do.Join;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.aApi.Api;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JoinActivity extends AppCompatActivity {

    ImageButton delete_btn;
    EditText id;
    EditText password;
    EditText passwordcheck;
    ImageView password_image1;
    ImageView password_check_image1;
    EditText name;
    EditText birth1, birth2, birth3;
    EditText phone1;
    Button joinBtn1, overlapbrn;
    Api apiservice;
    TextView year;

    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);


        delete_btn = findViewById(R.id.join_delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JoinActivity.super.onBackPressed();
            }
        });

        id = findViewById(R.id.join_id_edit);

        password = findViewById(R.id.join_password_edit1);
        passwordcheck = findViewById(R.id.join_password_check_edit1);
        password_image1 = findViewById(R.id.join_password_imageview1);
        password_check_image1 = findViewById(R.id.join_password_check_imageview1);

        name = findViewById(R.id.join_name_edit);

        birth1 = findViewById(R.id.join_birth_edit1);
        birth2 = findViewById(R.id.join_birth_edit2);
        birth3 = findViewById(R.id.join_birth_edit3);

        phone1 = findViewById(R.id.join_phone_edit1);
        overlapbrn = findViewById(R.id.idtextoverlap_btn);

        /*_____________________________-__*/
        year = findViewById(R.id.year);

        phone1.addTextChangedListener(new TextWatcher() {

            private int _beforeLenght = 0;
            private int _afterLenght = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                _beforeLenght = s.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                char inputChar = s.charAt(s.length() - 1);

                _afterLenght = s.length();

                // 입력 중
                if (_beforeLenght < _afterLenght) {
                    if (_afterLenght == 4 && s.toString().indexOf("-") < 0) {
                        phone1.setText(s.toString().subSequence(0, 3) + "-" + s.toString().substring(3, s.length()));
                    } else if (_afterLenght == 9) {
                        phone1.setText(s.toString().subSequence(0, 8) + "-" + s.toString().substring(8, s.length()));
                    }
                }
                phone1.setSelection(phone1.length());

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 생략
            }
        });

        //생년월일 년도 넣기
        //현재 년도 가져오기
        Calendar nowDate = Calendar.getInstance();
        final int nowYear = nowDate.get(Calendar.YEAR);


        passwordcheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password1 = password.getText().toString();
                String passwordcheck1 = passwordcheck.getText().toString();

                if (password.getText().toString().matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$")) {

                    if (password1.equals(passwordcheck1)) {
                        password_image1.setColorFilter(Color.parseColor("#64DD17"));
                        password_check_image1.setColorFilter(Color.parseColor("#64DD17"));
                    } else {
                        password_image1.setColorFilter(Color.parseColor("#FF3D00"));
                        password_check_image1.setColorFilter(Color.parseColor("#FF3D00"));
                    }
                } else {

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        joinBtn1 = findViewById(R.id.joinBtn);
        joinBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                String idin = id.getText().toString();
                String passwordin = password.getText().toString();
                String namein = name.getText().toString();
                String birthin1 = birth1.getText().toString();
                String birthin2 = birth2.getText().toString();
                String birthin3 = birth3.getText().toString();
                String phonein = phone1.getText().toString();


                if(birthin1.length() > 0){
                    if(Integer.parseInt(birthin1) > nowYear ){
                        Toast.makeText(getApplicationContext(), nowYear+"까지 가능합니다.", Toast.LENGTH_LONG).show();
                        birth1.requestFocus();
                        return;
                    }
                    if(Integer.parseInt(birthin1) < 1800){
                        Toast.makeText(getApplicationContext(), "년도를 확인해주세요", Toast.LENGTH_LONG).show();
                        birth1.requestFocus();
                        return;
                    }
                }

                //아이디
                if (id.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "이름을 입력해주요", Toast.LENGTH_SHORT).show();
                    id.requestFocus();
                    return;
                }

                //비밀번호
                if (password.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주요", Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                    return;
                }

                //비밀번호
                if (passwordcheck.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "비밀번호체크를 입력해주요", Toast.LENGTH_SHORT).show();
                    passwordcheck.requestFocus();
                    return;
                }

                //비밀번호 일치하기 않음
                if (!password.getText().toString().equals(passwordcheck.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    password.setText("");
                    passwordcheck.setText("");
                    password.requestFocus();
                    return;
                }
                //비밀번호 자릿수 검사
                if(password.getText().toString().length()<8){
                    Toast.makeText(getApplicationContext(), "비밀번호 8자리 이상으로 입력해주세요", Toast.LENGTH_SHORT).show();;
                    password.requestFocus();
                    return;
                }


                //이름
                if (name.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "이름를 입력해주요", Toast.LENGTH_SHORT).show();
                    name.requestFocus();
                    return;
                }

                //생년월일
                if (birth1.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "생년을 입력해주요", Toast.LENGTH_SHORT).show();
                    birth1.requestFocus();
                    return;
                }
                //생년월일
                if (birth2.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "월을 입력해주요", Toast.LENGTH_SHORT).show();
                    birth2.requestFocus();
                    return;
                }

                //생년월일
                if (birth3.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "일을 입력해주요", Toast.LENGTH_SHORT).show();
                    birth3.requestFocus();
                    return;
                }

                //전화번호1
                if (phone1.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "전화번호를 입력해주요", Toast.LENGTH_SHORT).show();
                    phone1.requestFocus();
                    return;
                }

                Join join = new Join();

                join.setU_idtext(idin);
                join.setU_password(passwordin);
                join.setU_name(namein);
                join.setU_birth(birthin1);
                join.setU_birth1(birthin2);
                join.setU_birth2(birthin3);
                join.setU_phone(phonein);

                Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.API_URL).addConverterFactory(GsonConverterFactory.create()).build();
                Api api = retrofit.create(Api.class);
                api.postJoin(join).enqueue(new Callback<Join>() {

                    @Override
                    public void onResponse(Call<Join> call, Response<Join> response) {
                        int StatusCode = response.code();
                        Log.i(ApplicationController.TAG, "Status Code : " + StatusCode);
                        if (response.isSuccessful()) {
                            Log.d("1", response.isSuccessful() + "");
                            Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);

                            onBackPressed();
                        } else {
                            Log.d("2", "no");
                            Toast.makeText(getApplicationContext(), "no", Toast.LENGTH_LONG).show();


                            if (StatusCode == 400) {
                                Log.d("3", "no");
                                Toast.makeText(getApplicationContext(), "아이디 중복확인을 해주세요", Toast.LENGTH_LONG).show();

                            } else {
                                Log.d("4", "no");
                                Toast.makeText(getApplicationContext(), "회원가입실패", Toast.LENGTH_LONG).show();

                            }
                        }
                    }


                    @Override
                    public void onFailure(Call<Join> call, Throwable t) {
                        Log.d("dddd", "실패");
                        Toast.makeText(getApplicationContext(), "회원가입실패", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        overlapbrn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new getOverlapTask()).execute();
            }
        });

    }

    public void preActivity(View v){
        onBackPressed();
    }
    public void onBackPressed(){
        super.onBackPressed();
    }

    //api통신
    private class getOverlapTask extends AsyncTask<String, Double, String> {
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
                JSONObject array = new JSONObject(s);
                String dd= ((String) array.get("u_idtext"));
                Log.d("ddd", dd);
                if(dd.length()>0){
                    Toast.makeText(getApplicationContext(), "사용불가 아이디입니다.", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "사용가능 아이디입니다.", Toast.LENGTH_LONG).show();
                }


            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "사용가능 아이디입니다.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

        String idtext = id.getText().toString();

        // Java(Spring), PHP(Laravel), Node.js(Express), Python(Flask)

        @Override
        protected String doInBackground(String... strings) {
            try {
                HttpGet httpget = new HttpGet(api.API_URL+"/movie/getOverlapJoin/?u_idtext=" + idtext);

                // httpURLConnecetion
                // Retrofit
                // OKVelly

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