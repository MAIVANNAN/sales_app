package sales_app.com.sales_app.Activities;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Button;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import sales_app.com.sales_app.R;
import sales_app.com.sales_app.util.MyFirebaseInstanceIDService;


public class RegistrationActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_EMAIL =7;
    MainActivity globalvariable;
    //String url ="http://6fc7bfc8.ngrok.io/php_login/sales_register.php";

    private String TAG = "AccountsActivityTAG";
    private String wantPermission = Manifest.permission.GET_ACCOUNTS;
    private Activity activity = RegistrationActivity.this;
    private static final int PERMISSION_REQUEST_CODE = 1;

    SharedPreferences manager_id;










    final Context context = this;

    Button signupbtn;
    private TextInputEditText fname;
    private TextInputEditText lname;
    private TextInputEditText email;
    private TextInputEditText phone;
    private TextInputEditText password;
    private TextInputEditText confirmpassword;
    TextInputLayout fname_label,lnam_label,ema_label,phon_label,pass_label,confirm_label;
    String selectWay;




    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    //private static TextView googleAccounts, allAccounts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        // final RadioGroup rg = (RadioGroup) findViewById(R.id.radioGender);

        fname_label = (TextInputLayout) findViewById(R.id.fname_label);
        lnam_label = (TextInputLayout) findViewById(R.id.lnam_label);
        ema_label = (TextInputLayout) findViewById(R.id.ema_label);
        phon_label = (TextInputLayout) findViewById(R.id.phon_label);
        pass_label = (TextInputLayout) findViewById(R.id.pass_label);
        confirm_label = (TextInputLayout) findViewById(R.id.confirm_label);
        fname = (TextInputEditText) findViewById(R.id.firstnamewrapper);
        lname = (TextInputEditText) findViewById(R.id.lastnamewrapper);
        email = (TextInputEditText) findViewById(R.id.emailwrapper);



        phone = (TextInputEditText) findViewById(R.id.phonewrapper);
        password = (TextInputEditText) findViewById(R.id.passwordwrapper);
        confirmpassword = (TextInputEditText) findViewById(R.id.confirmpasswordwrapper);
        signupbtn = findViewById(R.id.signUp);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        //radioGenderGroup =  findViewById(R.id.radioGender);
        //final int selectedId = radioGenderGroup.getCheckedRadioButtonId();
        // radioGenderButton = findViewById(selectedId);









        email.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkPermission(wantPermission)) {
                    requestPermission(wantPermission);

                } else {
                    selectGoogleDriveAccount();
                }

            }
        });




        signupbtn.setOnClickListener(new View.OnClickListener() {

            //String gender = radioGenderButton.getText().toString();


            @Override
            public void onClick(View v) {


                int selectedId = radioSexGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioSexButton = (RadioButton) findViewById(selectedId);
                selectWay = radioSexButton.getText().toString();
                Log.i("radio sex button",""+selectWay);

                String semail = email.getText().toString();
                String suser1 = fname.getText().toString();
                String suser2 = lname.getText().toString();
                String spass = password.getText().toString();
                String sgender = selectWay;
                String smob = phone.getText().toString();

                String dId = FirebaseInstanceId.getInstance().getToken();




                if (!validateFirstname() || !validateEmail() || !validateLastname() || !validatePassword() || !validateConfirmpassword()||!validatePhone()) {


                } else {


                    signup(suser1, suser2, semail, smob, sgender, spass, dId);
                    SharedPreferences preferences = getSharedPreferences("Mypref", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.putString("fname", suser1);
                    editor.putString("lname", suser2);
                    editor.putString("email", semail);
                    editor.putString("mobile", smob);
                    editor.putString("gender", sgender);
                    editor.putString("password", spass);
                    editor.putString("dId", dId);
                    editor.putString("role", "manager");

                    Log.i("Hitesh",""+dId);

                    editor.commit();


                }

            }

        });
    }











    public void show( final String otp ,final String s_id){

        Intent intentOTP = new Intent(RegistrationActivity.this,OtpActivity.class);

        intentOTP.putExtra("OTP",otp);
        intentOTP.putExtra("s_id",s_id);
        startActivity(intentOTP);
        finish();

    }


    public void signup(final String fname, final String lname,final String email, final String phone,final String gender ,final String password,final  String dID){

        SharedPreferences LINK = getSharedPreferences("MAIN_LINK",0);
        String link1 = LINK.getString("MAIN_LINK","");
        Log.i("maivannan", "" + link1);
        String URL_REGIS = link1+"sales_register.php";
        Log.i("maivannan", "" + URL_REGIS);



        RequestQueue requestQueue = Volley.newRequestQueue(RegistrationActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("Hitesh",""+response);

                JSONObject jsonObj1 ;

                try {
                    jsonObj1 = new JSONObject(response);

                    JSONObject jsonObj = new JSONObject(jsonObj1.getString("Register_Response"));
                    if(jsonObj.getString("errorcode").equals("47000")){

                        Log.i("JSON OBJECT",""+jsonObj.getString("otp"));

                        Log.i("JSON OBJECT",""+jsonObj.getString("s_id"));
                        final String s_id=jsonObj.getString("s_id");
                        final String otp=jsonObj.getString("otp");
                        Toast.makeText(RegistrationActivity.this, ""+otp, Toast.LENGTH_SHORT).show();

                        show(otp,s_id);




                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }





                Log.i("maivannan",""+dID);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Hitesh",""+error);
                Toast.makeText(RegistrationActivity.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> stringMap = new HashMap<>();
                stringMap.put("userEmail",email);
                stringMap.put("name",fname);
                stringMap.put("lname",lname);
                stringMap.put("gender",gender);
                stringMap.put("userPass",password);
                stringMap.put("phone",phone);
                stringMap.put("device_id",dID);
                stringMap.put("role","manager");

                Log.i("maivannan",""+dID);

                return stringMap;
            }
        };

        requestQueue.add(stringRequest);


    }





        /*

        RequestQueue requestQueue = Volley.newRequestQueue(RegistrationActivity.this);


        String url1 ="http://44d9f5d9.ngrok.io/php_login/sales_register.php";
        StringRequest request = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {

                Toast.makeText(RegistrationActivity.this, response, Toast.LENGTH_SHORT).show();

                Log.d("driver_success_api res", response);

                try {

                    // Log.e("ash",response.trim());

                    JSONObject jsonObject = new JSONObject(response);

                    // Toast.makeText(getApplicationContext(),jsonObject.toString(),Toast.LENGTH_SHORT).show();

                    JSONObject eCode = new JSONObject(jsonObject.getString("Login_Response"));

                    String errorcd = eCode.getString("errorcode");

                    String errormsg = eCode.getString("message");

                    //  String otp=jsonObject.getString("otp");


                    if (errorcd.equals("2000"))

                    {

                        JSONObject eCode1 = new JSONObject(jsonObject.getString("profile_details"));

                        //  data1=jsonObject.getString("bus_no");

                        List<GlobalVariable> globalVariable = new ArrayList<>(2);
                        globalVariable.set(eCode1.getString("driver_id"));

                        globalVariable.setDriver_NAME(eCode1.getString("driver_name"));

                        globalVariable.setDriver_Phone(eCode1.getString("driver_phone"));

                        globalVariable.setConductor_name(eCode1.getString("conductor_name"));

                        globalVariable.setConductor_Phone(eCode1.getString("conductor_phone"));


                        globalVariable.setLogin_Status("1");

                        Intent activityChangeIntent = new Intent(RegistrationActivity.this, MainActivity.class);

                        startActivity(activityChangeIntent);


                    } else

                    {

                        Toast.makeText(RegistrationActivity.this, errormsg, Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {

                    e.printStackTrace();

                }


            }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {


            }

        })

        {

            @Override

            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> hashMap = new HashMap<String, String>();

                hashMap.put("otp_response", "successfull");

                hashMap.put("dr_phone_no", phone.getText().toString());

                return hashMap;

            }

        };

        requestQueue.add(request);
        */




    private boolean selectGoogleDriveAccount() {
        Log.v(TAG, "SettingsActivity::selectGoogleDriveAccount. Entry...");

        Intent intent = AccountManager.newChooseAccountIntent(null, null, new String[]{"com.google"}, true, null, null, null, null);
        startActivityForResult(intent, 7);


        Log.v(TAG, "SettingsActivity::selectGoogleDriveAccount. Exit.");
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_EMAIL && resultCode == RESULT_OK) {
            String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            Toast.makeText(RegistrationActivity.this,accountName,Toast.LENGTH_SHORT).show();
            email.setText(accountName);
        }
    }



    private boolean checkPermission(String permission){
        if (Build.VERSION.SDK_INT >= 23) {
            int result = ContextCompat.checkSelfPermission(activity, permission);
            if (result == PackageManager.PERMISSION_GRANTED){
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    private void requestPermission(String permission){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)){
            Toast.makeText(activity, "Get account permission allows us to get your email",
                    Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(activity, new String[]{permission}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectGoogleDriveAccount();
                } else {
                    Toast.makeText(activity,"Permission Denied.",
                            Toast.LENGTH_LONG).show();
                }
                break;
        }
    }



    private boolean validateFirstname() {
        String Firstname = fname.getEditableText().toString().trim();
        if (Firstname.isEmpty()) {
            fname_label.setError("Field can't be Empty");
            return false;
        } else {
            fname_label.setError(null);
            return true;
        }
    }

    private boolean validateLastname() {
        String Lastname = lname.getEditableText().toString().trim();
        if (Lastname.isEmpty()) {
            lnam_label.setError("Field can't be empty");
            return false;
        } else {
            lnam_label.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        String Email = email.getText().toString().trim();
        if (Email.isEmpty()) {
            ema_label.setError("Field can't be empty");
            return false;
        } else {
            ema_label.setError(null);
            return true;
        }
    }

    private boolean validatePhone() {
        String Phone = email.getText().toString().trim();
        if (Phone.isEmpty()) {
            phon_label.setError("Field can't be empty");
            return false;
        } else {
            phon_label.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String Password = password.getText().toString().trim();
        if (Password.isEmpty()) {
            pass_label.setError("Field can't be empty");
            return false;
        } else {
            pass_label.setError(null);
            return true;
        }
    }

    private boolean validateConfirmpassword() {
        String Confirmpassword = confirmpassword.getText().toString().trim();
        if (Confirmpassword.isEmpty()) {
            confirm_label.setError("Field can't be empty");
            return false;
        } else {
            confirm_label.setError(null);
            return true;
        }
    }

   /* public void confirminput(View view) {
        if (!validateFirstname()|!validateLastname()|!validatePhone()|!validateEmail()|!validatePassword()|!validateConfirmpassword())
        {
            Intent activityChangeIntent = new Intent(RegistrationActivity.this,OtpActivity.class);
            startActivity(activityChangeIntent);
            signup(semail,suser,spass,smob);
            SharedPreferences preferences = getSharedPreferences("Mypref", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.putString("username",fname);
            editor.putString("password",spass);
            editor.putString("mobile",smob);
            editor.putString("email",semail);
            editor.commit();
            show();


            return;
        }


        String input="Firstname:"+fname.getText().toString();
        input += "\n";
        input = "Lastname:" + lname.getText().toString();
        input += "\n";
        input = "Email:" + email.getText().toString();
        input += "\n";
        input = "Phone:" + phone.getText().toString();
        input += "\n";
        input = "Password:" + password.getText().toString();
        input += "\n";
        input = "Confirmpassword:" + confirmpassword.getText().toString();
        input += "\n";
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
    }*/







}













