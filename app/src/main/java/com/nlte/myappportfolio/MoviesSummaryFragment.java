package com.nlte.myappportfolio;


import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.nlte.myappportfolio.adapter.MoviesPosterAdapter;
import com.nlte.myappportfolio.bean.MoviesSetBean;
import com.nlte.myappportfolio.utils.ToastShowUtil;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.nlte.myappportfolio.utils.GetUrl.POP_MOVIES;
import static com.nlte.myappportfolio.utils.GetUrl.TOP_RATED_MOVIES;
import static com.nlte.myappportfolio.utils.GetUrl.getUrl;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesSummaryFragment extends Fragment {

   /* private static final int POP_MOVIES = 1;
    private static final int TOP_RATED_MOVIES = 2;
    private static final int MOVIE_DETAIL = 3;*/

    private PullToRefreshGridView mMoviesPtrGv;
    private MoviesPosterAdapter mMoviesPosterAdapter;
    private Spinner mSpinner;


    private int page = 1;
    private int mMoviesStyle;
    private int flag;

    public MoviesSummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies_summary, container, false);

        mMoviesPtrGv = (PullToRefreshGridView) view.findViewById(R.id.ptr_gv_movies);
        mSpinner = (Spinner) view.findViewById(R.id.sp_fetch_type);

        //设置适配器
        mMoviesPosterAdapter = new MoviesPosterAdapter(getActivity(),
                new ArrayList<MoviesSetBean.ResultsBean>());
        mMoviesPtrGv.setAdapter(mMoviesPosterAdapter);

        mMoviesPtrGv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                flag = 0;
                new FetchMoviesDataTask().execute(getUrl(mMoviesStyle, page, 0));
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
                flag = 0;
                new FetchMoviesDataTask().execute(getUrl(mMoviesStyle, page, 0));
            }
        });


        mMoviesPtrGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView movieTitle = (TextView) view.findViewById(R.id.tv_movie_id);

                Bundle bundle = new Bundle();
                bundle.putString("MOVIE_ID", movieTitle.getText().toString());

                MoviesDetailFragment moviesDetailFragment = new MoviesDetailFragment();
                moviesDetailFragment.setArguments(bundle);

                getFragmentManager().beginTransaction()
                        .replace(R.id.movies_content, moviesDetailFragment, "MoviesSummary")
                        .addToBackStack("MoviesSummary")
                        .commit();
            }
        });

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        flag = 1;
                        page = 1;
                        mMoviesStyle = POP_MOVIES;
                        new FetchMoviesDataTask().execute(getUrl(mMoviesStyle, page, 0));
                        break;
                    case 1:
                        flag = 1;
                        page = 1;
                        mMoviesStyle = TOP_RATED_MOVIES;
                        new FetchMoviesDataTask().execute(getUrl(mMoviesStyle, page, 0));
                        break;
                    default:
                        break;
                }
                /*//延时加载, 实现自动刷新效果
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mMoviesPtrGv.setRefreshing(true);
                    }
                }, 500);*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //延时加载, 实现自动刷新效果
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mMoviesPtrGv.setRefreshing(true);
            }
        }, 500);
        System.out.println("onStart");
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
            if (moviesSetBean != null && moviesSetBean.getResults() != null) {

                System.out.println("======  "+flag);
                mMoviesPosterAdapter.addItem(moviesSetBean.getResults(), moviesSetBean, flag);
                //通知刷新完成
                mMoviesPtrGv.onRefreshComplete();

            } else {
                ToastShowUtil.show(getActivity(), "哈哈，网络有问题~~~");

                mMoviesPtrGv.onRefreshComplete();
            }
        }
    }

}
