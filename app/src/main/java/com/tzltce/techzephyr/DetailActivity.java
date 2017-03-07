package com.tzltce.techzephyr;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import com.klinker.android.sliding.SlidingActivity;
import com.tzltce.techzephyr.database.DBHandler;
import com.tzltce.techzephyr.model.Detail;

import java.io.IOException;

public class DetailActivity extends SlidingActivity{
    DBHandler db;
    Cursor c;
    TextView headNameTxtData,feesamounttxt,awardtxt,descriptiontxt;
    String headNamestr, feesamountstr, awardstr, descriptionstr;
    String rowid, head_contactno, title, imageID;
    int drawablefrmdb;
    Button contactbutton;
    Detail details;
    @Override
    public void init(Bundle bundle) {
        setPrimaryColors(
                getResources().getColor(R.color.my_accent),
                getResources().getColor(R.color.my_primary_dark)
        );
        Bundle data = getIntent().getExtras();

        rowid = data.getString("rowid").toString();

        setContent(R.layout.activity_detail);

        //views
        headNameTxtData = (TextView)findViewById(R.id.headNameTxtData);
        contactbutton = (Button)findViewById(R.id.contactbutton);
        feesamounttxt =(TextView)findViewById(R.id.feesamounttxt);
        awardtxt = (TextView)findViewById(R.id.awardtxt);
        descriptiontxt = (TextView)findViewById(R.id.descriptiontxt);

            dbFunctioningEvent();

        title = details.getName().toString();
        setTitle(title);
        setFab(
                getResources().getColor(R.color.fab_color),
                R.mipmap.ic_create_black_48dp,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent registerIntent = new Intent(DetailActivity.this, RegisterActivity.class);
                        startActivity(registerIntent);
                        DetailActivity.this.finish();
                    }
                }
        );
        head_contactno = details.getHeadNo();
        headNameTxtData.setText(headNamestr);
        feesamounttxt.setText(feesamountstr);
        awardtxt.setText(awardstr);
        descriptiontxt.setText(descriptionstr);
        contactbutton.setOnClickListener(new View.OnClickListener() {

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
    }//oncreate ends


    private void dbFunctioningEvent() {
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
        details = db.getDetailEvent(rowid);

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
