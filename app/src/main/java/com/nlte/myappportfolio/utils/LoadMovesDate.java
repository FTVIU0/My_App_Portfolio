package com.nlte.myappportfolio.utils;

import android.net.Uri;

import com.google.gson.Gson;
import com.nlte.myappportfolio.bean.MoviesSetBean;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 加载电影数据
 * NLTE
 * 2016/6/5 0005 17:54
 */
public class LoadMovesDate {
    private static final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/popular?";
//    private static final String MOVIE_PATH = "/movie";
//    private static final String POPULAR_PATH = "/popular?";
//    private static final String TOP_TATED_PATH = "/top_rated?";
    private static final String APPKEY_PARAM = "api_key";

    private static final String APPKEY = "b4b96c2ecfb28af68295d7a9401012b9";

    private static Uri buildUri;
    private static OkHttpClient sOkHttpClient;

    //获取最受欢迎的电影
    public static MoviesSetBean getPopMovies() throws IOException{
        //构建URL
        buildUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendQueryParameter(APPKEY_PARAM, APPKEY)
                .build();
        System.out.println("++++++++++"+buildUri.toString());

        return getMoviesData(buildUri.toString());
    }

    //// TODO: 2016/6/5 0005  更改URL
    //获取评分最高的电影
    public static MoviesSetBean getTopRatedMovies() throws IOException{
        //构建URL
        buildUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendQueryParameter(APPKEY_PARAM, APPKEY)
                .build();

        return getMoviesData(buildUri.toString());
    }



    //获取访问URL返回的数据, gson解析数据，并将数值赋值给对象并返回
    public static MoviesSetBean getMoviesData(String url) throws IOException{
        OkHttpClient okHttpClient = getInstance();
        final String[] movieJsonStr = new String[1];
        final Gson gson = new Gson();
        //访问URL
        Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                movieJsonStr[0] = response.body().toString();
            }
        });

        System.out.println("----------------------"+movieJsonStr[0]);
        return gson.fromJson(movieJsonStr[0], MoviesSetBean.class);
    }

    //获取Okhttp实例
    private static OkHttpClient getInstance() {
        if (sOkHttpClient == null) {
            synchronized (OkHttpClient.class) {
                sOkHttpClient = new OkHttpClient();
            }
        }
        return sOkHttpClient;
    }
}
