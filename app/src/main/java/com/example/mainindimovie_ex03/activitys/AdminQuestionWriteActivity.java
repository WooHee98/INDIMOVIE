package com.example.mainindimovie_ex03.activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainindimovie_ex03.ApplicationController;
import com.example.mainindimovie_ex03.Do.AdminQuestionDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.StaticValues;
import com.example.mainindimovie_ex03.aApi.Api;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminQuestionWriteActivity extends AppCompatActivity {
    private ImageButton write_delete_btn;
    private TextView writer, text1;
    private EditText write_title_edit, write_content_edit;
    private Button write_enrol_btn;
    String title = "";
    String content = "";
    Handler timer = new Handler();
    private Api api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_question_write);
        write_delete_btn = findViewById(R.id.admin_question_write_delete_btn);
        write_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminQuestionWriteActivity.super.onBackPressed();
            }
        });

        //글쓴이
        writer = findViewById(R.id.admin_question_writer);
        writer.setText(StaticValues.u_name);

        //content 글자수 제한
        setcontent();

        //제목
        write_title_edit = findViewById(R.id.admin_question_write_title_edit);

        write_enrol_btn = findViewById(R.id.admin_question_write_enrol_btn);
        write_enrol_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //제목
                if (write_title_edit.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "제목을 입력해주요", Toast.LENGTH_SHORT).show();
                    write_title_edit.requestFocus();
                    return;
                }
                //내용
                if (write_content_edit.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "내용을 입력해주요", Toast.LENGTH_SHORT).show();
                    write_content_edit.requestFocus();
                    return;
                }

                title = write_title_edit.getText().toString();
                content = write_content_edit.getText().toString();
                (new insertAdminTask()).execute();
                timer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "등록되었습니다.", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }, 1000);



            }
        });



    }

    //글자수 제한
    private void setcontent() {
        final int MAX_LENGTH = 2000;
        write_content_edit = findViewById(R.id.admin_question_write_content_edit);

        write_content_edit.addTextChangedListener(new TextWatcher() {
            String strCur;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strCur = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                InputFilter[] inputFilters = new InputFilter[1];
                inputFilters[0] = new InputFilter.LengthFilter(MAX_LENGTH);
                write_content_edit.setFilters(inputFilters);

                if (s.length() > 2000) {
                    write_content_edit.setText(strCur);
                    write_content_edit.setSelection(start);
                } else {
                    text1 = findViewById(R.id.text1);
                    text1.setText(String.valueOf(s.length()));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    //어드민 insert api
    private class insertAdminTask extends AsyncTask<String, Double, String> {
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
                HttpGet httpPost = new HttpGet(api.API_URL+"/movie/InsertAdminQView?aq_title=" + title + "&aq_content=" + content + "&u_id_id=" + StaticValues.u_id);
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
