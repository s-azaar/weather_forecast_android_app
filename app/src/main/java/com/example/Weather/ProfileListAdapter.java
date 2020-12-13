package com.example.Weather;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ProfileListAdapter extends  RecyclerView.Adapter<ProfileListAdapter.ViewHolder> {

    private ArrayList<ProfileItemData> listdata = new ArrayList<ProfileItemData>();
    private Context context;
    private Activity activity;


    // RecyclerView recyclerView;
    public ProfileListAdapter(ArrayList<ProfileItemData> listdata) {
        this.listdata = listdata;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        activity = (Activity) context;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.profile_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ProfileItemData myListData = listdata.get(position);
        holder.textView.setText(listdata.get(position).getProfileName());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String profile=myListData.getProfileName();
                DataBaseHelper dataBaseHelper =new DataBaseHelper(context);
                dataBaseHelper.setThisProfileDefault(profile);
                Intent intent = new Intent(context, MainActivity.class);
                activity.startActivity(intent);
                activity.finish();


            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.select_profile_name);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.select_layout_linear);
        }
    }
}
