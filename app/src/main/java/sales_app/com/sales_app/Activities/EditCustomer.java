package sales_app.com.sales_app.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sales_app.com.sales_app.R;
import sales_app.com.sales_app.areadialog;
import sales_app.com.sales_app.models.Area;

public class EditCustomer extends AppCompatActivity implements areadialog.ExampleDialogListener {


    private Spinner spinner;
    private List<Area> areaList ;

    private ArrayList<String> arrayList;


    TextInputEditText custname,custmail,custphone,custaddr,custGST;
    TextInputLayout sname_label,em_label,pho_label,add_label,gst_label;

    Button custEditBtn,delButton;
    private static final int REQUEST_CODE_EMAIL =7;


    MainActivity globalvariable;

    private String TAG = "EditCustomerActivity";
    private String wantPermission = Manifest.permission.GET_ACCOUNTS;
    private Activity activity = EditCustomer.this;
    private static final int PERMISSION_REQUEST_CODE = 1;

    String getName,getEmail,getPhone,getAddress,getArea,getC_id,getGST;


    public static final String JSON_ARRAY = "area_list";
    private JSONArray area;
    String a_id;


    TextView employeename;
    TextView textViewarea1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);
        sname_label=(TextInputLayout)findViewById(R.id.sname_label);
        em_label=(TextInputLayout)findViewById(R.id.em_label);
        pho_label=(TextInputLayout)findViewById(R.id.pho_label);
        add_label=(TextInputLayout)findViewById(R.id.add_label);
        custname=(TextInputEditText)findViewById(R.id.salname);
        custmail=(TextInputEditText)findViewById(R.id.salemail);
        custphone=(TextInputEditText)findViewById(R.id.phone);
        custaddr=(TextInputEditText)findViewById(R.id.address);
        custEditBtn =(Button)findViewById(R.id.edit_cutomer);
        gst_label=(TextInputLayout)findViewById(R.id.gst_label);
        custGST = findViewById(R.id.gst);

        Intent toy6001 = getIntent();
        Bundle bd = toy6001.getExtras();
        if(bd != null)
        {


            getName = (String) bd.get("custIName");
            getEmail = (String) bd.get("custIEmail");
            getPhone = (String) bd.get("custIPhone");
            getAddress = (String) bd.get("custIAddress");
            getArea = (String) bd.get("custIPassword");
            getC_id=(String)bd.get("custId") ;
            getGST = (String)bd.get("custGST");

            custname.setText(getName);
            custmail.setText(getEmail);
            custphone.setText(getPhone);
            custaddr.setText(getAddress);
            custGST.setText(getGST);





        }
        employeename= (TextView) findViewById(R.id.area_id_cust);

        textViewarea1 =(TextView)findViewById(R.id.textViewarea);
        textViewarea1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });







        custEditBtn.setOnClickListener(new View.OnClickListener() {

            //String gender = radioGenderButton.getText().toString();


            @Override
            public void onClick(View v) {
                String cemail = custmail.getText().toString();
                String cuser1 = custname.getText().toString();
                String cmob = custphone.getText().toString();
                String caddr =custaddr.getText().toString();
                String c_aid= employeename.getText().toString() ;
                String c_id=getC_id;
                String c_gst = custGST.getText().toString();




                if (!validateSalesname() || !validateSalesemail() || !validateSalesadress() || !validateSalesphone() ||!validateSalesGST() ) {

                    Toast.makeText(EditCustomer.this, "Fill all details", Toast.LENGTH_SHORT).show();
                } else {


                    edit(cuser1, cmob, caddr, cemail, c_aid,c_id,c_gst);
                    SharedPreferences preferences = getSharedPreferences("Mypref", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.putString("name", cuser1);
                    editor.putString("userEmail", cemail);
                    editor.putString("phone", cmob);
                    editor.putString("address", caddr);
                    editor.putString("a_id", c_aid);
                    editor.putString("c_id", c_id);
                    editor.putString("c_gst", c_gst);


                    editor.commit();


                }

            }

        });
    }





    public void openDialog(){
        areadialog areadialog = new areadialog();
        areadialog.show(getSupportFragmentManager(),"area");
    }




    public void edit(final String fname, final String mobile, final String address, final String email , final String c_aid, final String c_id,final String c_gst){



        SharedPreferences LINK = getSharedPreferences("MAIN_LINK",0);
        String link1 = LINK.getString("MAIN_LINK","");
        Log.i("maivannan", "" + link1);
        String URL_edit_custo = link1+"edit_customer.php";
        Log.i("maivannan", "" + URL_edit_custo);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adding ");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(EditCustomer.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_edit_custo, new Response.Listener<String>() {
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
                Toast.makeText(EditCustomer.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> stringMap = new HashMap<>();
                stringMap.put("userEmail",email);
                stringMap.put("name",fname);
                stringMap.put("address",address);
                stringMap.put("phone",mobile);
                stringMap.put("a_id",c_aid);
                stringMap.put("c_id",c_id);
                stringMap.put("gstnum",c_gst);








                Log.i("maivannan",""+stringMap.toString());

                return stringMap;
            }

        };

        requestQueue.add(stringRequest);

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
        textViewarea1.setText(area);
        employeename.setText(a_id);

    }






}

