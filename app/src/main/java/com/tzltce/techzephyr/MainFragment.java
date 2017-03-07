package com.tzltce.techzephyr;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tzltce.techzephyr.adapters.InfoRecyclerAdapter;
import com.tzltce.techzephyr.database.DBHandler;
import com.tzltce.techzephyr.model.InfoRecycler;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Chetan on 25-10-2015.
 */
public class MainFragment extends Fragment{
    DBHandler db;
    Cursor c;
    public MainFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Tech Zephyr");
        RecyclerView recList = (RecyclerView) rootView.findViewById(R.id.cardRecyclerList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

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
        c = db.getTechIntro();
        ArrayList<InfoRecycler> result = new ArrayList<InfoRecycler>();

        if(c!=null){
            if(c.moveToFirst()){
                do{
                    InfoRecycler in = new InfoRecycler();
                    in.setHead(c.getString(c.getColumnIndex(DBHandler.INTRO_HEADER)));
                    in.setDesc(c.getString(c.getColumnIndex(DBHandler.INTRO_DESCRIPTION)));
                    result.add(in);
                }while(c.moveToNext());
            }
        }
        c.close();

        InfoRecyclerAdapter adapter = new InfoRecyclerAdapter(result);
        recList.setAdapter(adapter);


        return rootView;
    }
}
