package com.tzltce.techzephyr;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tzltce.techzephyr.database.DBHandler;
import com.tzltce.techzephyr.model.CrewRecycler;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Chetan on 22-12-2015.
 */
public class CrewFragment extends Fragment {

    DBHandler db;
    Cursor c;

    public CrewFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_crew, container, false);

        RecyclerView recList = (RecyclerView) rootView.findViewById(R.id.cardRecyclerListCrew);
        recList.setHasFixedSize(true);
        recList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
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
        c = db.getTechCrew();
        ArrayList<CrewRecycler> result = new ArrayList<CrewRecycler>();

        if(c!=null){
            if(c.moveToFirst()){
                do{
                    CrewRecycler in = new CrewRecycler();
                    in.setName(c.getString(c.getColumnIndex(DBHandler.CREW_NAME)));
                    in.setPost(c.getString(c.getColumnIndex(DBHandler.CREW_POST)));
                    in.setNumber(c.getString(c.getColumnIndex(DBHandler.CREW_NUMBER)));
                    result.add(in);
                }while(c.moveToNext());
            }
        }
        c.close();

        CrewRecyclerAdapter adapter = new CrewRecyclerAdapter(result);
        recList.setAdapter(adapter);

        return rootView;
    }

    public class CrewRecyclerAdapter extends RecyclerView.Adapter<CrewRecyclerAdapter.CrewViewHolder>{

        public ArrayList<CrewRecycler> infoList;

        public CrewRecyclerAdapter(ArrayList<CrewRecycler> infoList){
            this.infoList = infoList;
        }
        @Override
        public CrewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.
                    from(parent.getContext()).
                    inflate(R.layout.crew_list_item, parent, false);
            return new CrewViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(CrewViewHolder holder, int position) {
            CrewRecycler info = infoList.get(position);
            holder.crewname.setText(info.getName());
            holder.crewpost.setText(info.getPost());
            final String number = info.getNumber().toString();
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    //intent.setPackage("com.android.dialer");
                    intent.setData(Uri.parse("tel:" + number));
                    try {
                        startActivity(intent);
                    }catch (ActivityNotFoundException ex){
                        Toast.makeText(getActivity().getApplicationContext(), "Dialer is not founded", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return infoList.size();
        }


        public class CrewViewHolder extends RecyclerView.ViewHolder {
            protected TextView crewname;
            protected TextView crewpost;

            public CrewViewHolder(View v) {
                super(v);
                crewname = (TextView) v.findViewById(R.id.crewname);
                crewpost = (TextView) v.findViewById(R.id.crewpost);
            }
        }
    }
}
