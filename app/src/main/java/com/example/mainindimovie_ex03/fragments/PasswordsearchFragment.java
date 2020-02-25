package com.example.mainindimovie_ex03.fragments;


import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.text.Editable;

import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainindimovie_ex03.MainActivity;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.StaticValues;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.activitys.PassSearchDetailActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class PasswordsearchFragment extends Fragment {
    Context context;
    Button passwordsearchbtn;
    View view;
    EditText phone, name, birth, idtext;
    TextView ranpassword;
    String phoneno;
    Handler timer = new Handler(); //시간 지연 위한
    Random rnd;
    StringBuffer buf;
    String buf1 = "";
    private Api api;
    public PasswordsearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_passwordsearch, container, false);

        name = view.findViewById(R.id.name);
        birth = view.findViewById(R.id.birth);
        idtext = view.findViewById(R.id.idtext);
        phone = (EditText) view.findViewById(R.id.phone);
        ranpassword = view.findViewById(R.id.ranpassword);

        phone.addTextChangedListener(new TextWatcher() {

            private int _beforeLenght = 0;
            private int _afterLenght = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                _beforeLenght = s.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _afterLenght = s.length();

                // 입력 중
                if (_beforeLenght < _afterLenght) {
                    if (_afterLenght == 4 && s.toString().indexOf("-") < 0) {
                        phone.setText(s.toString().subSequence(0, 3) + "-" + s.toString().substring(3, s.length()));
                    } else if (_afterLenght == 9) {
                        phone.setText(s.toString().subSequence(0, 8) + "-" + s.toString().substring(8, s.length()));
                    }
                }
                phone.setSelection(phone.length());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 생략
            }
        });

        passwordsearchbtn = (Button) view.findViewById(R.id.message_btn);
        passwordsearchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (birth.getText().toString().length() < 8) {
                    Toast.makeText(getContext(), "생년월일 다시 입력해주세요", Toast.LENGTH_SHORT).show();

                } else {
                    (new getPasssearchTask()).execute(); //비밀번호 확인 Task

                    timer.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rnd = new Random();
                            buf = new StringBuffer();


                            for (int i = 0; i < 6; i++) {
                                if (rnd.nextBoolean()) {
                                    buf.append((char) ((int) (rnd.nextInt(26)) + 97));
                                } else {
                                    buf.append((rnd.nextInt(100)));
                                }
                            }
                            buf1 = idtext.getText().toString();
                            (new passUpdateTask()).execute();
                            //ranpassword.setText(buf);
                            Toast.makeText(getContext(), "임시번호가 발급되었습니다", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), PassSearchDetailActivity.class);
                            intent.putExtra("password", (CharSequence) buf);
                            startActivity(intent);
                        }
                    }, 2000);
                }
            }
        });
        return view;

    }

    //PassssearchTask api통신
    private class getPasssearchTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        String name1 = name.getText().toString();
        String birth1 = birth.getText().toString();
        String phone1 = phone.getText().toString();
        String idtext1 = idtext.getText().toString();

        @Override
        //다 끝나고 나서(결과값)
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ddd", s);


            try {

                //s를 자동으로 array로 바꿔준다.
                JSONObject array = new JSONObject(s);
                String name = (String) array.get("u_name");
                String birth11 = (String) array.get("u_birth");
                String birth21 = (String) array.get("u_birth1");
                String birth31 = (String) array.get("u_birth2");
                String phone11 = (String) array.get("u_phone");
                String idtext11 = (String) array.get("u_idtext");


                //String 은 equals
                //실제로 서버에서 쿼리 결과를 가져오는 것이기 때문에 array.length>0이면 pass임 (일치하는 디비가 있다는 뜻)
                if (name1.equals(name) && phone1.equals(phone11) && idtext1.equals(idtext11)) {
                    Log.d("ddd", "비밀번호찾기 성공");
                    Toast.makeText(getContext(), "비밀번호찾기 찾기 성공", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), "비밀번호 찾기 실패", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Toast.makeText(getContext(), "일치하는 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }


        }

        String name2 = name.getText().toString();
        String phone2 = phone.getText().toString();
        String idtext2 = idtext.getText().toString();


        // Java(Spring), PHP(Laravel), Node.js(Express), Python(Flask)

        @Override
        protected String doInBackground(String... strings) {
            try {

                HttpGet httpget = new HttpGet(api.API_URL+"/movie/PasswordSearchView/?u_name=" + name2 + "&u_idtext=" + idtext2 + "&u_phone=" + phone2);

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


    //password update  api통신
    private class passUpdateTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ddd", s + "");
        }


        @Override
        protected String doInBackground(String... strings) {

            try {
                Log.d("1111", buf+"++++"+buf1);
                HttpGet httpPost = new HttpGet(api.API_URL+"/movie/UserpassupdateView/?u_password=" + buf + "&u_idtext=" + buf1);

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
