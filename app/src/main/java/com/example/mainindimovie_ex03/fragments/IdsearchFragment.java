package com.example.mainindimovie_ex03.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.UserDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.activitys.IDPWSearchActivity;
import com.example.mainindimovie_ex03.activitys.IdSearchDetailActivity;
import com.example.mainindimovie_ex03.activitys.LoginActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class IdsearchFragment extends Fragment {
    public static Fragment IdsearchFragment;//내

    Button idsearchbtn;
    View view;
    EditText name, birth, phone;
    private Api api;

    public IdsearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_idsearch, container, false);

        name = view.findViewById(R.id.name);
        birth = view.findViewById(R.id.birth);
        phone = view.findViewById(R.id.phone);


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

        idsearchbtn = (Button) view.findViewById(R.id.idsearch_btn);
        idsearchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (birth.getText().toString().length() < 8) {
                    Toast.makeText(getContext(), "생년월일 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    (new getIDsearchTask()).execute();
                }
            }
        });
        return view;
    }

    //IDsearchTask api통신
    private class getIDsearchTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        String name1 = name.getText().toString();
        String birth1 = birth.getText().toString();
        String phone1 = phone.getText().toString();

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
                if (name1.equals(name) && phone1.equals(phone11)) {
                    Log.d("ddd", "아이디찾기 성공");
                    Toast.makeText(getContext(), "아이디 찾기 성공", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), IdSearchDetailActivity.class);
                    intent.putExtra("uname", name);
                    intent.putExtra("uid", idtext11);
                    startActivity(intent);

                } else {
                    Toast.makeText(getContext(), "아이디 찾기 실패", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Toast.makeText(getContext(), "일치하는 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        String name2 = name.getText().toString();
        String phone2 = phone.getText().toString();

        // Java(Spring), PHP(Laravel), Node.js(Express), Python(Flask)
        @Override
        protected String doInBackground(String... strings) {
            try {
                HttpGet httpget = new HttpGet(api.API_URL+"/movie/IDSearchView/?u_name=" + name2 + "&u_phone=" + phone2);

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
