package com.nlte.myappportfolio.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nlte.myappportfolio.R;
import com.nlte.myappportfolio.bean.MoviesSetBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Function：电影海报图片资源适配器
 * <p>
 * NLTE
 * 2016/6/5 15:18
 */
public class MoviesPosterAdapter extends BaseAdapter {

    private List<MoviesSetBean.ResultsBean> moviesResultList;

    private Context context;
    private static final String MoviePosterUrl = "http://image.tmdb.org/t/p/w185";

    public MoviesPosterAdapter(Context context, List<MoviesSetBean.ResultsBean> results) {
        this.context = context;
        moviesResultList = results;
    }

    /**
     * 添加多个项
     *
     * @param movies 电影列表
     */

    ArrayList<Integer> pages = new ArrayList<>();
    public void addItem(List<MoviesSetBean.ResultsBean> movies, MoviesSetBean moviesSetBean) {

        if (!pages.contains(moviesSetBean.getPage())){
            pages.add(moviesSetBean.getPage());
            if (null != movies && movies.size() > 0) {
                for (int i = 0; i < movies.size(); i++) {
                    moviesResultList.add(movies.get(i));
                }

                //通知数据应将改变
                notifyDataSetChanged();
            }
        }
        System.out.println(pages);


    }

    @Override
    public int getCount() {
        return moviesResultList.size();
    }

    @Override
    public Object getItem(int position) {
        return moviesResultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.movie_item_summary, null);
            viewHolder = new ViewHolder();

            viewHolder.ivMoviePoster = (ImageView) convertView.findViewById(R.id.iv_movies_poster);
            viewHolder.tvMovieTitle = (TextView) convertView.findViewById(R.id.tv_movies_title);
            viewHolder.rabMovieVoteAve = (RatingBar) convertView.findViewById(R.id.rab_movies_star);
            viewHolder.tvVoteAvg = (TextView) convertView.findViewById(R.id.tv_vote_average);
            viewHolder.tvMovieId = (TextView) convertView.findViewById(R.id.tv_movie_id);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(context)
                .load(MoviePosterUrl + moviesResultList.get(position).getPoster_path())
                .into(viewHolder.ivMoviePoster);
        viewHolder.tvMovieTitle.setText(moviesResultList.get(position).getOriginal_title());
        viewHolder.rabMovieVoteAve.setRating((float) (moviesResultList.get(position).getVote_average() / 2));
        viewHolder.tvVoteAvg.setText(String.valueOf(
                Math.round(moviesResultList.get(position).getVote_average() * 10) / 10.0));
        viewHolder.tvMovieId.setText(String.valueOf(moviesResultList.get(position).getId()));

        return convertView;
    }

    private static class ViewHolder {
        ImageView ivMoviePoster;
        TextView tvMovieTitle;
        RatingBar rabMovieVoteAve;
        TextView tvVoteAvg;
        TextView tvMovieId;
    }

}
