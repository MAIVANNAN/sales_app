package sales_app.com.sales_app.Activities;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sales_app.com.sales_app.R;
import sales_app.com.sales_app.models.Area;

public class EditSalesOfficer extends AppCompatActivity {


    private Spinner spinner;
    private List<Area> areaList ;

    private ArrayList<String> arrayList;

    private static final int REQUEST_CODE_EMAIL =7;
    MainActivity globalvariable;

    private String TAG = "addSalesActivity";
    private String wantPermission = Manifest.permission.GET_ACCOUNTS;
    private Activity activity = EditSalesOfficer.this;
    private static final int PERMISSION_REQUEST_CODE = 1;

    private EditText exName, exMail, exPhone, exArea, exPasw,exAddr;
    private TextInputLayout exName_label,exEmail_label,exPh_label,exArea_label,exPass_label;
    protected Button EditSales;
    public static final String JSON_ARRAY = "area_list";
    private JSONArray area;
    String a_id;
    TextView employeename;



    String getName,getEmail,getPhone,getAddress,getPassword,getso_id,getArea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sales_officer);
        exName_label=findViewById(R.id.exName_label);
        exEmail_label=findViewById(R.id.exEmail_label);
        exPh_label=findViewById(R.id.exPh_label);
        exPass_label=findViewById(R.id.exPass_label);
        exName = findViewById(R.id.EtSalesExName);
        exMail = findViewById(R.id.EtSalesExEmail);
        exPhone = findViewById(R.id.EtSalesExPh);
        exAddr=findViewById(R.id.EtSalesExAddr);
        exPasw = findViewById(R.id.EtSalesExPass);


        spinner= (Spinner) findViewById(R.id.CustArea);

        Intent toy6001 = getIntent();
        Bundle bd = toy6001.getExtras();
        if(bd != null)
        {

            getso_id = (String) bd.get("so_id");

            getName = (String) bd.get("so_Name");
            getEmail = (String) bd.get("so_Email");
            getPhone = (String) bd.get("so_Phone");
            getAddress = (String) bd.get("so_Address");
            getPassword = (String) bd.get("so_password");
            getArea=(String) bd.get("so_Area");



            exName.setText(getName);
            exMail.setText(getEmail);
            exPhone.setText(getPhone);
            exPasw.setText(getPassword);
            exAddr.setText(getAddress);




        }

        EditSales = findViewById(R.id.btAddSalesEx);
        employeename= (TextView) findViewById(R.id.area_id_cust);
        arrayList = new ArrayList<String>();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Setting the values to textviews for a selected item
                employeename.setText(getemployeeName(position));
                a_id= (String) employeename.getText();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        getdata();
        EditSales.setOnClickListener(new View.OnClickListener() {

            //String gender = radioGenderButton.getText().toString();


            @Override
            public void onClick(View v) {
                String eemail = exMail.getText().toString();
                String euser1 = exName.getText().toString();
                String epass = exPasw.getText().toString();
                String eaddr = exAddr.getText().toString();
                String emob = exPhone.getText().toString();
                String earea = a_id;
                String M_Id ;
                String role="officer";
                String so_id = getso_id;

                String dId = FirebaseInstanceId.getInstance().getToken();




                if (!validateSalesphone()||!validateSalespass()||!validateSalesname()||!validateSalesemail()) {

                    Toast.makeText(EditSalesOfficer.this, "Fill all details", Toast.LENGTH_SHORT).show();
                } else {


                    signup(euser1, eemail, emob, eaddr,earea,epass,dId,role,so_id);
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
                    editor.putString("so_id",so_id);
                    Log.i("Maivannan",""+dId);

                    editor.commit();


                }

            }

        });


    }
    public void signup(final String fname, final String email,final String mobile, final String address,final String Area ,final String password,final String dID,final String role,final String so_id){
        SharedPreferences LINK = getSharedPreferences("MAIN_LINK",0);
        String link1 = LINK.getString("MAIN_LINK","");
        Log.i("maivannan", "" + link1);
        String URL_edit_sales_officer = link1+"edit_sales_officer.php";
        Log.i("maivannan", "" + URL_edit_sales_officer);






        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adding ");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(EditSalesOfficer.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_edit_sales_officer, new Response.Listener<String>() {
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
                Toast.makeText(EditSalesOfficer.this, ""+error, Toast.LENGTH_SHORT).show();

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
                stringMap.put("so_id",so_id);
                Log.i("maivannan",""+stringMap.toString());

                return stringMap;
            }
        };

        requestQueue.add(stringRequest);

    }
    private void getdata() {



        SharedPreferences LINK = getSharedPreferences("MAIN_LINK",0);
        String link1 = LINK.getString("MAIN_LINK","");
        Log.i("maivannan", "" + link1);
        String URL_area_list = link1+"area_list.php";
        Log.i("maivannan", "" + URL_area_list);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_area_list, new Response.Listener<String>() {

            public void onResponse(String response) {
                Log.i("Hitesh",""+response);



                try {


                    JSONObject object = new JSONObject(response);
                    JSONObject object1 = object.getJSONObject("area_list_response");
                    Log.i("maivannan",""+object1);

                    area  = object1.getJSONArray("area_list");









                    empdetails(area);


















                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();


                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> stringMap = new HashMap<>();

                SharedPreferences manger_id = getSharedPreferences("manager_id",0);
                String s_id = manger_id.getString("manager_id","");
                stringMap.put("s_id",s_id);



                return stringMap;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);



    }


    private void empdetails(JSONArray j) {
        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                arrayList.add(json.getString("area_name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // arrayList.add(0,"Select Employee");
        spinner.setAdapter(new ArrayAdapter<String>(EditSalesOfficer.this, android.R.layout.simple_spinner_dropdown_item, arrayList));
    }


    private String getemployeeName(int position){
        String name="";
        try {
            //Getting object of given index
            JSONObject json = area.getJSONObject(position);
            //Fetching name from that object
            name = json.getString("a_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return name;
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
            Toast.makeText(EditSalesOfficer.this,accountName,Toast.LENGTH_SHORT).show();
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


}













