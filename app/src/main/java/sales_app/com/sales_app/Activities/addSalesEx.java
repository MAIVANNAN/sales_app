package sales_app.com.sales_app.Activities;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sales_app.com.sales_app.R;
import sales_app.com.sales_app.areadialog;
import sales_app.com.sales_app.models.Area;


public class addSalesEx extends AppCompatActivity implements areadialog.ExampleDialogListener {

    private Spinner spinner;
    private List<Area> areaList ;

    private ArrayList<String> arrayList;

    private static final int REQUEST_CODE_EMAIL =7;
    String URL_add_sales_officer="http://6f46f287.ngrok.io/php_login/add_sales_officer.php";
    String URL_fetch_data="http://6f46f287.ngrok.io/php_login/area_list.php";
    MainActivity globalvariable;

    private String TAG = "addSalesActivity";
    private String wantPermission = Manifest.permission.GET_ACCOUNTS;
    private Activity activity = addSalesEx.this;
    private static final int PERMISSION_REQUEST_CODE = 1;






    private EditText exName, exMail, exPhone, exArea, exPasw,exAddr;
    private TextInputLayout exName_label,exEmail_label,exPh_label,exArea_label,exPass_label;
    protected Button AddSales;


    public static final String JSON_ARRAY = "area_list";
    private JSONArray area;
    String a_id;
    TextView employeename;
    TextView select_area2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sales_ex);

        exName_label=findViewById(R.id.exName_label);
        exEmail_label=findViewById(R.id.exEmail_label);
        exPh_label=findViewById(R.id.exPh_label);
        exPass_label=findViewById(R.id.exPass_label);
        exName = findViewById(R.id.EtSalesExName);
        exMail = findViewById(R.id.EtSalesExEmail);
        exPhone = findViewById(R.id.EtSalesExPh);
        exAddr=findViewById(R.id.EtSalesExAddr);
        exPasw = findViewById(R.id.EtSalesExPass);

        AddSales = findViewById(R.id.btEditSalesEx);
        employeename= (TextView) findViewById(R.id.area_id_cust);

        select_area2 =(TextView)findViewById(R.id.textView4);
        select_area2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        exMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkPermission(wantPermission)) {
                    requestPermission(wantPermission);

                } else {
                    selectGoogleDriveAccount();
                }

            }
        });



     AddSales.setOnClickListener(new View.OnClickListener() {

        //String gender = radioGenderButton.getText().toString();


        @Override
        public void onClick(View v) {
            String eemail = exMail.getText().toString();
            String euser1 = exName.getText().toString();
            String epass = exPasw.getText().toString();
            String eaddr = exAddr.getText().toString();
            String emob = exPhone.getText().toString();
            String earea = employeename.getText().toString();
            String M_Id ;
            String role="admin";

            String dId = FirebaseInstanceId.getInstance().getToken();




            if (!validateSalesphone()||!validateSalespass()||!validateSalesname()||!validateSalesemail()) {

                Toast.makeText(addSalesEx.this, "Fill all details", Toast.LENGTH_SHORT).show();
            } else {


                signup(euser1, eemail, emob, eaddr,earea,epass,dId,role);
                SharedPreferences preferences = getSharedPreferences("Mypref", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.putString("fname", euser1);
                editor.putString("email", eemail);
                editor.putString("mobile", emob);
                editor.putString("address", eaddr);
                editor.putString("password", epass);
                editor.putString("Area", earea);
                editor.putString("device_id",dId);
                editor.putString("role",role);
                Log.i("Maivannan",""+dId);

                editor.commit();


            }

        }

    });
}
       public void openDialog(){
        areadialog areadialog = new areadialog();
        areadialog.show(getSupportFragmentManager(),"area");
    }






    public void signup(final String fname, final String email,final String mobile, final String address,final String Area ,final String password,final String dID,final String role){
        SharedPreferences LINK = getSharedPreferences("MAIN_LINK",0);
        String link1 = LINK.getString("MAIN_LINK","");
        Log.i("maivannan", "" + link1);
        String URL_add_sales_officer = link1+"add_sales_officer.php";
        Log.i("maivannan", "" + URL_add_sales_officer);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adding ");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(addSalesEx.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_add_sales_officer, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("Maivannan",""+response);
                progressDialog.dismiss();
                finish();











            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Maivannan",""+error);
                Toast.makeText(addSalesEx.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> stringMap = new HashMap<>();
                SharedPreferences manger_id = getSharedPreferences("manager_id",MODE_PRIVATE);
                String s_id = manger_id.getString("manager_id","");
                stringMap.put("name",fname);
                stringMap.put("phone",mobile);
                stringMap.put("address",address);
                stringMap.put("userEmail",email);
                stringMap.put("userPass",password);
                stringMap.put("device_id",dID);
                stringMap.put("role",role);
                stringMap.put("a_id",Area);
                stringMap.put("s_id",s_id);
                Log.i("maivannan",""+stringMap.toString());

                return stringMap;
            }
        };

        requestQueue.add(stringRequest);

    }





















    private boolean validateSalesname()
        {
            String Name = exName.getText().toString().trim();
            if (Name.isEmpty())
            {
                exName_label.setError("Name Field can't be empty");
                return false;
            }
            else
            {
                exName_label.setError(null);
                return true;
            }
        }

    private boolean validateSalesemail()
    {
        String Email = exMail.getText().toString().trim();

        if (Email.isEmpty())
        {
            exEmail_label.setError("Email Field can't be empty");
            return false;
        }
        else
        {
            exEmail_label.setError(null);
            return true;
        }
    }
    private boolean validateSalesphone()
    {
        String phone = exPhone.getText().toString().trim();

        if (phone.isEmpty())
        {
            exPh_label.setError("phone number Field can't be empty");
            return false;
        }
        else
        {
            exPh_label.setError(null);
            return true;
        }
    }
    private boolean validateSalespass() {
        String pass = exPasw.getText().toString().trim();


        if (pass.isEmpty()) {
            exPass_label.setError("password Field can't be empty");
            return false;
        } else {
            exPass_label.setError(null);
            return true;
        }
    }







    public AlertDialog.Builder buildDialog1(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("ERROR");
        builder.setMessage("Enter Proper name");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();


            }
        });

        return builder;
    }

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
            Toast.makeText(addSalesEx.this,accountName,Toast.LENGTH_SHORT).show();
            exMail.setText(accountName);
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
    @Override
    public void applyTexts(String area,String a_id) {
        select_area2.setText(area);
        employeename.setText(a_id);

    }


}











