package com.example.mainindimovie_ex03.fragments;

/*http://youknow-yoonho.blogspot.com/2016/02/android-fragment-dialog.html*/

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.StaticValues;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.activitiesScenario.ScenarioDetail_Go_MoneyActivity;
import com.example.mainindimovie_ex03.activitys.LoginActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScenarioDetailSinarioFragment extends Fragment {
    Context context;
    Button goalert, gosponsor;
    View view;
    AlertDialog customDialog;
    CheckBox check1, check2;
    TextView title, writer, jang, pretotal, day, count, content, total, countper, year, month, day10,introduce, purpose , core, plan, email, phone;
    String daysplit0 = "";

    private Api api;
    String s_id = "";
    String pretotal1 = "";

    public ScenarioDetailSinarioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_sinario_detail_sinario, container, false);
        goalert = view.findViewById(R.id.sinario_detail_sponsored_btn);
        title = view.findViewById(R.id.scenario_datail_title); //제목
        writer = view.findViewById(R.id.scenario_datail_writer); //작성자
        jang = view.findViewById(R.id.scenario_datail_jang); //장르
        pretotal = view.findViewById(R.id.present_sinario_price); //현재 모인 금액
        day = view.findViewById(R.id.present_sinario_time); //시간
        content = view.findViewById(R.id.scenario_detail_content);    //내용
        total = view.findViewById(R.id.total_sinario_price);   //작가가 원하는 금액
        countper = view.findViewById(R.id.present_sinario_person);//후원자 수
//        year = view.findViewById(R.id.total_sinario_year);
//        month = view.findViewById(R.id.total_sinario_month);
//        day10 = view.findViewById(R.id.total_sinario_day2);
        introduce =  view.findViewById(R.id.scenario_detail_introduce);
        purpose =  view.findViewById(R.id.scenario_detail_purpose);
        core =  view.findViewById(R.id.scenario_detail_core);
        plan =  view.findViewById(R.id.scenario_detail_plan);    //목적
        email =view.findViewById(R.id.scenario_detail_email);
        phone = view.findViewById(R.id.scenario_detail_phone);

        Bundle bundle = getArguments();
        if (bundle != null) {

        }
        (new getscenaDetailTask()).execute(bundle.getString("s_id"));
        (new CountscenaDetailTask()).execute(bundle.getString("s_id"));
        s_id = bundle.getString("s_id");
        Log.d("S_id", s_id);

        goalert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StaticValues.u_id.length() == 0) {
                    Toast.makeText(getContext(), "로그인을 해주세요", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    LayoutInflater inflater = (LayoutInflater) getActivity().getLayoutInflater();
                    View view1 = inflater.inflate(R.layout.alert_dialog_gosponsored, null);
                    builder.setView(view1);
                    customDialog = builder.create();
                    customDialog.show();


                    //체크박스 눌러야 이동0000000000000000000000000000000000000000000000000
                    check1 = view1.findViewById(R.id.firstcheckbtn);
                    check2 = view1.findViewById(R.id.secondcheckbtn);
                    gosponsor = view1.findViewById(R.id.alert_scenario_go_sponsor_btn);
                    gosponsor.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (check1.isChecked() && check2.isChecked()) {
                                Intent intent = new Intent(getContext(), ScenarioDetail_Go_MoneyActivity.class);
                                intent.putExtra("s_id", s_id);
                                intent.putExtra("title", title.getText());
                                intent.putExtra("writer", writer.getText());
                                intent.putExtra("pretotal1", pretotal1);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getContext(), "모두 체크해주세요", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }


        });
        return view;
    }

    //시나리오 디테일
    private class getscenaDetailTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                //array : movie 전체
                JSONArray array = new JSONArray(s);
                title.setText((String) array.get(2));
                writer.setText((String) array.get(9));
                jang.setText((String) array.get(1));
                content.setText((String) array.get(3));
                day.setText((String) array.get(6));
                introduce.setText((String) array.get(10));
                purpose.setText((String) array.get(11));
                core.setText((String) array.get(12));
                plan.setText((String) array.get(13));
                email.setText((String) array.get(14));
                phone.setText((String) array.get(15));
                daysplit0 = (String) ((String) array.get(6)).trim();
//                String[] array22 = daysplit0.split("-");
//                year.setText(array22[0]);
//
//
//                if ((array22[1].trim()).equals("01") || (array22[1].trim()).equals("03") || (array22[1].trim()).equals("05") || (array22[1].trim()).equals("07") || (array22[1].trim()).equals("08") || (array22[1].trim()).equals("10")) {
//
//                    if ((array22[2].trim()).equals("28")) {
//                        int day2 = Integer.parseInt(array22[1].trim()) + 1;
//                        String day3 = Integer.toString(day2);
//                        month.setText(day3);
//                        day10.setText("01");
//                    } else {
//                        month.setText(array22[1].trim());
//                        int day4 = Integer.parseInt(array22[2].trim()) + 4;
//                        String day5 = Integer.toString(day4);
//                        day10.setText(day5);
//                    }
//                    if ((array22[2].trim()).equals("29")) {
//                        int day2 = Integer.parseInt(array22[1].trim()) + 1;
//                        String day3 = Integer.toString(day2);
//                        month.setText(day3);
//                        day10.setText("02");
//                    } else {
//                        month.setText(array22[1].trim());
//                        int day4 = Integer.parseInt(array22[2].trim()) + 4;
//                        String day5 = Integer.toString(day4);
//                        day10.setText(day5);
//                    }
//                    if ((array22[2].trim()).equals("30")) {
//                        int day2 = Integer.parseInt(array22[1].trim()) + 1;
//                        String day3 = Integer.toString(day2).trim();
//                        month.setText(day3);
//                        day10.setText("03");
//                    } else {
//                        month.setText(array22[1].trim());
//                        int day4 = Integer.parseInt(array22[2].trim()) + 4;
//                        String day5 = Integer.toString(day4).trim();
//                        day10.setText(day5);
//                    }
//                    if ((array22[2].trim()).equals("31")) {
//                        int day2 = Integer.parseInt(array22[1].trim()) + 1;
//                        String day3 = Integer.toString(day2).trim();
//                        month.setText(day3);
//                        day10.setText("04");
//                    } else {
//                        month.setText(array22[1].trim());
//                        int day4 = Integer.parseInt(array22[2].trim()) + 4;
//                        String day5 = Integer.toString(day4).trim();
//                        day10.setText(day5);
//                    }
//                }
//
//                if ((array22[1].trim()).equals("02") || (array22[1].trim()).equals("04") || (array22[1].trim()).equals("06") || (array22[1].trim()).equals("09") || (array22[1].trim()).equals("11")) {
//                    if ((array22[2].trim()).equals("27")) {
//                        int day2 = Integer.parseInt(array22[1].trim()) + 1;
//                        String day3 = Integer.toString(day2).trim();
//                        month.setText(day3);
//                        day10.setText("01");
//                    } else {
//                        month.setText(array22[1].trim());
//                        int day4 = Integer.parseInt(array22[2].trim()) + 4;
//                        String day5 = Integer.toString(day4).trim();
//                        day10.setText(day5);
//                    }
//                    if ((array22[2].trim()).equals("28")) {
//                        int day2 = Integer.parseInt(array22[1].trim()) + 1;
//                        String day3 = Integer.toString(day2).trim();
//                        month.setText(day3);
//                        day10.setText("02");
//                    } else {
//                        month.setText(array22[1].trim());
//                        int day4 = Integer.parseInt(array22[2].trim()) + 4;
//                        String day5 = Integer.toString(day4).trim();
//                        day10.setText(day5);
//                    }
//                    if ((array22[2].trim()).equals("29")) {
//                        int day2 = Integer.parseInt(array22[1].trim()) + 1;
//                        String day3 = Integer.toString(day2).trim();
//                        month.setText(day3);
//                        day10.setText("03");
//                    } else {
//                        month.setText(array22[1].trim());
//                        int day4 = Integer.parseInt(array22[2].trim()) + 4;
//                        String day5 = Integer.toString(day4).trim();
//                        day10.setText(day5);
//                    }
//                    if ((array22[2].trim()).equals("30")) {
//                        int day2 = Integer.parseInt(array22[1].trim()) + 1;
//                        String day3 = Integer.toString(day2).trim();
//                        month.setText(day3);
//                        day10.setText("04");
//                    } else {
//                        month.setText(array22[1].trim());
//                        int day4 = Integer.parseInt(array22[2].trim()) + 4;
//                        String day5 = Integer.toString(day4).trim();
//                        day10.setText(day5);
//                    }
//                }
//
//                if((array22[1].trim()).equals("12")){
//
//                    if ((array22[2].trim()).equals("28")) {
//                        month.setText("01");
//                        day10.setText("01");
//                    } else {
//                        int day4 = Integer.parseInt(array22[2].trim()) + 4;
//                        String day5 = Integer.toString(day4).trim();
//                        day10.setText(day5);
//                    }
//                    if ((array22[2].trim()).equals("29")) {
//                        Log.d("dddfs", array22[2].trim());
//                        String a= "02" ;
//                        day10.setText(a.trim());
//                        month.setText("01");
//
//                    } else {
//                        int day4 = Integer.parseInt(array22[2].trim()) + 4;
//                        String day5 = Integer.toString(day4).trim();
//                        day10.setText(day5);
//                    }
//
//                    if ((array22[2].trim()).trim().equals("30")) {
//                        month.setText("01");
//                        day10.setText("03");
//                    } else {
//                        int day4 = Integer.parseInt(array22[2].trim()) + 4;
//                        String day5 = Integer.toString(day4).trim();
//                        day10.setText(day5);
//                    }
//                    if ((array22[2].trim()).equals("31")) {
//                        month.setText("01");
//                        day10.setText("04");
//                    } else {
//                        int day4 = Integer.parseInt(array22[2].trim()) + 4;
//                        String day5 = Integer.toString(day4).trim();
//                        day10.setText(day5);
//                    }
//                }


                total.setText((Integer) array.get(5) + "");
                pretotal.setText((Integer) array.get(7) + "");
                pretotal1 = (Integer) array.get(7) + "";

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                HttpGet httpPost = new HttpGet(api.API_URL + "/movie/getScenarioDetailInfo/?s_id=" + strings[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httpPost);

                // StatusLine stat = response.getStatusLine();

                //404 : page not found error
                //500 : internal server error
                //200 : 정상
                int res = response.getStatusLine().getStatusCode();

                if (res >= 400) {

                } else {
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


    //시나리오 후원자 수 가져오기
    private class CountscenaDetailTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                //array : movie 전체
                JSONArray array = new JSONArray(s);
                countper.setText((Integer) array.get(0) + "");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                HttpGet httpPost = new HttpGet(api.API_URL + "/movie/CountScenarioInfo/?s_id=" + strings[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httpPost);

                // StatusLine stat = response.getStatusLine();

                //404 : page not found error
                //500 : internal server error
                //200 : 정상
                int res = response.getStatusLine().getStatusCode();

                if (res >= 400) {

                } else {
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

