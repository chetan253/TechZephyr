package com.tzltce.techzephyr;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import de.psdev.licensesdialog.LicensesDialogFragment;

public class AboutActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private View rootView;
    private String version_no;


    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.about:
                    SpannableString spannableString = new SpannableString("Tech Zephyr is an application developed by Chetan Patil.\n\n" +//43-55
                            "The new version includes following features:\n\n" +//56-102
                            "Provide Latest Information about Events carried out in College Festival.\n" +
                            "Easy to Register Facility available in-app for applying in events via Online Registrations.");
                    ClickableSpan clickableSpan = new ClickableSpan() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_SENDTO);
                            intent.setData(Uri.parse("mailto:"));
                            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"cpatil0@gmail.com"});
                            if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivity(intent);
                            }
                        }
                    };
                    spannableString.setSpan(clickableSpan, 43, 55, 0);
                    spannableString.setSpan(new RelativeSizeSpan(1.5f), 0, 55, 0);
                    spannableString.setSpan(new RelativeSizeSpan(1.5f), 56, 102, 0);
                    spannableString.setSpan(new StyleSpan(Typeface.BOLD), 56, 102, 0);
                    spannableString.setSpan(new RelativeSizeSpan(1.5f), 103, spannableString.length(), 0);
                    TextView textView = new TextView(getApplicationContext());
                    textView.setMovementMethod(LinkMovementMethod.getInstance());
                    textView.setTextColor(Color.BLACK);
                    textView.setText(spannableString);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AboutActivity.this);
                    alertDialogBuilder.setTitle("About");
                    alertDialogBuilder.setView(textView);
                    alertDialogBuilder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    break;
                case R.id.about_licenses:
                    try{
                        final LicensesDialogFragment fragment = new LicensesDialogFragment.Builder(AboutActivity.this)
                                .setNotices(R.raw.licenses)
                                .build();

                        fragment.show(getSupportFragmentManager(), null);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                    break;
                case R.id.about_problems:
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:"));
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"cpatil0@gmail.com"});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Tech Zephyr Application Issue");
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }

                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        rootView = findViewById(R.id.about_container);
        mToolbar =(Toolbar)findViewById(R.id.toolbarabout);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("About");
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version_no = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        TextView body = (TextView) rootView.findViewById(R.id.about_main);
        body.setText("Tech Zephyr\n" + "v"+version_no);
        rootView.findViewById(R.id.about).setOnClickListener(mOnClickListener);
        rootView.findViewById(R.id.about_licenses).setOnClickListener(mOnClickListener);
        rootView.findViewById(R.id.about_problems).setOnClickListener(mOnClickListener);
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
