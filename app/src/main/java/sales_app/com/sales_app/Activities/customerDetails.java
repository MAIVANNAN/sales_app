package sales_app.com.sales_app.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import sales_app.com.sales_app.R;
import sales_app.com.sales_app.adapters.customerAdapter;
import sales_app.com.sales_app.models.Customer;

public class customerDetails extends AppCompatActivity {
    private TextView custName,custEmail,custPhone,custAddress;
    TextView custArea1;
    Button custEditBtn,delButton;
    String getName,getEmail,getPhone,getAddress,getArea,getGST;
    String getC_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
         custName = findViewById(R.id.custName);
         custEmail = findViewById(R.id.E_mail);
         custPhone = findViewById(R.id.CustPhone);
         custAddress = findViewById(R.id.CustAddress);
         custArea1 =findViewById(R.id.CustArea);
         custEditBtn=findViewById(R.id.btnEditCust);
         delButton=findViewById(R.id.BtnDeleteCust);



        TextView custArea = findViewById(R.id.CustArea);

        Intent toy6001 = getIntent();
            Bundle bd = toy6001.getExtras();
            if(bd != null)
            {


                getName = (String) bd.get("custIName");
             getEmail = (String) bd.get("custIEmail");
             getPhone = (String) bd.get("custIPhone");
             getAddress = (String) bd.get("custIAddress");
             getArea = (String) bd.get("custIArea");
             getC_id=(String)bd.get("custId") ;
             getGST = (String)bd.get("custGST");



            custName.setText(getName);
            custEmail.setText(getEmail);
            custPhone.setText(getPhone);
            custArea1.setText(getArea);
            custAddress.setText(getAddress);



        }
        custEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(customerDetails.this, EditCustomer.class);
                intent.putExtra("custId",getC_id);
                intent.putExtra("custIName",getName);
                intent.putExtra("custIEmail",getEmail);
                intent.putExtra("custIPhone",getPhone);
                intent.putExtra("custIAddress",getAddress);
                intent.putExtra("custIArea",getArea);
                intent.putExtra("custGST",getGST);

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
                    String URL_delete_cust = link1+"delete_customer.php";
                    Log.i("maivannan", "" + URL_delete_cust);



                    final StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_delete_cust, new Response.Listener<String>() {
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

                            stringMap.put("c_id",getC_id);
                            Log.i("maivannan",""+getC_id);



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
