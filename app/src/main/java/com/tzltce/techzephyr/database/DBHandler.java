package com.tzltce.techzephyr.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.tzltce.techzephyr.model.Detail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Chetan on 26-10-2015.
 */
public class DBHandler extends SQLiteOpenHelper{
    private Context mycontext;
    private String DB_PATH = "/data/data/com.tzltce.techzephyr/databases/";
    private static String DB_NAME = "techzephyr.db";
    public static String DB_TABLE_INTRO="intro_tech";
    public static String DB_TABLE_CREW_TZ="crew_tz";
    public static String DB_TABLE_EVENTS="events";
    public static String DB_TABLE_WORKSHOP="workshop_info";

    //column intro
    public static final String INTRO_ID = "_id";
    public static final String INTRO_HEADER = "header";
    public static final String INTRO_DESCRIPTION = "description";

    //column crew_tz
    public static final String CREW_ID = "_id";
    public static final String CREW_NAME = "name";
    public static final String CREW_POST = "post";
    public static final String CREW_NUMBER = "number";

    //column events
    public static final String EVENT_ID = "_id";
    public static final String EVENT_NAME = "event_name";
    public static final String EVENT_DRAWABLE = "event_drawable";
    public static final String EVENT_BACKDROP = "event_backdrop";
    public static final String EVENT_HEAD = "event_head";
    public static final String EVENT_HEAD_NO = "event_head_no";
    public static final String EVENT_FEES = "event_fees";
    public static final String EVENT_PRIZE = "event_prize";
    public static final String EVENT_DESCRIPTION = "event_description";
    public static final String EVENT_GROUP = "event_group";

    //column workshop
    public static final String WORKSHOP_ID = "_id";
    public static final String WORKSHOP_NAME = "w_name";
    public static final String WORKSHOP_DRAWABLE = "w_drawable";
    public static final String WORKSHOP_BACKDROP = "w_backdrop";
    public static final String WORKSHOP_HEAD = "w_head";
    public static final String WORKSHOP_HEAD_NO = "w_head_no";
    public static final String WORKSHOP_FEES = "w_fees";
    public static final String WORKSHOP_PRIZE = "w_prize";
    public static final String WORKSHOP_DESCRIPTION = "w_description";
    public static final String WORKSHOP_GROUP = "w_group";
    public SQLiteDatabase myDatabase;

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)throws IOException{
        super(context, DB_NAME, null, version);
        this.mycontext = context;
    }
    public void createdatabase() throws IOException{
        boolean dbexist = checkdatabase();
            this.getReadableDatabase();
            try{
                copydatabase();
            }
            catch(IOException e){
                throw new Error("Error copying database");
            }
    }
    private boolean checkdatabase() {
        //SQLiteDatabase checkdb = null;
        boolean checkdb = false;
        try{
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE) != null;
            checkdb = dbfile.exists();
        }
        catch(SQLiteException e){
            System.out.println("Database doesn't exist");
        }

        return checkdb;
    }
    private void copydatabase() throws IOException {

        //Open your local db as the input stream
        InputStream myinput = mycontext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outfilename = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream(outfilename);

        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer))>0)
        {
            myoutput.write(buffer,0,length);
        }

        //Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();

    }

    public void opendatabase() throws SQLException
    {
        String mypath = DB_PATH + DB_NAME;
        myDatabase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READONLY);

    }

    public synchronized void close(){
        if(myDatabase != null){
            myDatabase.close();
        }
        super.close();
    }

    public Cursor getTechIntro(){

        Cursor cintro = myDatabase.rawQuery("SELECT * FROM " + DB_TABLE_INTRO,null);
        if(cintro!=null) {
            cintro.moveToFirst();
        }
        return cintro;
    }

    public Cursor getTechCrew(){

        Cursor crew = myDatabase.rawQuery("SELECT * FROM " + DB_TABLE_CREW_TZ,null);
        if(crew!=null) {
            crew.moveToFirst();
        }
        return crew;
    }

   public Cursor getAllEvents(){

        Cursor ce = myDatabase.rawQuery("SELECT * FROM " + DB_TABLE_EVENTS,null);
        if(ce!=null) {
            ce.moveToFirst();
        }
        return ce;
    }

    public Cursor getAllWorkshop(){

        Cursor cw = myDatabase.rawQuery("SELECT * FROM " + DB_TABLE_WORKSHOP,null);
        if(cw!=null) {
            cw.moveToFirst();
        }
        return cw;
    }

    public Cursor getAllEventNames(){

        Cursor cen = myDatabase.query(DB_TABLE_EVENTS, new String[]{EVENT_NAME}, null, null, null, null, null, null);
        if(cen!=null) {
            cen.moveToFirst();
        }
        return cen;
    }

    public Cursor getAllWorkshopNames(){

        Cursor cwn = myDatabase.query(DB_TABLE_WORKSHOP, new String[]{WORKSHOP_NAME}, null, null, null, null, null, null);
        if(cwn!=null) {
            cwn.moveToFirst();
        }
        return cwn;
    }


    public Detail getDetailEvent(String id) {
        //SQLiteDatabase db = this.getReadableDatabase();

        Cursor cde = myDatabase.query(DB_TABLE_EVENTS,
                new String[]{EVENT_ID,
                EVENT_DRAWABLE,
                EVENT_BACKDROP,
                EVENT_NAME,
                EVENT_HEAD,
                EVENT_HEAD_NO,
                EVENT_FEES,
                EVENT_PRIZE,
                EVENT_DESCRIPTION,
                EVENT_GROUP}, EVENT_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if(cde != null)
            cde.moveToNext();
        Detail detail = new Detail(cde.getString(cde.getColumnIndex(EVENT_ID)),
                cde.getString(cde.getColumnIndex(EVENT_DRAWABLE)),
                cde.getString(cde.getColumnIndex(EVENT_BACKDROP)),
                cde.getString(cde.getColumnIndex(EVENT_NAME)),
                cde.getString(cde.getColumnIndex(EVENT_HEAD)),
                cde.getString(cde.getColumnIndex(EVENT_HEAD_NO)),
                cde.getString(cde.getColumnIndex(EVENT_FEES)),
                cde.getString(cde.getColumnIndex(EVENT_PRIZE)),
                cde.getString(cde.getColumnIndex(EVENT_DESCRIPTION)),
                cde.getString(cde.getColumnIndex(EVENT_GROUP)));
        return detail;
    }

    public Detail getDetailWorkshop(String id) {
        //SQLiteDatabase db = this.getReadableDatabase();

        Cursor cdew = myDatabase.query(DB_TABLE_WORKSHOP, new String[]{WORKSHOP_ID,
                WORKSHOP_DRAWABLE,
                WORKSHOP_BACKDROP,
                WORKSHOP_NAME,
                WORKSHOP_HEAD,
                WORKSHOP_HEAD_NO,
                WORKSHOP_FEES,
                WORKSHOP_PRIZE,
                WORKSHOP_DESCRIPTION,
                WORKSHOP_GROUP}, WORKSHOP_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if(cdew != null)
            cdew.moveToNext();
        Detail detailwrk = new Detail(cdew.getString(cdew.getColumnIndex(WORKSHOP_ID)),
                cdew.getString(cdew.getColumnIndex(WORKSHOP_DRAWABLE)),
                cdew.getString(cdew.getColumnIndex(WORKSHOP_BACKDROP)),
                cdew.getString(cdew.getColumnIndex(WORKSHOP_NAME)),
                cdew.getString(cdew.getColumnIndex(WORKSHOP_HEAD)),
                cdew.getString(cdew.getColumnIndex(WORKSHOP_HEAD_NO)),
                cdew.getString(cdew.getColumnIndex(WORKSHOP_FEES)),
                cdew.getString(cdew.getColumnIndex(WORKSHOP_PRIZE)),
                cdew.getString(cdew.getColumnIndex(WORKSHOP_DESCRIPTION)),
                cdew.getString(cdew.getColumnIndex(WORKSHOP_GROUP)));
        return detailwrk;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
