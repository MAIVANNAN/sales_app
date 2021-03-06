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
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class addCustomers extends AppCompatActivity implements areadialog.ExampleDialogListener {
    private Spinner spinner;
    private List<Area> areaList ;

    private ArrayList<String> arrayList;



    private static final int REQUEST_CODE_EMAIL =7;


    private TextInputEditText custname,custmail,custphone,custaddr,custGST;
    TextInputLayout sname_label,em_label,pho_label,add_label,gst_label;
    Button addbtn;
    public static final String JSON_ARRAY = "area_list";
    private JSONArray area;
    String a_id;



    private String TAG = "addCusotmerActivityTAG";
    private String wantPermission = Manifest.permission.GET_ACCOUNTS;
    private Activity activity = addCustomers.this;
    private static final int PERMISSION_REQUEST_CODE = 1;

    TextView employeename;
    TextView select_area1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customers);
        sname_label=(TextInputLayout)findViewById(R.id.sname_label);
        em_label=(TextInputLayout)findViewById(R.id.em_label);
        pho_label=(TextInputLayout)findViewById(R.id.pho_label);
        add_label=(TextInputLayout)findViewById(R.id.add_label);
        gst_label=(TextInputLayout)findViewById(R.id.gst_label);

        custname=(TextInputEditText)findViewById(R.id.salname);
        custmail=(TextInputEditText)findViewById(R.id.salemail);
        custphone=(TextInputEditText)findViewById(R.id.phone);
        custaddr=(TextInputEditText)findViewById(R.id.address);
        custGST =(TextInputEditText)findViewById(R.id.gst);
        addbtn =(Button)findViewById(R.id.add_customer);
        employeename= (TextView) findViewById(R.id.area_id_cust);
        select_area1 =(TextView)findViewById(R.id.textView5);
        select_area1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

       /* spinner= (Spinner) findViewById(R.id.CustArea);
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
        });*/










        addbtn.setOnClickListener(new View.OnClickListener() {

            //String gender = radioGenderButton.getText().toString();


            @Override
            public void onClick(View v) {
                String cemail = custmail.getText().toString();
                String cuser1 = custname.getText().toString();
                String cmob = custphone.getText().toString();
                String caddr =custaddr.getText().toString();
                String c_aid = employeename.getText().toString();
                String crea_by="manager";
                String crea_id;
                String c_gst = custGST.getText().toString();
                String dId = FirebaseInstanceId.getInstance().getToken();




                if (!validateSalesname() || !validateSalesemail() || !validateSalesadress() || !validateSalesphone()||!validateSalesGST() ) {

                } else {


                    signup(cuser1, cmob, caddr, cemail, c_aid, crea_by,c_gst);
                    SharedPreferences preferences = getSharedPreferences("Mypref", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.putString("fname", cuser1);
                    editor.putString("email", cemail);
                    editor.putString("mobile", cmob);
                    editor.putString("address", caddr);
                    editor.putString("c_aid", c_aid);
                    editor.putString("c_gst", c_gst);

                    editor.putString("crea_by", crea_by);
                    Log.i("Hitesh",""+dId);

                    editor.commit();


                }

            }

        });
    }
    public void openDialog(){
        areadialog areadialog = new areadialog();
        areadialog.show(getSupportFragmentManager(),"area");
    }









    public void signup(final String fname, final String mobile, final String address, final String email , final String c_aid, final  String crea_by,final String c_gst){
        SharedPreferences LINK = getSharedPreferences("MAIN_LINK",0);
        String link1 = LINK.getString("MAIN_LINK","");
        Log.i("maivannan", "" + link1);
        String URL_add_customer = link1+"add_customer.php";
        Log.i("maivannan", "" + URL_add_customer);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adding ");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(addCustomers.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_add_customer, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("maivannan",""+response);
                progressDialog.dismiss();
                finish();











            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Hitesh",""+error);
                Toast.makeText(addCustomers.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> stringMap = new HashMap<>();
                SharedPreferences manger_id = getSharedPreferences("manager_id",MODE_PRIVATE);
                String crea_id = manger_id.getString("manager_id","");

                stringMap.put("userEmail",email);
                stringMap.put("name",fname);
                stringMap.put("address",address);
                stringMap.put("phone",mobile);
                stringMap.put("a_id",c_aid);
                stringMap.put("created_by",crea_by);
                stringMap.put("creator_id",crea_id);
                stringMap.put("gstnum",c_gst);



                Log.i("maivannan",""+stringMap.toString());

                return stringMap;
            }
        };

        requestQueue.add(stringRequest);

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
            Toast.makeText(addCustomers.this,accountName,Toast.LENGTH_SHORT).show();
            custmail.setText(accountName);
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




    private boolean validateSalesname()
    {
        String Salesname=custname.getText().toString().trim();
        if (Salesname.isEmpty())
        {
            sname_label.setError("Name Field can't be empty");
            return false;
        }
        else
        {
            sname_label.setError(null);
            return true;
        }
    }
    private boolean validateSalesemail()
    {
        String Salesemail=custmail.getText().toString().trim();
        if (Salesemail.isEmpty())
        {
            em_label.setError("Email Field can't be empty");
            return false;
        }
        else
        {
            em_label.setError(null);
            return true;
        }
    }
    private boolean validateSalesphone()
    {
        String Salesphone=custphone.getText().toString().trim();
        if (Salesphone.isEmpty())
        {
            pho_label.setError("Phone number Field can't be empty");
            return false;
        }
        else
        {
            pho_label.setError(null);
            return true;
        }
    }
    private boolean validateSalesadress()
    {
        String Salesaddress=custaddr.getText().toString().trim();
        if (Salesaddress.isEmpty())
        {
            add_label.setError(" address Field can't be empty");
            return false;
        }
        else
        {
            add_label.setError(null);
            return true;
        }
    }

    private boolean validateSalesGST()
    {
        String SalesGST=custGST.getText().toString().trim();
        if (SalesGST.isEmpty())
        {
            gst_label.setError(" address Field can't be empty");
            return false;
        }
        else
        {
            gst_label.setError(null);
            return true;
        }
    }


    @Override
    public void applyTexts(String area,String a_id) {
        select_area1.setText(area);
        employeename.setText(a_id);

    }
}
