package com.tzltce.techzephyr;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.klinker.android.sliding.SlidingActivity;
import com.tzltce.techzephyr.database.DBHandler;
import com.tzltce.techzephyr.model.Detail;

import java.io.IOException;

public class DetailWorkshopActivity extends SlidingActivity {
    DBHandler db;
    Cursor c;
    TextView headNameTxtDataWrk,feesamounttxtWrk,awardtxtWrk,descriptiontxtWrk;
    String headNamestr, feesamountstr, awardstr, descriptionstr;
    String rowidWrk, head_contactno, title, imageID;
    int drawablefrmdb;
    Button contactbuttonWrk;
    Detail details;
    @Override
    public void init(Bundle bundle) {
        setPrimaryColors(
                getResources().getColor(R.color.my_accent),
                getResources().getColor(R.color.my_primary_dark)
        );
        Bundle data = getIntent().getExtras();

        rowidWrk = data.getString("rowidWrk").toString();

        setContent(R.layout.activity_detail_workshop);

        //views
        headNameTxtDataWrk = (TextView)findViewById(R.id.headNameTxtDataWrk);
        contactbuttonWrk = (Button)findViewById(R.id.contactbuttonWrk);
        feesamounttxtWrk =(TextView)findViewById(R.id.feesamounttxtWrk);
        awardtxtWrk = (TextView)findViewById(R.id.awardtxtWrk);
        descriptiontxtWrk = (TextView)findViewById(R.id.descriptiontxtWrk);

        dbFunctioningWorkshop();

        title = details.getName().toString();
        setTitle(title);
        setFab(
                getResources().getColor(R.color.fab_color),
                R.mipmap.ic_create_black_48dp,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent registerIntent = new Intent(DetailWorkshopActivity.this, RegisterActivity.class);
                        registerIntent.putExtra("name", title);
                        startActivity(registerIntent);
                    }
                }
        );
        head_contactno = details.getHeadNo();
        headNameTxtDataWrk.setText(headNamestr);
        feesamounttxtWrk.setText(feesamountstr);
        awardtxtWrk.setText(awardstr);
        descriptiontxtWrk.setText(descriptionstr);
        contactbuttonWrk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                //intent.setPackage("com.android.dialer");
                intent.setData(Uri.parse("tel:" + head_contactno));
                try {
                    startActivity(intent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "Dialer is not founded", Toast.LENGTH_SHORT).show();
                }

            }

        });
        drawablefrmdb = getResources().getIdentifier(imageID ,"drawable", this.getPackageName());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setImage(drawablefrmdb);
            }
        }, 500);
    }


    private void dbFunctioningWorkshop() {
        try{
            db = new DBHandler(this ,null,null,1);
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
        details = db.getDetailWorkshop(rowidWrk);

        headNamestr = details.getHead().toString();
        feesamountstr = details.getFees().toString();
        awardstr = details.getPrize().toString();
        descriptionstr = details.getDescription().toString();
        imageID = details.getBackdrop().toString();
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
