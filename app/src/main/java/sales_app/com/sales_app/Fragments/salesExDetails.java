package sales_app.com.sales_app.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

import sales_app.com.sales_app.Activities.EditCustomer;
import sales_app.com.sales_app.Activities.EditSalesOfficer;
import sales_app.com.sales_app.Activities.customerDetails;
import sales_app.com.sales_app.R;

public class salesExDetails extends AppCompatActivity{


    Button salesEditBtn,delButton;
    String getID,getName,getEmail,getPhone,getArea,getPassword,getAddress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.salesexdetails);
        TextView salesExName = findViewById(R.id.namesales);
        TextView salesExEmail = findViewById(R.id.EmailIdSales);
        TextView salesExPhone = findViewById(R.id.phonenumSales);
        TextView salesExArea = findViewById(R.id.AreaSales);
        TextView salesExAddress = findViewById(R.id.addressSales);

        TextView salesExpassword = findViewById(R.id.passwordSales);
        salesEditBtn=findViewById(R.id.salesEdit);
        delButton=findViewById(R.id.salesDelete);



        Intent toy6002 = getIntent();
        Bundle bd = toy6002.getExtras();
        if(bd != null)
        {
             getID =(String)bd.get("ExID") ;
             getName = (String) bd.get("ExName");
             getEmail = (String) bd.get("ExEmail");
             getPhone = (String) bd.get("ExPhone");
             getArea = (String) bd.get("ExArea");
             getAddress=(String)bd.get("ExAddress");
             getPassword = (String) bd.get("EXPassword");



            salesExName.setText(getName);
            salesExEmail.setText(getEmail);
            salesExPhone.setText(getPhone);
            salesExAddress.setText(getAddress);
            salesExpassword.setText(getPassword);


        }
        salesEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(salesExDetails.this, EditSalesOfficer.class);
                intent.putExtra("so_id",getID);
                intent.putExtra("so_Name",getName);
                intent.putExtra("so_Email",getEmail);
                intent.putExtra("so_Phone",getPhone);
                intent.putExtra("so_Address",getAddress);
                intent.putExtra("so_password",getPassword);


                startActivity(intent);
                finish();


            }
        });
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences LINK = getSharedPreferences("MAIN_LINK",0);
                String link1 = LINK.getString("MAIN_LINK","");
                Log.i("maivannan", "" + link1);
                String URLSD = link1+"delete_sales_officer.php";
                Log.i("maivannan", "" + URLSD);



                final StringRequest stringRequest = new StringRequest(Request.Method.POST, URLSD, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Hitesh",""+response);



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

                        stringMap.put("s_id",getID);
                        Log.i("maivannan",""+getID);



                        return stringMap;
                    }
                };



                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
                finish();



            }




        });





    }
}
