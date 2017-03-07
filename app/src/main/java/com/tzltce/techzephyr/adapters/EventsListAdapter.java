package com.tzltce.techzephyr.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.tzltce.techzephyr.R;
import com.tzltce.techzephyr.model.Detail;

import java.util.ArrayList;

/**
 * Created by Chetan on 15-12-2015.
 */


// As adapter specifies only about the events but we will use it also for
    //workshop name display in dialog.
public class EventsListAdapter extends BaseAdapter{
    Context context;
    ArrayList<Detail> eventNameList;

    public EventsListAdapter(Context context, ArrayList<Detail> list){
        this.context = context;
        eventNameList = list;
    }

    @Override
    public int getCount() {
        return eventNameList.size();
    }

    @Override
    public Object getItem(int position) {
        return eventNameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Detail d = eventNameList.get(position);

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.regevent_list_item,parent,false);
        }
        CheckBox ch = (CheckBox)convertView.findViewById(R.id.eventcheckBox);
        ch.setText(d.getName());
        ch.setOnCheckedChangeListener(myCheckChangList);
        ch.setTag(position);
        ch.setChecked(d.box);
        return convertView;
    }

    public Detail getDetailEvent(int position){
        return((Detail)getItem(position));
    }

    public ArrayList<Detail> getBox(){
        ArrayList<Detail> box = new ArrayList<Detail>();
        for(Detail d : eventNameList){
            if(d.box){
                box.add(d);
            }
        }
        return box;
    }
    OnCheckedChangeListener myCheckChangList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            getDetailEvent((Integer) buttonView.getTag()).box = isChecked;
        }
    };

}
