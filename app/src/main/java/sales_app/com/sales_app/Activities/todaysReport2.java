package sales_app.com.sales_app.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sales_app.com.sales_app.Fragments.salesExDetails;
import sales_app.com.sales_app.R;
import sales_app.com.sales_app.adapters.salesAdapter;
import sales_app.com.sales_app.adapters.todaysReportAdapter;
import sales_app.com.sales_app.models.RecyclerTouchListener;
import sales_app.com.sales_app.models.salesOfficers;

public class todaysReport2 extends AppCompatActivity {
    private List<salesOfficers> salesOfficersList ;
    private RecyclerView recyclerView1;
    private todaysReportAdapter maAdapter;
    FloatingActionButton fab;
    String URL_fetch_data="http://6f46f287.ngrok.io/php_login/sales_officer_list.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_report2);

        salesOfficersList = new ArrayList<>();
        //you can set the title for your toolbar here for different fragments different titles
        recyclerView1 =   findViewById(R.id.Managernames);

        loadRecyclerViewData();









        recyclerView1.addOnItemTouchListener(new RecyclerTouchListener(todaysReport2.this, recyclerView1, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent toy6001 = new Intent(todaysReport2.this,salesExDetails.class);

                startActivity(toy6001);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));





    }


    private void loadRecyclerViewData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_fetch_data, new Response.Listener<String>() {

            public void onResponse(String response) {
                Log.i("Hitesh",""+response);



                progressDialog.dismiss();
                try {


                    JSONObject object = new JSONObject(response);
                    JSONObject object1 = object.getJSONObject("sales_officers_list_response");
                    Log.i("maivannan",""+object1);

                    JSONArray sales_officers  = object1.getJSONArray("area_list");


                    for (int i=0;i<sales_officers.length();i++)
                    {


                        JSONObject o = sales_officers.getJSONObject(i);

                        String s_id = o.getString("so_id");
                        String so_name=o.getString("so_name");
                        String so_address=o.getString("so_address");
                        String so_email=o.getString("so_email");
                        String so_phone =o.getString("so_phone");
                        String c_name =o.getString("c_name");
                        String c_id =o.getString("c_id");





                        salesOfficers item = new salesOfficers(s_id,so_name,so_phone,c_id,so_email,so_address,c_name);
                        Log.i("item",""+item.getE_id()+ " "+item.getEPhonenum()+" "+item.getEmail_id()+" "+item.getAreasales()+""+item.getEName());
                        salesOfficersList.add(item);

                    }
                    maAdapter = new todaysReportAdapter(salesOfficersList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView1.addItemDecoration(new DividerItemDecoration(todaysReport2.this, LinearLayoutManager.VERTICAL));
                    recyclerView1.setHasFixedSize(true);
                    recyclerView1.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView1.setLayoutManager(mLayoutManager);
                    recyclerView1.setItemAnimator(new DefaultItemAnimator());
                    recyclerView1.setAdapter(maAdapter);











                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> stringMap = new HashMap<>();
                stringMap.put("s_id","27");


                return stringMap;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);



    }


}

