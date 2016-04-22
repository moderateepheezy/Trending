package com.example.simpumind.trends.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simpumind.trends.R;
import com.example.simpumind.trends.models.NewsDataList;
import com.ftinc.kit.adapter.BetterRecyclerAdapter;
import com.thefinestartist.finestwebview.FinestWebView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;




public class NewsListAdapter extends BetterRecyclerAdapter<NewsDataList, NewsListAdapter.ViewHolder> {


    Context c;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        c = parent.getContext();
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        final NewsDataList faceBookEventList = getItem(position);
        holder.eventName.setText(faceBookEventList.getNewsTitle());
        holder.startEvent.setText(faceBookEventList.getStartTime());
        holder.news_link.setText(faceBookEventList.getLinks());
        holder.news_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FinestWebView.Builder(c)
                        .theme(R.style.FinestWebViewTheme)
                        .titleDefault("What's Trending")
                        .showUrl(false)
                        .statusBarColorRes(R.color.bluePrimaryDark)
                        .toolbarColorRes(R.color.bluePrimary)
                        .titleColorRes(R.color.finestWhite)
                        .urlColorRes(R.color.bluePrimaryLight)
                        .iconDefaultColorRes(R.color.finestWhite)
                        .progressBarColorRes(R.color.PrimaryDarkColor)
                        .stringResCopiedToClipboard(R.string.copied_to_clipboard)
                        .stringResCopiedToClipboard(R.string.copied_to_clipboard)
                        .stringResCopiedToClipboard(R.string.copied_to_clipboard)
                        .showSwipeRefreshLayout(true)
                        .swipeRefreshColorRes(R.color.bluePrimaryDark)
                        .menuSelector(R.drawable.selector_light_theme)
                        .menuTextGravity(Gravity.CENTER)
                        .menuTextPaddingRightRes(R.dimen.defaultMenuTextPaddingLeft)
                        .dividerHeight(0)
                        .gradientDivider(false)
                        .setCustomAnimations(R.anim.slide_up, R.anim.hold, R.anim.hold, R.anim.slide_down)
                        .show(faceBookEventList.getLinks());
            }
        });
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.event_starts)    public TextView startEvent;
        @Bind(R.id.event_names)         public TextView eventName;
        @Bind(R.id.news_link)   public TextView news_link;
        @Bind(R.id.rlv_name_views)      public ImageView mDisplayGeneratedImage;
        @Bind(R.id.shares)              public Button share;
        @Bind(R.id.sourceNames)         public TextView sourceName;
        @Bind(R.id.sourceImgs)          public ImageView sourceImage;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public String getDays(String dates){
        SimpleDateFormat dfDate  = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        Date d1 = null;
        Calendar cal = Calendar.getInstance();
        try {
            d = dfDate.parse(dates);
            d1 = dfDate.parse(dfDate.format(cal.getTime()));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        int diffInDays = (int) ((d.getTime() - d1.getTime())/ (1000 * 60 * 60 * 24));

        return diffInDays + "d";
    }

}

