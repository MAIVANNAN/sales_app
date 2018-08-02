package sales_app.com.sales_app.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import sales_app.com.sales_app.LoginActivity;
import sales_app.com.sales_app.R;

public class OtpActivity extends AppCompatActivity {
    private  static final String url ="http://6f46f287.ngrok.io/php_login/otp_success.php";

    EditText otp;
    Button tv;
    String ONETP;
    String s_id;
    public static final String PREFS_NAME1 = "success";
    SharedPreferences sp;
    SharedPreferences manager_id ;
    String manger_id;
    String success;





    Button  register_link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        sp = getSharedPreferences("login",MODE_PRIVATE);


        tv = findViewById(R.id.verify_otp);
        //String OOOTTPP=tv.getText().toString();
        otp = findViewById(R.id.otp);

        Intent intentOTP = getIntent();
        Bundle bd = intentOTP.getExtras();
        if (bd != null) {
            ONETP = (String) bd.get("OTP");
            s_id=(String)bd.get("s_id");
            Log.i("JSON OBJECT",""+s_id);


        }

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(otp.getText().toString().equals(ONETP)){


                    signup(s_id);


               }

            }
        });


    }
    public void signup(final String s_id){

        SharedPreferences LINK = getSharedPreferences("MAIN_LINK",0);
        String link1 = LINK.getString("MAIN_LINK","");
        Log.i("maivannan", "" + link1);
        String URL_OTP = link1+"otp_success.php";
        Log.i("maivannan", "" + URL_OTP);

        RequestQueue requestQueue = Volley.newRequestQueue(OtpActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_OTP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("Hitesh",""+response);

                JSONObject jsonObj1 ;

                try {
                    jsonObj1 = new JSONObject(response);

                    JSONObject jsonObj = new JSONObject(jsonObj1.getString("otp_response"));
                    if(jsonObj.getString("errorcode").equals("47000")){

                        manager_id = getSharedPreferences("manager_id",MODE_PRIVATE);

                        Log.i("AFTER OTP",""+jsonObj.getString("message"));
                         success = jsonObj.getString("message");
                          manger_id = jsonObj.getString("s_id");
                        Log.i("Manager_id ",""+jsonObj.getString("s_id"));

                        manager_id.edit().putString("manager_id", manger_id).apply();





                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }







            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Hitesh",""+error);
                Toast.makeText(OtpActivity.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> stringMap = new HashMap<>();


                stringMap.put("otp_res","success");
                stringMap.put("s_id",s_id);
                sp.edit().putBoolean("logged",true).apply();

                Log.i("JSON OBJECT",""+stringMap.toString());


                goToMainActivity();


                return stringMap;
            }
        };

        requestQueue.add(stringRequest);


    }
    public void goToMainActivity () {

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();

    }

}



























       /* register_link = (Button)findViewById(R.id.btnsubotp);

        register_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(OtpActivity.this, MainActivity.class);
                startActivity(activityChangeIntent);


            }
        });*/
