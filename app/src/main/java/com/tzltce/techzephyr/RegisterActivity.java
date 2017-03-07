package com.tzltce.techzephyr;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbarreg;
    public EditText inputName;
    public EditText inputCollege;
    public static EditText inputEvent, inputWorkshop;
    public EditText inputPhone;
    public EditText inputEmail;
    public TextInputLayout inputLayoutName, inputLayoutCollege, inputLayoutEvent ,inputLayoutWorkshop, inputLayoutPhone, inputLayoutEmail;
    Button eventSelectbtn, wrkshpSelectbtn;
    private String imei;
    private boolean hasRegistered = false;
    
    private String insertURL = "http://localhost/techzephyr/insertEntry.php";
    ProgressDialog PD;
    RequestQueue requestQueue;
    public static final String TAG = "VolleyRegister";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupToolbar();
        getSupportActionBar().setTitle("Registration");
        requestQueue = Volley.newRequestQueue(this, new HurlStack());
        PD = new ProgressDialog(this);
        PD.setMessage("Loading...");
        PD.setCancelable(false);
        //get device specific id for database user identification
        imei = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        mCoordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinatorLayoutReg);

        inputLayoutName = (TextInputLayout)findViewById(R.id.input_layout_name);
        inputLayoutEvent = (TextInputLayout)findViewById(R.id.input_layout_event);
        inputLayoutWorkshop = (TextInputLayout)findViewById(R.id.input_layout_workshop);
        inputLayoutCollege = (TextInputLayout)findViewById(R.id.input_layout_college);
        inputLayoutPhone =(TextInputLayout)findViewById(R.id.input_layout_phone);
        inputLayoutEmail = (TextInputLayout)findViewById(R.id.input_layout_email);

        inputName = (EditText)findViewById(R.id.input_name);
        inputEvent = (EditText)findViewById(R.id.input_event);
        inputWorkshop = (EditText)findViewById(R.id.input_workshop);
        inputCollege = (EditText)findViewById(R.id.input_college);
        inputPhone = (EditText)findViewById(R.id.input_phone);
        inputEmail = (EditText)findViewById(R.id.input_email);

        eventSelectbtn = (Button)findViewById(R.id.eventSelectbtn);
        eventSelectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, EventSelector.class);
                startActivity(i);
            }
        });

        wrkshpSelectbtn = (Button)findViewById(R.id.wrkshpSelectbtn);
        wrkshpSelectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, WorkshopSelector.class);
                startActivity(i);
            }
        });

        inputName.addTextChangedListener(new MyTextWatcher(inputName));
        inputEvent.addTextChangedListener(new MyTextWatcher(inputEvent));
        inputWorkshop.addTextChangedListener(new MyTextWatcher(inputWorkshop));
        inputCollege.addTextChangedListener(new MyTextWatcher(inputCollege));
        inputPhone.addTextChangedListener(new MyTextWatcher(inputPhone));
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));

        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("checkreg",Context.MODE_PRIVATE);

        hasRegistered = sharedPreferences.getBoolean("hasreg", false);

        if(hasRegistered){
            //We will start the Profile Activity
            //Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            Toast.makeText(this, "You've been already registered.", Toast.LENGTH_SHORT).show();
            //startActivity(intent);
            //overridePendingTransition(R.anim.activity_left_in,R.anim.activity_right_out);
            RegisterActivity.this.finish();
        }
    }

    public boolean isNetworkAvailable() {
        try {
            NetworkInfo networkInfo = ((ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            return networkInfo.isConnected();
        } catch (Exception e) {
            return false;
        }
    }
    private void submitDetails(){
        RetryPolicy retryPolicy = new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        );
        PD.show();
        StringRequest request = new StringRequest(Request.Method.POST, insertURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PD.dismiss();
                Toast.makeText(RegisterActivity.this, "Thanks for Registration " + inputName.getText().toString(), Toast.LENGTH_LONG).show();
                SharedPreferences sharedPreferences = RegisterActivity.this.getSharedPreferences("checkreg",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("hasreg", true);
                editor.commit();
                onBackPressed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PD.dismiss();
                Snackbar snackbar = Snackbar.make(mCoordinatorLayout, "Connection could not be established. Try after some time." , Snackbar.LENGTH_INDEFINITE);
                snackbar.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters = new HashMap<String, String>();
                parameters.put("_imei", imei);
                parameters.put("name", inputName.getText().toString());
                parameters.put("college", inputCollege.getText().toString());
                parameters.put("event", inputEvent.getText().toString());
                parameters.put("workshop", inputWorkshop.getText().toString());
                parameters.put("phone", inputPhone.getText().toString());
                parameters.put("email", inputEmail.getText().toString());
                parameters.put("receipt_issued", "null");
                return parameters;
            }
        };
        request.setRetryPolicy(retryPolicy);
        request.setShouldCache(false);
        request.setTag(TAG);
        requestQueue.add(request);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        RegisterActivity.this.finish();

    }

    public boolean verifyFields(){
        if (!validateName()) {
            return false;
        }
        if((inputEvent.getText().toString().trim().isEmpty() || !inputWorkshop.getText().toString().trim().isEmpty())&&
                (!inputEvent.getText().toString().trim().isEmpty() || inputWorkshop.getText().toString().trim().isEmpty())) {
            if (!validateEventName()) {
                return false;
            }
            if (!validateWorkshopName()) {
                return false;
            }
        }
        if (!validateCollegeName()) {
            return false;
        }
        if (!validatePhone()) {
            return false;
        }
        if(!inputEmail.getText().toString().trim().isEmpty()) {
            //As its not mandatory so will check only when text is present in field
            if (!validateEmail()) {
                return false;
            }
        }
        return true;
    }

    private void setupToolbar() {
        mToolbarreg = (Toolbar)findViewById(R.id.toolbarreg);
        setSupportActionBar(mToolbarreg);
    }

    //Validation methods
    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError("Enter your Full Name");
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateEventName() {
        if (inputEvent.getText().toString().trim().isEmpty()) {
            inputLayoutEvent.setError("Enter valid Name");
            requestFocus(inputEvent);
            return false;
        } else {
            inputLayoutEvent.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateWorkshopName() {
        if (inputWorkshop.getText().toString().trim().isEmpty()) {
            inputLayoutWorkshop.setError("Enter valid Name");
            requestFocus(inputWorkshop);
            return false;
        } else {
            inputLayoutWorkshop.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateCollegeName() {
        if (inputCollege.getText().toString().trim().isEmpty()) {
            inputLayoutCollege.setError("Enter valid Name");
            requestFocus(inputCollege);
            return false;
        } else {
            inputLayoutCollege.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePhone() {
        String phone = inputPhone.getText().toString().trim();

        if (!isValidPhone(phone)) {
            inputLayoutPhone.setError("Enter valid Phone Number");
            //requestFocus(inputPhone);
            return false;
        }
        else {
            inputLayoutPhone.setErrorEnabled(false);
        }
        return true;
    }

    private static boolean isValidPhone(String phone) {
        return !TextUtils.isEmpty(phone) && Patterns.PHONE.matcher(phone).matches();
    }

    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (!isValidEmail(email)) {
            inputLayoutEmail.setError("Enter valid Email");
            //requestFocus(inputEmail);
            //return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
            return false;
        } else{
            inputLayoutEmail.setErrorEnabled(false);
        }
        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.register) {
            if(verifyFields() && isNetworkAvailable()) {
                submitDetails();
                hideKeyboard();
            }
            else if(!isNetworkAvailable()) {
                Snackbar snackbar = Snackbar.make(mCoordinatorLayout, "Make sure you have an working Internet Connection.", Snackbar.LENGTH_LONG);
                snackbar.show();
                //PD.dismiss();
            }
            else
                verifyFields();
            //PD.dismiss();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    validateName();
                    break;
                case R.id.input_event:
                    validateEventName();
                    break;
                case R.id.input_workshop:
                    validateWorkshopName();
                    break;
                case R.id.input_college:
                    validateCollegeName();
                    break;
                case R.id.input_phone:
                    validatePhone();
                    break;
                case R.id.input_email:
                    validateEmail();
                    break;
            }
        }
    }


}
