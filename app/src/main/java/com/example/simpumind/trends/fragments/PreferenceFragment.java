package com.example.simpumind.trends.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simpumind.trends.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SimpuMind on 4/21/16.
 */
public class PreferenceFragment extends android.support.v4.app.DialogFragment{

   public static PreferenceFragment newInstance() {
        return new PreferenceFragment();
    }

    private GridLayoutManager lLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.preference_view, null, false);
        List<String> rowListItem = getAllItemList();
        lLayout = new GridLayoutManager(getActivity(), 2);

        RecyclerView rView = (RecyclerView)view.findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(getActivity(), rowListItem);
        rView.setAdapter(rcAdapter);
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        return dialog;
    }

    private List<String> getAllItemList(){

        List<String> allItems = new ArrayList<>();
        allItems.add("Linda");
        allItems.add("Bella");
        allItems.add("Soccer");
        allItems.add("Kakaki");

        return allItems;
    }



    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

        private List<String> itemList;
        private Context context;

        public RecyclerViewAdapter(Context context, List<String> itemList) {
            this.itemList = itemList;
            this.context = context;
        }

        @Override
        public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.preference_item, null);
            RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
            return rcv;
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolders holder, int position) {
            holder.preference.setText(itemList.get(position));
        }

        @Override
        public int getItemCount() {
            return this.itemList.size();
        }
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView preference;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            preference = (TextView)itemView.findViewById(R.id.lf);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }
}
