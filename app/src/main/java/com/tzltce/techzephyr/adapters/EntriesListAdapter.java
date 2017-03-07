package com.tzltce.techzephyr.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tzltce.techzephyr.R;
import com.tzltce.techzephyr.model.DetailOlEntry;

import java.util.ArrayList;

/**
 * Created by Chetan on 18-12-2015.
 */
public class EntriesListAdapter extends BaseAdapter{
    Context context;
    ArrayList<DetailOlEntry> entriesNameList;

    public EntriesListAdapter(Context context, ArrayList<DetailOlEntry> list){
        this.context = context;
        entriesNameList = list;
    }

    @Override
    public int getCount() {
        return entriesNameList.size();
    }

    @Override
    public Object getItem(int position) {
        return entriesNameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final DetailOlEntry d = entriesNameList.get(position);

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.olentrylist_item,parent,false);
        }
        TextView regid = (TextView)convertView.findViewById(R.id.idol);
        final TextView regname = (TextView)convertView.findViewById(R.id.nameol);
        final TextView regcollege = (TextView)convertView.findViewById(R.id.collegeol);
        regid.setText(d.getId());
        regname.setText(d.getName());
        regcollege.setText(d.getCollege());
        final String event = d.getEvent();
        final String workshop = d.getWorkshop();
        final String phone = d.getPhone();
        final String email = d.getEmail();
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle(regname.getText());
                alertDialogBuilder.setMessage("College: "+regcollege.getText()+"\n\nEvents Registered: " + event + "\n\nWorkhop Registered: " + workshop +
                        "\n\nPhone: " + phone + "\n\nEmail: " + email);
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        return convertView;
    }
}
