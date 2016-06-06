package com.nlte.myappportfolio;


import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nlte.myappportfolio.adapter.MoviesPosterAdapter;
import com.nlte.myappportfolio.bean.MoviesSetBean;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesSummaryFragment extends Fragment {

    private GridView mMoviesGv;



    public MoviesSummaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies_summary, container, false);

        mMoviesGv = (GridView) view.findViewById(R.id.gv_movies_summary);

        new FetchMoviesDataTask().execute("https://api.themoviedb.org/3/movie/popular?api_key=b4b96c2ecfb28af68295d7a9401012b9");

        return view;
    }

    private class FetchMoviesDataTask extends AsyncTask<String, Void, Object>{

        private OkHttpClient mOkHttpClient = new OkHttpClient();
        @Override
        protected Object doInBackground(String... params) {
            //访问URL
            Request request = new Request.Builder()
                    .url(params[0])
                    .build();
            try {
                Response response = mOkHttpClient.newCall(request).execute();
                if (response.isSuccessful()){
                    Gson gson = new GsonBuilder().create();
                    return gson.fromJson(response.body().string(), MoviesSetBean.class);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object obj) {
            super.onPostExecute(obj);
            MoviesSetBean moviesSetBean = (MoviesSetBean) obj;
            if (moviesSetBean != null) {
                mMoviesGv.setAdapter(new MoviesPosterAdapter(getActivity(), moviesSetBean));
            }
        }
    }

}
