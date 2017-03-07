package com.tzltce.techzephyr.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tzltce.techzephyr.DetailWorkshopActivity;
import com.tzltce.techzephyr.R;
import com.tzltce.techzephyr.model.Detail;

import java.util.ArrayList;

/**
 * Created by Chetan on 19-12-2015.
 */
public class WorkshopFragListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Detail> workshopList;
    int imgres;
    public WorkshopFragListAdapter(Context context, ArrayList<Detail> list){
        this.context = context;
        workshopList = list;
    }
    @Override
    public int getCount() {
        return workshopList.size();
    }

    @Override
    public Object getItem(int position) {
        return workshopList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Detail d = workshopList.get(position);
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_cards,parent,false);
        }
        TextView list_name = (TextView)convertView.findViewById(R.id.list_name);
        ImageView list_img = (ImageView)convertView.findViewById(R.id.list_img);
        TextView txtGrp = (TextView)convertView.findViewById(R.id.txtGrp);
        txtGrp.setText(d.getGroup().toString());
        list_name.setText(d.getName().toString());

        imgres = context.getResources().getIdentifier(d.getDrawable().toString(),"drawable", context.getPackageName());
        list_img.setImageResource(imgres);


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int tempi = position; 
                Intent intent = new Intent(context, DetailWorkshopActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                intent.putExtra("rowidWrk",String.valueOf(tempi+1));
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
