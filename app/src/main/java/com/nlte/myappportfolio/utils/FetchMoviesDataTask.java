package com.nlte.myappportfolio.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.nlte.myappportfolio.adapter.MoviesPosterAdapter;
import com.nlte.myappportfolio.bean.MoviesSetBean;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * NLTE
 * 2016/6/9 0009 16:36
 */
public class FetchMoviesDataTask extends AsyncTask<String, Void, Object> {

    private OkHttpClient mOkHttpClient = new OkHttpClient();
    private Gson mGson;
    private Context mContext;
    private MoviesPosterAdapter mMoviesPosterAdapter;
    private PullToRefreshGridView mMoviesPtrGv;
    private MoviesSetBean mMoviesSetBean;

    public FetchMoviesDataTask(Context context,
                               MoviesPosterAdapter moviesPosterAdapter,
                               PullToRefreshGridView moviesPtrGv, MoviesSetBean moviesSetBean) {
        mContext = context;
        mMoviesPosterAdapter = moviesPosterAdapter;
        mMoviesPtrGv = moviesPtrGv;
        mMoviesSetBean = moviesSetBean;
    }

    @Override
    protected Object doInBackground(String... params) {
        //访问URL
        Request request = new Request.Builder()
                .url(params[0])
                .build();
        try {
            Response response = mOkHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                mGson = new GsonBuilder().create();
                return mGson.fromJson(response.body().string(), mMoviesSetBean.getClass());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object object) {
        super.onPostExecute(object);

        MoviesSetBean moviesSetBean = (MoviesSetBean) object;
        if (moviesSetBean != null && moviesSetBean.getResults() != null) {

            mMoviesPosterAdapter.addItem(moviesSetBean.getResults(), moviesSetBean, 1);
            //通知刷新完成
            mMoviesPtrGv.onRefreshComplete();

        } else {
            ToastShowUtil.show(mContext, "哈哈，网络有问题~~~");

            mMoviesPtrGv.onRefreshComplete();
        }
    }
}
