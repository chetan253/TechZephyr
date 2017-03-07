package com.tzltce.techzephyr;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tzltce.techzephyr.adapters.WorkshopFragListAdapter;
import com.tzltce.techzephyr.database.DBHandler;
import com.tzltce.techzephyr.model.Detail;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Chetan on 25-10-2015.
 */
public class WorkshopFragment extends Fragment {
    DBHandler db;
    Cursor c;
    WorkshopFragListAdapter adapter;
    public WorkshopFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_workshop, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Workshops");

        try{
            db = new DBHandler(getActivity().getApplicationContext(),null,null,1);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        try{
            db.createdatabase();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        db.opendatabase();
        c = db.getAllWorkshop();
        ArrayList<Detail> workshopnames = new ArrayList<Detail>();
        workshopnames.clear();
        if(c!=null){
            if(c.moveToFirst()){
                do{
                    Detail det = new Detail();
                    det.setName(c.getString(c.getColumnIndex(DBHandler.WORKSHOP_NAME)));
                    det.setDrawable(c.getString(c.getColumnIndex(DBHandler.WORKSHOP_DRAWABLE)));
                    det.setGroup(c.getString(c.getColumnIndex(DBHandler.WORKSHOP_GROUP)));
                    workshopnames.add(det);
                }while(c.moveToNext());
            }
        }
        c.close();
        ListView lv = (ListView)rootView.findViewById(R.id.listworkshop);
        adapter = new WorkshopFragListAdapter(getActivity().getApplicationContext(), workshopnames);
        lv.setAdapter(adapter);

        return rootView;
    }
}
