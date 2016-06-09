package com.nlte.myappportfolio;


import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nlte.myappportfolio.bean.MovieDetailBean;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.nlte.myappportfolio.utils.GetUrl.MOVIE_DETAIL;
import static com.nlte.myappportfolio.utils.GetUrl.getUrl;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesDetailFragment extends Fragment {


    private TextView mTvMovieTitle;
    private TextView mTvMovieGenres;
    private TextView mTvMovieReleaseDate;
    private TextView mTvMovieTagline;
    private TextView mTvMovieOverview;
    private ImageView mIvMoviePoster;
    private RatingBar mRabMovieVoteAverage;
    private TextView mTvMovieVoteAverage;

    private static final String MoviePosterUrl = "http://image.tmdb.org/t/p/w185";
    private ProgressBar mProgressBar;
    private RelativeLayout mRelMovieDetailContent;

    public MoviesDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies_detail, container, false);

        initView(view);

        String url = getUrl(MOVIE_DETAIL, 0, Integer.parseInt((String) getArguments().get("MOVIE_ID")));

        System.out.println(url);

        new FetchMoviesDataTask().execute(url);

        return view;
    }

    //初始化界面
    private void initView(View view) {
        mTvMovieTitle = (TextView) view.findViewById(R.id.tv_movie_title);
        mTvMovieGenres = (TextView) view.findViewById(R.id.tv_movie_genres);
        mTvMovieReleaseDate = (TextView) view.findViewById(R.id.tv_movie_release_date);
        mTvMovieTagline = (TextView) view.findViewById(R.id.tv_movie_tagline);
        mTvMovieOverview = (TextView) view.findViewById(R.id.tv_movie_overview);
        mIvMoviePoster = (ImageView) view.findViewById(R.id.iv_movie_poster);
        mRabMovieVoteAverage = (RatingBar) view.findViewById(R.id.rab_movies_star);
        mTvMovieVoteAverage = (TextView) view.findViewById(R.id.tv_vote_average);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mRelMovieDetailContent = (RelativeLayout) view.findViewById(R.id.rel_movie_detail_content);
    }

    //访问url抓取数据
    private class FetchMoviesDataTask extends AsyncTask<String, Integer, Object> {

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
                    return mGson.fromJson(response.body().string(), MovieDetailBean.class);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object object) {
            super.onPostExecute(object);
            mProgressBar.setVisibility(View.INVISIBLE);

            MovieDetailBean movieDetailBean = (MovieDetailBean) object;

            setView(movieDetailBean);

            mRelMovieDetailContent.setVisibility(View.VISIBLE);
        }

        //设置数据
        private void setView(MovieDetailBean movieDetailBean) {
            mTvMovieTitle.setText(movieDetailBean.getOriginal_title());
            String movieGenres = null;
            for (int i = 0; i < movieDetailBean.getGenres().size(); i++) {
                movieGenres += movieDetailBean.getGenres().get(i).getName();
            }
            mTvMovieGenres.setText(movieGenres);
            mTvMovieReleaseDate.setText(movieDetailBean.getRelease_date());
            mTvMovieTagline.setText(movieDetailBean.getTagline());
            mTvMovieOverview.setText(movieDetailBean.getOverview());
            Picasso.with(getActivity())
                    .load(MoviePosterUrl+movieDetailBean.getPoster_path())
                    .into(mIvMoviePoster);
            mRabMovieVoteAverage.setRating((float) (movieDetailBean.getVote_average() / 2));
            mTvMovieVoteAverage.setText(String.valueOf(Math.round(movieDetailBean.getVote_average() * 10) / 10.0));
        }
    }

}
