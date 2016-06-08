package com.nlte.myappportfolio;


import android.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.nlte.myappportfolio.adapter.MoviesPosterAdapter;
import com.nlte.myappportfolio.bean.MoviesSetBean;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesSummaryFragment extends Fragment {

    private static final int POP_MOVIES = 1;
    private static final int TOP_RATED_MOVIES = 2;
    private static final int MOVIE_DETAIL = 3;
    private PullToRefreshGridView mMoviesPtrGv;
    private MoviesPosterAdapter mMoviesPosterAdapter;



    private int page = 1;

    public MoviesSummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies_summary, container, false);

        mMoviesPtrGv = (PullToRefreshGridView) view.findViewById(R.id.ptr_gv_movies);
        //mMoviesPtrGv.setRefreshing(true);

        //设置适配器
        mMoviesPosterAdapter = new MoviesPosterAdapter(getActivity(), new ArrayList<MoviesSetBean.ResultsBean>());
        mMoviesPtrGv.setAdapter(mMoviesPosterAdapter);


        //获取Popular Movie
//        new FetchMoviesDataTask().execute(getUri(POP_MOVIES));

        mMoviesPtrGv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                new FetchMoviesDataTask().execute(getUri(POP_MOVIES));
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
            }
        });

        //当滑到底部时自动加载数据
        mMoviesPtrGv.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                page++;
                new FetchMoviesDataTask().execute(getUri(POP_MOVIES));
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //延时加载
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mMoviesPtrGv.setRefreshing(true);
                new FetchMoviesDataTask().execute(getUri(POP_MOVIES));
            }
        }, 500);
    }

    //获取MovieUrl
    private String getUri(int type) {

        final String POP_MOVIES_URL = "https://api.themoviedb.org/3/movie/popular?";
        final String TOP_RATED_MOVIES_URL = "https://api.themoviedb.org/3/movie/top_rated?";
        final String API_KEY = "b4b96c2ecfb28af68295d7a9401012b9";
        final String API_KEY_PARAM = "api_key";
        final String PAGE_NUMBER = "page";

        switch (type) {
            case POP_MOVIES:
                return Uri.parse(POP_MOVIES_URL).buildUpon()
                        .appendQueryParameter(API_KEY_PARAM, API_KEY)
                        .appendQueryParameter(PAGE_NUMBER, String.valueOf(page))
                        .build()
                        .toString();
            case TOP_RATED_MOVIES:
                return Uri.parse(TOP_RATED_MOVIES_URL).buildUpon()
                        .appendQueryParameter(API_KEY_PARAM, API_KEY)
                        .build()
                        .toString();
            case MOVIE_DETAIL:
                return null;
            default:
                return null;
        }
    }

    //访问url抓取数据
    private class FetchMoviesDataTask extends AsyncTask<String, Void, Object> {

        private OkHttpClient mOkHttpClient = new OkHttpClient();
        private Gson mGson;

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
                    return mGson.fromJson(response.body().string(), MoviesSetBean.class);
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
            if (moviesSetBean.getResults() != null) {
                mMoviesPosterAdapter.addItem(moviesSetBean.getResults(), moviesSetBean);
                //通知刷新完成
                mMoviesPtrGv.onRefreshComplete();
            }
        }
    }

}
