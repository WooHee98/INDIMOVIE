package com.example.mainindimovie_ex03.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.StaticValues;
import com.example.mainindimovie_ex03.aApi.Api;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PasswordChangeActivity extends AppCompatActivity {

    EditText old, new1, new2;
    String password;
    Button button;
    String new11 = "";
    ImageButton delete_btn;
    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        old = findViewById(R.id.oldpassword);
        new1 = findViewById(R.id.newpassword1);
        new2 = findViewById(R.id.newpassword2);
        button = findViewById(R.id.change_btn);
        delete_btn = findViewById(R.id.passwordchange_delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PasswordChangeActivity.super.onBackPressed();
            }
        });

        Intent intent = getIntent();
        password = intent.getStringExtra("password");
        Log.d("비밀번호", password);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldpass = old.getText().toString().trim();
                if (oldpass.equals(password.trim())) {
                    new11 = new1.getText().toString().trim();
                    String new22 = new2.getText().toString().trim();
                    if (new11.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$")) {

                        if (new11.equals(new22)) {
                            //버튼클릭하고 update
                            if(!new11.equals(oldpass)) {

                                (new UserpassUpdateTask()).execute();
                                Toast.makeText(getApplicationContext(), "변경되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(getApplicationContext(), MyPageActivity.class);
                                startActivity(intent1);
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), "새로운 비밀번호와 현재 비밀번호를 다르게 입력해주세요.", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            //새로운 비밀번호가 다릅니다.
                            Toast.makeText(getApplicationContext(), "새로운 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //비밀번호 형식이 아닙니다.
                        Toast.makeText(getApplicationContext(), "비밀번호가 형식에 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    //현재 비밀번호는 없는거
                    Toast.makeText(getApplicationContext(), "현재 비밀번호를 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //개인정보 수정  api통신
    private class UserpassUpdateTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("ddd", s + "");
            Log.d("ddd", "업데이트가져온");
        }


        @Override
        protected String doInBackground(String... strings) {

            try {
                HttpGet httpPost = new HttpGet(api.API_URL+"/movie/UserpassupdateView/?u_password=" + new11 + "&u_idtext=" + StaticValues.u_name);
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
