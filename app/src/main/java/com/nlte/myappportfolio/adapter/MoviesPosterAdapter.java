package com.nlte.myappportfolio.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nlte.myappportfolio.R;
import com.nlte.myappportfolio.bean.MoviesSetBean;
import com.squareup.picasso.Picasso;

/**
 * Function：电影海报图片资源适配器
 *
 *  NLTE
 *  2016/6/5 15:18
 */
public class MoviesPosterAdapter extends BaseAdapter {

    private MoviesSetBean moviesSetBean;

    private Context context;

    public MoviesPosterAdapter(Context context, MoviesSetBean moviesSetBean) {
        this.context = context;
        this.moviesSetBean = moviesSetBean;
    }

    @Override
    public int getCount() {
        return moviesSetBean.getResults().size();
    }

    @Override
    public Object getItem(int position) {
        return moviesSetBean.getResults().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = View.inflate(context, R.layout.movie_item_summary, null);
            viewHolder = new ViewHolder();

            viewHolder.ibMoviePoster = (ImageButton) convertView.findViewById(R.id.ib_movies_poster);
            viewHolder.tvMovieTitle = (TextView) convertView.findViewById(R.id.tv_movies_title);
            viewHolder.rabMovieVoteAve = (RatingBar) convertView.findViewById(R.id.rab_movies_star);
            viewHolder.tvVoteAvg = (TextView) convertView.findViewById(R.id.tv_vote_average);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w500" + moviesSetBean
                        .getResults().get(position)
                        .getPoster_path())
                .into(viewHolder.ibMoviePoster);
        viewHolder.tvMovieTitle.setText(moviesSetBean.getResults().get(position).getOriginal_title());
        viewHolder.rabMovieVoteAve.setRating((float) (moviesSetBean.getResults().get(position).getVote_average() / 2));
        viewHolder.tvVoteAvg.setText(String.valueOf(Math.round(moviesSetBean.getResults().get(position).getVote_average()*10)/10.0));

        return convertView;
    }

    private static class ViewHolder{
        ImageButton ibMoviePoster;
        TextView tvMovieTitle;
        RatingBar rabMovieVoteAve;
        TextView tvVoteAvg;
    }

}
