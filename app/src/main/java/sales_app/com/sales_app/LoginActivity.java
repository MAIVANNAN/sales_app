package sales_app.com.sales_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import sales_app.com.sales_app.Activities.MainActivity;
import sales_app.com.sales_app.Activities.RegistrationActivity;

public class LoginActivity extends AppCompatActivity {

    //public static final String PREFS_NAME = "LoginPrefs";
    public static String LINK ="http://3f9ed256.ngrok.io/php_login/";
    public static String Sign_up_URL = LINK+"sm_login.php";
    TextInputEditText username, password;
    TextInputLayout username_label, password_label;
    Button login;
    TextView register_link;
    SharedPreferences MAIN_LINK1;


    SharedPreferences sp;
    SharedPreferences manager_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("login",MODE_PRIVATE);


        if(sp.getBoolean("logged",false)){
            goToMainActivity();
        }

        setContentView(R.layout.activity_login);
        username =  findViewById(R.id.username);
        password =  findViewById(R.id.pass);
        username_label =  findViewById(R.id.username_label);
        password_label =  findViewById(R.id.password_label);
        login =  findViewById(R.id.login);
        register_link =  findViewById(R.id.registrtion);

        manager_id = getSharedPreferences("manager_id",MODE_PRIVATE);
        MAIN_LINK1 =getSharedPreferences("MAIN_LINK",MODE_PRIVATE);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String userEmail = username.getText().toString();
                String userPass = password.getText().toString();
                String dId = FirebaseInstanceId.getInstance().getToken();
                Log.i("device_ID", "" + dId);




                if (!validateUsername() || !validatePassword()) {

                    Toast.makeText(LoginActivity.this, "Fill all details", Toast.LENGTH_SHORT).show();
                } else {

                    MAIN_LINK1.edit().putString("manager_id",LINK).apply();

                    signin(userEmail, userPass,dId);
                    SharedPreferences preferences = getSharedPreferences("Mypref", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.putString("userEmail", userEmail);
                    editor.putString("userPass", userPass);
                    editor.putString("dId",dId);
                    editor.apply();











                }
            }


        });


        register_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MAIN_LINK1.edit().putString("manager_id",LINK).apply();

                Intent activityChangeIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(activityChangeIntent);
                finish();
            }
        });

    }


        public void signin ( final String userEmail, final String userPass,final String dID){



            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Sign_up_URL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    Log.i("maivannan", "" + response);
                    try {


                        JSONObject object12 = new JSONObject(response);
                        Log.i("maivannan12", "" + object12);
                        if(object12.getString("errorcode").equals("47000")) {

                            {
                                String manger_id = object12.getString("s_id");
                                Log.i("String manager ID", "" + manger_id);
                                manager_id.edit().putString("manager_id", manger_id).apply();
                                MAIN_LINK1.edit().putString("MAIN_LINK",LINK).apply();
                                Log.i("LINK", "" + MAIN_LINK1);

                                goToMainActivity();


                                Log.i("JSON OBJECT manager ID", "" + object12.getString("s_id"));
                            }

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }




                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.i("Hitesh", "" + error);
                    Toast.makeText(LoginActivity.this, "" + error, Toast.LENGTH_SHORT).show();

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> stringMap = new HashMap<>();
                    stringMap.put("userEmail", userEmail);
                    stringMap.put("userPass", userPass);
                    stringMap.put("device_id",dID);
                    sp.edit().putBoolean("logged",true).apply();





                    Log.i("maivannan1234", "" + stringMap.toString());

                    return stringMap;
                }
            };

            requestQueue.add(stringRequest);

        }

        private boolean validateUsername ()
        {
            String Username = username.getEditableText().toString().trim();
            if (Username.isEmpty()) {
                username_label.setError("Field can't be Empty");
                return false;
            } else {
                username_label.setError(null);
                return true;
            }
        }

        public void goToMainActivity () {

            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();

        }

        private boolean validatePassword ()
        {
            String Password = password.getEditableText().toString().trim();
            if (Password.isEmpty()) {
                password_label.setError("Field can't be Empty");
                return false;
            } else {
                password_label.setError(null);
                return true;
            }
        }





}





  /*  Button login;
    TextView register_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button)findViewById(R.id.login);
        register_link = (TextView)findViewById(R.id.registration);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent activityChangeIntent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(activityChangeIntent);
                finish();
            }
        });
        register_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent activityChangeIntent = new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(activityChangeIntent);
                finish();
            }
        });

    }*/

