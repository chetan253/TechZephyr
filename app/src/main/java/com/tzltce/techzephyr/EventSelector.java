package com.tzltce.techzephyr;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ListView;

import com.tzltce.techzephyr.adapters.EventsListAdapter;
import com.tzltce.techzephyr.database.DBHandler;
import com.tzltce.techzephyr.model.Detail;

import java.io.IOException;
import java.util.ArrayList;
/*
* This class is used in registeration activity in order
* to select events easily.
* It will describe the workshop list.
* */
public class EventSelector extends AppCompatActivity {

    DBHandler db;
    Cursor c;
    CheckBox ch;
    ListView lv;
    EventsListAdapter adapter;
    Toolbar mtoolbareventselect;
    RegisterActivity registerActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_event_selector);
        ch = (CheckBox)findViewById(R.id.eventcheckBox);
        lv = (ListView)findViewById(R.id.listViewEventNames);
        registerActivity = new RegisterActivity();
        setuptoolbar();
        loadData();
    }

    private void setuptoolbar() {
        mtoolbareventselect = (Toolbar)findViewById(R.id.toolbareventselect);
        mtoolbareventselect.setTitleTextColor(Color.BLACK);
        setSupportActionBar(mtoolbareventselect);
        getSupportActionBar().setTitle("Select Event");

    }

    private void loadData() {

        try{
            db = new DBHandler(EventSelector.this,null,null,1);
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

        ArrayList<Detail> names = new ArrayList<Detail>();
        names.clear();
        c = db.getAllEventNames();
        if(c!=null){
            if(c.moveToFirst()){
                do{
                    Detail det = new Detail();
                    det.setName(c.getString(c.getColumnIndex(DBHandler.EVENT_NAME)));
                    names.add(det);
                }while(c.moveToNext());
            }
        }
        c.close();

        adapter = new EventsListAdapter(EventSelector.this, names);
        lv.setAdapter(adapter);
    }
    public void showResult(View v) {
        try {
            String result = "";
            for (Detail p : adapter.getBox()) {
                if (p.box) {
                    result += p.getName() + ", ";
                }
            }
            result = result.substring(0, result.length() - 2) + ".";
            RegisterActivity.inputEvent.clearFocus();
            RegisterActivity.inputEvent.setText(result.toString());
            this.finish();
        }
        catch (Exception e){
            this.finish();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
