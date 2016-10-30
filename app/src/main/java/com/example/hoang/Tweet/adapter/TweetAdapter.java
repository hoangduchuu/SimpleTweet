package com.example.hoang.Tweet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.hoang.Tweet.R;
import com.example.hoang.Tweet.model.Tweet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hoang on 10/28/16.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {
    private static final String TAG = "TweetAdapter";
    private List<Tweet> tweets;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public TweetAdapter(Context context, List<Tweet> tweetList) {
        mContext = context;
        tweets = tweetList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tweet tweet = tweets.get(position);

        holder.tvUser.setText(tweet.getUser().getName());
        holder.tvBody.setText(tweet.getBody().toString());
        holder.screeName.setText(" @"+tweet.getUser().getScreeName());
        holder.tvTime.setText(tweet.getCreatedAtTimeSpan().toString());
        Glide.with(mContext)
                .load(tweet.getUser().getProfileImgUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivprofile);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvUser) TextView tvUser;
        @BindView(R.id.screenName) TextView screeName;
        @BindView(R.id.tvBody) TextView tvBody;
        @BindView(R.id.tvTime) TextView tvTime;
        @BindView(R.id.ivProfileImages) ImageView ivprofile;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}