package com.example.simpumind.trends.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.simpumind.trends.R;
import com.example.simpumind.trends.models.TrendListItem;

import java.util.List;

/**
 * Created by simpumind on 2/18/16.
 */
public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    private List<TrendListItem> items;
    int itemLayout;
    public OnItemClickListener listener;

    public String[] tagList;

    Context c;
   /* public TweetAdapter(List<TrendListItem> items, int itemLayout){
        this.items = items;
        this.itemLayout = itemLayout;
    }*/

    public TweetAdapter(List<TrendListItem> items, String[] tagList, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
        this.tagList = tagList;
    }

    @Override
    public TweetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        c = parent.getContext();
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position), listener);
        holder.lastTweet.setText(tagList[position]);
        /*if (items.size() == 0){
            holder.mRoundedLetterView.setTitleText("A");
        }else{
            holder.mRoundedLetterView.setTitleText(items.get(position).getNames().substring(0, 1).toUpperCase());
        }
        if(position%2 == 0){
            holder. mRoundedLetterView.setBackgroundColor(c.getResources().getColor(R.color.green));
        }else{
            holder.mRoundedLetterView.setBackgroundColor(c.getResources().getColor(R.color.red));
        }*/
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class  ViewHolder extends RecyclerView.ViewHolder{
        public TextView tweetName;
        public TextView twwetVolume;
        public TextView lastTweet;

        public ViewHolder(View itemView){
            super(itemView);
            tweetName = (TextView) itemView.findViewById(R.id.tweet_name);
            twwetVolume = (TextView) itemView.findViewById(R.id.tweet_volume);
            lastTweet = (TextView) itemView.findViewById(R.id.last_tweet);
        }

        public void bind(final TrendListItem item, final OnItemClickListener listener){
            tweetName.setText(item.getNames());
            twwetVolume.setText(String.valueOf(item.getTweetVolume() + " Tweets"));
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(TrendListItem item);
    }
}
