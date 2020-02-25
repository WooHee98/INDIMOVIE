package com.example.mainindimovie_ex03;

import android.app.Application;
import android.util.Log;

import com.example.mainindimovie_ex03.aApi.Api;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.mainindimovie_ex03.aApi.Api.API_URL;

public class ApplicationController extends Application {
    public final static String TAG = "indimovie";
    private static ApplicationController instance;

    public static ApplicationController getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationController.instance = this;
    }

    private Api ApiService;

    public Api getApi() {
        return ApiService;
    }


//    private String baseUrl;
////
////    public void buildNetworkService(String ip, int port) {
////        synchronized (ApplicationController.class) {
////            if (ApiService == null) {
////                baseUrl = String.format("http://%s:%d/", ip, port);
////                Log.i(TAG, baseUrl);
////
////                Retrofit retrofit = new Retrofit.Builder()
////                        .baseUrl(baseUrl)
////                        .addConverterFactory(GsonConverterFactory.create())
////                        .build();
////
////                ApiService = retrofit.create(Api.class);
////            }
////        }
////    }
////}
//
//    public void buildNetworkService(String ip){
//        synchronized (ApplicationController.class){
//            if (ApiService == null){
//                baseUrl = String.format("http://%s/", ip);
//                Log.i(TAG, baseUrl);
//
//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl(baseUrl)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//
//                ApiService = retrofit.create(Api.class);
//            }
//        }
//    }
//}

    private String baseUrl;

    public void buildNetworkService1( ){

        synchronized (ApplicationController.class){

            if (ApiService == null) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                httpClient.interceptors().add(logging);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Api.API_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiService = retrofit.create(Api.class);
            }
        }
    }

}