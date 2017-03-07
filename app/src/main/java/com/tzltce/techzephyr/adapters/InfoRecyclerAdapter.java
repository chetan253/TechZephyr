package com.tzltce.techzephyr.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tzltce.techzephyr.R;
import com.tzltce.techzephyr.model.InfoRecycler;

import java.util.ArrayList;

/**
 * Created by Chetan on 22-12-2015.
 */
public class InfoRecyclerAdapter extends RecyclerView.Adapter<InfoRecyclerAdapter.InfoViewHolder>{

    public ArrayList<InfoRecycler> infoList;

    public InfoRecyclerAdapter(ArrayList<InfoRecycler> infoList){
        this.infoList = infoList;
    }

    @Override
    public InfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.info_card, parent, false);
        return new InfoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(InfoViewHolder holder, int position) {
        InfoRecycler info = infoList.get(position);
        holder.tvheader.setText(info.getHead());
        holder.tvdesc.setText(info.getDesc());
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    public static class InfoViewHolder extends RecyclerView.ViewHolder{
        protected TextView tvheader;
        protected TextView tvdesc;

        public InfoViewHolder(View v) {
            super(v);
            tvheader = (TextView) v.findViewById(R.id.info_header);
            tvdesc = (TextView) v.findViewById(R.id.info_desc);
        }
    }

}
