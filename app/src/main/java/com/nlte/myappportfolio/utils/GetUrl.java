package com.nlte.myappportfolio.utils;

import android.net.Uri;

/**
 * NLTE
 * 2016/6/9 0009 16:06
 */
public class GetUrl {

    private static final String POP_MOVIES_URL = "https://api.themoviedb.org/3/movie/popular?";
    private static final String TOP_RATED_MOVIES_URL = "https://api.themoviedb.org/3/movie/top_rated?";
    private static final String MOVIES_DETAIL_URL = "https://api.themoviedb.org/3/movie/";

    private static final String API_KEY = "b4b96c2ecfb28af68295d7a9401012b9";
    private static final String API_KEY_PARAM = "api_key";
    private static final String PAGE_NUMBER = "page";

    public static final int POP_MOVIES = 1;//最受欢迎电影
    public static final int TOP_RATED_MOVIES = 2;//评分最高的电影
    public static final int MOVIE_DETAIL = 3;//电影详情数据

    //获取MovieUrl
    public static String getUrl(int type, int page, int movieId) {

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
                        .appendQueryParameter(PAGE_NUMBER, String.valueOf(page))
                        .build()
                        .toString();
            case MOVIE_DETAIL:
                return Uri.parse(MOVIES_DETAIL_URL+movieId+"?").buildUpon()
                        .appendQueryParameter(API_KEY_PARAM, API_KEY)
                        .build()
                        .toString();
            default:
                return null;
        }
    }
}
