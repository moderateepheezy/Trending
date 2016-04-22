
package com.example.simpumind.trends.adapter;
/*
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ParseException;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ftinc.kit.adapter.BetterRecyclerAdapter;
import com.kd.dynamic.calendar.generator.ImageGenerator;

import org.simpumind.com.twittertrendsearch.R;
import org.simpumind.com.twittertrendsearch.models.EventsDataList;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

*/

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simpumind.trends.R;
import com.example.simpumind.trends.models.EventsDataList;
import com.example.simpumind.trends.util.OnLoadMoreListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by simpumind on 3/4/16.
 *//*

public class EventListAdapter extends BetterRecyclerAdapter<EventsDataList, EventListAdapter.ViewHolder> {

    public SharedPreferences sPrefs;
    Context c;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        c = parent.getContext();
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        EventsDataList faceBookEventList = getItem(position);
        holder.sourceName.setText(faceBookEventList.getImgName());
        holder.sourceImage.setImageResource(faceBookEventList.getImg());
        holder.eventName.setText(faceBookEventList.getEventName());
        holder.startEvent.setText( getDays(faceBookEventList.getStartTime()));
        holder.eventDescription.setText(faceBookEventList.getDescription() + "...");
        */
/*if (items.size() == 0){
            holder.mRoundedLetterView.setTitleText("A");
        }else{
            holder.mRoundedLetterView.setTitleText(items.get(position).getEventName().substring(0, 1).toUpperCase());
        }
        if(position%2 == 0){
            holder.mRoundedLetterView.setBackgroundColor(c.getResources().getColor(R.color.green));
        }else{
            holder.mRoundedLetterView.setBackgroundColor(c.getResources().getColor(R.color.red));
        }*//*


        getCalender(faceBookEventList);


        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDefaultAppOrPromptUserForSelection();
            }
        });


    }

    private void getCalender(EventsDataList faceBookEventList) {
        Calendar cal = Calendar.getInstance();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        try {
            cal.setTime(sdf.parse(faceBookEventList.getStartTime()));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.event_start)    public TextView startEvent;
        @Bind(R.id.event_name)         public TextView eventName;
        @Bind(R.id.event_description)   public TextView eventDescription;
        @Bind(R.id.rlv_name_view)      public ImageView mDisplayGeneratedImage;
        @Bind(R.id.share)              public Button share;
        @Bind(R.id.sourceName)         public TextView sourceName;
        @Bind(R.id.sourceImg)          public ImageView sourceImage;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public String getDays(String dates){
        SimpleDateFormat dfDate  = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date d = null;
        java.util.Date d1 = null;
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

    public void startDefaultAppOrPromptUserForSelection() {
        String action = Intent.ACTION_SEND;


       sPrefs = PreferenceManager.getDefaultSharedPreferences(c);
        // Get list of handler apps that can send
        Intent intent = new Intent(action);
        intent.setType("image/jpeg");
        PackageManager pm = c.getPackageManager();
        List<ResolveInfo> resInfos = pm.queryIntentActivities(intent, 0);

        boolean useDefaultSendApplication = sPrefs.getBoolean("useDefaultSendApplication", false);
        if (!useDefaultSendApplication) {
            // Referenced http://stackoverflow.com/questions/3920640/how-to-add-icon-in-alert-dialog-before-each-item

            // Class for a singular activity item on the list of apps to send to
            class ListItem {
                public final String name;
                public final Drawable icon;
                public final String context;
                public final String packageClassName;
                public ListItem(String text, Drawable icon, String context, String packageClassName) {
                    this.name = text;
                    this.icon = icon;
                    this.context = context;
                    this.packageClassName = packageClassName;
                }
                @Override
                public String toString() {
                    return name;
                }
            }

            // Form those activities into an array for the list adapter
            final ListItem[] items = new ListItem[resInfos.size()];
            int i = 0;
            for (ResolveInfo resInfo : resInfos) {
                String context = resInfo.activityInfo.packageName;
                String packageClassName = resInfo.activityInfo.name;
                CharSequence label = resInfo.loadLabel(pm);
                Drawable icon = resInfo.loadIcon(pm);
                items[i] = new ListItem(label.toString(), icon, context, packageClassName);
                i++;
            }
            ListAdapter adapter = new ArrayAdapter<ListItem>(
                    c,
                    android.R.layout.select_dialog_item,
                    android.R.id.text1,
                    items){

                public View getView(int position, View convertView, ViewGroup parent) {
                    // User super class to create the View
                    View v = super.getView(position, convertView, parent);
                    TextView tv = (TextView)v.findViewById(android.R.id.text1);

                    // Put the icon drawable on the TextView (support various screen densities)
                    int dpS = (int) (32 * c.getResources().getDisplayMetrics().density +  0.5f);
                    items[position].icon.setBounds(0, 0, dpS, dpS);
                    tv.setCompoundDrawables(items[position].icon, null, null, null);

                    // Add margin between image and name (support various screen densities)
                    int dp5 = (int) (5 * c.getResources().getDisplayMetrics().density  + 0.5f);
                    tv.setCompoundDrawablePadding(dp5);

                    return v;
                }
            };

            // Build the list of send applications
            AlertDialog.Builder builder = new AlertDialog.Builder(c);
            builder.setTitle("Choose your app:");
            builder.setIcon(R.drawable.buzzfeed);
            CheckBox checkbox = new CheckBox(c);
            checkbox.setText(c.getString(R.string.enable_default_send_application));
            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                // Save user preference of whether to use default send application
                @Override
                public void onCheckedChanged(CompoundButton paramCompoundButton,
                                             boolean paramBoolean) {
                    SharedPreferences.Editor editor = sPrefs.edit();
                    editor.putBoolean("useDefaultSendApplication", paramBoolean);
                    editor.commit();
                }
            });
            builder.setView(checkbox);
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface paramDialogInterface) {
                    // do something
                }
            });

            // Set the adapter of items in the list
            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences.Editor editor = sPrefs.edit();
                    editor.putString("defaultSendApplicationName", items[which].name);
                    editor.putString("defaultSendApplicationPackageContext", items[which].context);
                    editor.putString("defaultSendApplicationPackageClassName", items[which].packageClassName);
                    editor.commit();

                    dialog.dismiss();

                    // Start the selected activity sending it the URLs of the resized images
                    Intent intent;
                    intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("image/jpeg");
                    intent.setClassName(items[which].context, items[which].packageClassName);
                    c.startActivity(intent);
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();


        } else { // Start the default send application

            // Get default app name saved in preferences
            String defaultSendApplicationName = sPrefs.getString("defaultSendApplicationName", "<null>");
            String defaultSendApplicationPackageContext = sPrefs.getString("defaultSendApplicationPackageContext", "<null>");
            String defaultSendApplicationPackageClassName = sPrefs.getString("defaultSendApplicationPackageClassName", "<null>");
            if (defaultSendApplicationPackageContext == "<null>" || defaultSendApplicationPackageClassName == "<null>") {
                Toast.makeText(c, "Can't find app: " + defaultSendApplicationName +
                        " (" +  defaultSendApplicationPackageClassName  + ")", Toast.LENGTH_LONG).show();

                // don't have default application details in prefs file so set use default app to null and rerun this method
                SharedPreferences.Editor editor = sPrefs.edit();
                editor.putBoolean("useDefaultSendApplication", false);
                editor.commit();
                startDefaultAppOrPromptUserForSelection();
                return;
            }

            // Check app is still installed
            try {
                ApplicationInfo info = c.getPackageManager().getApplicationInfo(defaultSendApplicationPackageContext, 0);
            } catch (PackageManager.NameNotFoundException e){
                Toast.makeText(c,  "Can't find app: " +  defaultSendApplicationName +
                        " ("  + defaultSendApplicationPackageClassName  + ")", Toast.LENGTH_LONG).show();

                // don't have default application installed so set use default app to null and rerun this method
                SharedPreferences.Editor editor = sPrefs.edit();
                editor.putBoolean("useDefaultSendApplication", false);
                editor.commit();
                startDefaultAppOrPromptUserForSelection();
                return;
            }

            // Start the selected activity
            intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/jpeg");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setClassName(defaultSendApplicationPackageContext, defaultSendApplicationPackageClassName);
            c.startActivity(intent);
           // finish();
            return;
        }
    }

}

*/


public class EventListAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private List<EventsDataList> studentList;
    public SharedPreferences sPrefs;
    public static Context c;

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;

    int lastPosition = -1;



    public EventListAdapter(List<EventsDataList> students, RecyclerView recyclerView) {
        studentList = students;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();


            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager
                                    .findLastVisibleItemPosition();
                            if (!loading
                                    && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                // End has been reached
                                // Do something
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                                loading = true;
                            }
                        }
                    });
        }

    }

    @Override
    public int getItemViewType(int position) {
        return studentList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        RecyclerView.ViewHolder vh;
        c = parent.getContext();
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.event_list_item, parent, false);

            vh = new ViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progress_item, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {

            Animation animation = AnimationUtils.loadAnimation(c,
                    (position > lastPosition) ? R.anim.up_from_bottom
                            : R.anim.down_from_top);
            holder.itemView.startAnimation(animation);
            lastPosition = position;

            EventsDataList evendata= studentList.get(position);

            ((ViewHolder) holder).sourceName.setText(evendata.getImgName());
            ((ViewHolder) holder).sourceImage.setImageResource(evendata.getImg());
            ((ViewHolder) holder).eventName.setText(evendata.getEventName());
            ((ViewHolder) holder).startEvent.setText( getDays(evendata.getStartTime()));
            ((ViewHolder) holder).eventDescription.setText(evendata.getDescription() + "...");
            ((ViewHolder) holder).share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startDefaultAppOrPromptUserForSelection();
                }
            });
            getCalender(evendata);

        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        return  studentList.size();
    }



    //
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.event_start)    public TextView startEvent;
        @Bind(R.id.event_name)         public TextView eventName;
        @Bind(R.id.event_description)   public TextView eventDescription;
        @Bind(R.id.rlv_name_view)      public ImageView mDisplayGeneratedImage;
        @Bind(R.id.share)              public Button share;
        @Bind(R.id.sourceName)         public TextView sourceName;
        @Bind(R.id.sourceImg)          public ImageView sourceImage;

        public EventsDataList eventsDataList;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Intent viewer = new Intent(v.getContext(), ViewerActivity.class);
                    viewer.putExtra(ViewerActivity.EXTRA_OS, eventsDataList);
                    v.getContext().startActivity(viewer);*/
                }
            });
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public String getDays(String dates){
        SimpleDateFormat dfDate  = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date d = null;
        java.util.Date d1 = null;
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

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }

    public void startDefaultAppOrPromptUserForSelection() {
        String action = Intent.ACTION_SEND;


        sPrefs = PreferenceManager.getDefaultSharedPreferences(c);
        // Get list of handler apps that can send
        Intent intent = new Intent(action);
        intent.setType("image/jpeg");
        PackageManager pm = c.getPackageManager();
        List<ResolveInfo> resInfos = pm.queryIntentActivities(intent, 0);

        boolean useDefaultSendApplication = sPrefs.getBoolean("useDefaultSendApplication", false);
        if (!useDefaultSendApplication) {
            // Referenced http://stackoverflow.com/questions/3920640/how-to-add-icon-in-alert-dialog-before-each-item

            // Class for a singular activity item on the list of apps to send to
            class ListItem {
                public final String name;
                public final Drawable icon;
                public final String context;
                public final String packageClassName;
                public ListItem(String text, Drawable icon, String context, String packageClassName) {
                    this.name = text;
                    this.icon = icon;
                    this.context = context;
                    this.packageClassName = packageClassName;
                }
                @Override
                public String toString() {
                    return name;
                }
            }

            // Form those activities into an array for the list adapter
            final ListItem[] items = new ListItem[resInfos.size()];
            int i = 0;
            for (ResolveInfo resInfo : resInfos) {
                String context = resInfo.activityInfo.packageName;
                String packageClassName = resInfo.activityInfo.name;
                CharSequence label = resInfo.loadLabel(pm);
                Drawable icon = resInfo.loadIcon(pm);
                items[i] = new ListItem(label.toString(), icon, context, packageClassName);
                i++;
            }
            ListAdapter adapter = new ArrayAdapter<ListItem>(
                    c,
                    android.R.layout.select_dialog_item,
                    android.R.id.text1,
                    items){

                public View getView(int position, View convertView, ViewGroup parent) {
                    // User super class to create the View
                    View v = super.getView(position, convertView, parent);
                    TextView tv = (TextView)v.findViewById(android.R.id.text1);

                    // Put the icon drawable on the TextView (support various screen densities)
                    int dpS = (int) (32 * c.getResources().getDisplayMetrics().density +  0.5f);
                    items[position].icon.setBounds(0, 0, dpS, dpS);
                    tv.setCompoundDrawables(items[position].icon, null, null, null);

                    // Add margin between image and name (support various screen densities)
                    int dp5 = (int) (5 * c.getResources().getDisplayMetrics().density  + 0.5f);
                    tv.setCompoundDrawablePadding(dp5);

                    return v;
                }
            };

            // Build the list of send applications
            AlertDialog.Builder builder = new AlertDialog.Builder(c);
            builder.setTitle("Choose your app:");
            builder.setIcon(R.drawable.buzzfeed);
            CheckBox checkbox = new CheckBox(c);
            checkbox.setText(c.getString(R.string.enable_default_send_application));
            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                // Save user preference of whether to use default send application
                @Override
                public void onCheckedChanged(CompoundButton paramCompoundButton,
                                             boolean paramBoolean) {
                    SharedPreferences.Editor editor = sPrefs.edit();
                    editor.putBoolean("useDefaultSendApplication", paramBoolean);
                    editor.commit();
                }
            });
            builder.setView(checkbox);
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface paramDialogInterface) {
                    // do something
                }
            });

            // Set the adapter of items in the list
            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences.Editor editor = sPrefs.edit();
                    editor.putString("defaultSendApplicationName", items[which].name);
                    editor.putString("defaultSendApplicationPackageContext", items[which].context);
                    editor.putString("defaultSendApplicationPackageClassName", items[which].packageClassName);
                    editor.commit();

                    dialog.dismiss();

                    // Start the selected activity sending it the URLs of the resized images
                    Intent intent;
                    intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("image/jpeg");
                    intent.setClassName(items[which].context, items[which].packageClassName);
                    c.startActivity(intent);
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();


        } else { // Start the default send application

            // Get default app name saved in preferences
            String defaultSendApplicationName = sPrefs.getString("defaultSendApplicationName", "<null>");
            String defaultSendApplicationPackageContext = sPrefs.getString("defaultSendApplicationPackageContext", "<null>");
            String defaultSendApplicationPackageClassName = sPrefs.getString("defaultSendApplicationPackageClassName", "<null>");
            if (defaultSendApplicationPackageContext == "<null>" || defaultSendApplicationPackageClassName == "<null>") {
                Toast.makeText(c, "Can't find app: " + defaultSendApplicationName +
                        " (" +  defaultSendApplicationPackageClassName  + ")", Toast.LENGTH_LONG).show();

                // don't have default application details in prefs file so set use default app to null and rerun this method
                SharedPreferences.Editor editor = sPrefs.edit();
                editor.putBoolean("useDefaultSendApplication", false);
                editor.commit();
                startDefaultAppOrPromptUserForSelection();
                return;
            }

            // Check app is still installed
            try {
                ApplicationInfo info = c.getPackageManager().getApplicationInfo(defaultSendApplicationPackageContext, 0);
            } catch (PackageManager.NameNotFoundException e){
                Toast.makeText(c,  "Can't find app: " +  defaultSendApplicationName +
                        " ("  + defaultSendApplicationPackageClassName  + ")", Toast.LENGTH_LONG).show();

                // don't have default application installed so set use default app to null and rerun this method
                SharedPreferences.Editor editor = sPrefs.edit();
                editor.putBoolean("useDefaultSendApplication", false);
                editor.commit();
                startDefaultAppOrPromptUserForSelection();
                return;
            }

            // Start the selected activity
            intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/jpeg");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setClassName(defaultSendApplicationPackageContext, defaultSendApplicationPackageClassName);
            c.startActivity(intent);
            // finish();
            return;
        }

    }

    private void getCalender(EventsDataList faceBookEventList) {
        Calendar cal = Calendar.getInstance();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        try {
            cal.setTime(sdf.parse(faceBookEventList.getStartTime()));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
    }

}