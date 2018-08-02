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
import sales_app.com.sales_app.models.Area;

public class EditCustomer extends AppCompatActivity {


    private Spinner spinner;
    private List<Area> areaList ;

    private ArrayList<String> arrayList;


    TextInputEditText custname,custmail,custphone,custaddr;
    TextInputLayout sname_label,em_label,pho_label,add_label;

    Button custEditBtn,delButton;
    private static final int REQUEST_CODE_EMAIL =7;
    String url_Edit_customer="http://6f46f287.ngrok.io/php_login/edit_customer.php";
    private static final String URL_fetch_data="http://6f46f287.ngrok.io/php_login/area_list.php";

    MainActivity globalvariable;

    private String TAG = "EditCustomerActivity";
    private String wantPermission = Manifest.permission.GET_ACCOUNTS;
    private Activity activity = EditCustomer.this;
    private static final int PERMISSION_REQUEST_CODE = 1;

    String getName,getEmail,getPhone,getAddress,getArea,getC_id;


    public static final String JSON_ARRAY = "area_list";
    private JSONArray area;
    String a_id;


    TextView employeename;

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

            custname.setText(getName);
            custmail.setText(getEmail);
            custphone.setText(getPhone);
            custaddr.setText(getAddress);





        }
        employeename= (TextView) findViewById(R.id.area_id_cust);

        spinner= (Spinner) findViewById(R.id.CustArea);
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







        custEditBtn.setOnClickListener(new View.OnClickListener() {

            //String gender = radioGenderButton.getText().toString();


            @Override
            public void onClick(View v) {
                String cemail = custmail.getText().toString();
                String cuser1 = custname.getText().toString();
                String cmob = custphone.getText().toString();
                String caddr =custaddr.getText().toString();
                String c_aid=a_id ;
                String c_id=getC_id;




                if (!validateSalesname() || !validateSalesemail() || !validateSalesadress() || !validateSalesphone() ) {

                    Toast.makeText(EditCustomer.this, "Fill all details", Toast.LENGTH_SHORT).show();
                } else {


                    edit(cuser1, cmob, caddr, cemail, c_aid,c_id);
                    SharedPreferences preferences = getSharedPreferences("Mypref", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.putString("name", cuser1);
                    editor.putString("userEmail", cemail);
                    editor.putString("phone", cmob);
                    editor.putString("address", caddr);
                    editor.putString("a_id", c_aid);
                    editor.putString("c_id", c_id);

                    editor.commit();


                }

            }

        });
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
        spinner.setAdapter(new ArrayAdapter<String>(EditCustomer.this, android.R.layout.simple_spinner_dropdown_item, arrayList));
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










    public void edit(final String fname, final String mobile, final String address, final String email , final String c_aid, final String c_id){



        SharedPreferences LINK = getSharedPreferences("MAIN_LINK",0);
        String link1 = LINK.getString("MAIN_LINK","");
        Log.i("maivannan", "" + link1);
        String URL_edit_cust = link1+"edit_customer.php";
        Log.i("maivannan", "" + URL_edit_cust);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adding ");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(EditCustomer.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_edit_cust, new Response.Listener<String>() {
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







}

