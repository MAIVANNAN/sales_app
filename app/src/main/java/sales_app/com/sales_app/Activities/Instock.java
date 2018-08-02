package sales_app.com.sales_app.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
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

public class Instock extends AppCompatActivity {
    String Gname,Pri,Sales;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instock);
        loadRecyclerViewData2();


    }


    private void loadRecyclerViewData2(){

        SharedPreferences LINK = getSharedPreferences("MAIN_LINK",0);
        String link1 = LINK.getString("MAIN_LINK","");
        Log.i("maivannan", "" + link1);
        String URLM2 = link1+"instock_product.php";
        Log.i("maivannan", "" + URLM2);
        final ProgressDialog progressDialog = new ProgressDialog(Instock.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        final TableLayout stk = (TableLayout) findViewById(R.id.table_main2);
        TableRow tb = new TableRow(this);
        TextView tv = new TextView(this);
        tv.setText("sl_no");
        tv.setTextColor(Color.BLACK);
        tv.setPadding(10, 10, 10, 10);
        tb.addView(tv);
        TextView tv1 = new TextView(this);
        tv1.setText("p_id");
        tv1.setTextColor(Color.BLACK);
        tv1.setPadding(10, 10, 10, 10);
        tb.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText("Productname");
        tv2.setTextColor(Color.BLACK);
        tv2.setPadding(10, 10, 10, 10);
        tb.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText("Quantity");
        tv3.setTextColor(Color.BLACK);
        tv3.setPadding(10, 10, 10, 10);
        tb.addView(tv3);
        TextView tv4 = new TextView(this);
        tv4.setText("Price");
        tv4.setTextColor(Color.BLACK);
        tv4.setPadding(10, 10, 10, 10);
        tb.addView(tv4);
        stk.addView(tb);




        final StringRequest stringRequest = new StringRequest(Request.Method.POST,URLM2 , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Hitesh",""+response);
                progressDialog.dismiss();

                try {
                    JSONObject object = new JSONObject(response);
                    JSONObject jsonObj = new JSONObject(object.getString("instock_product_response"));
                    if(jsonObj.getString("errorcode").equals("47000")) {

                        JSONArray array = jsonObj.getJSONArray("area_list");

                        for (int i = 0; i < array.length(); i++) {
                            Log.i("count", "" + array.length());

                            JSONObject o1 = array.getJSONObject(i);

                            String p_id = o1.getString("p_id");
                            String p_name = o1.getString("p_name");
                            String price = o1.getString("price");
                            String no_of_stock = o1.getString("no_of_stock");
                            int sl_no=i+1;
                            init1(p_id,p_name,price,no_of_stock,sl_no,stk);






                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"NO CUSTOMER",Toast.LENGTH_LONG).show();

                    }






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
                SharedPreferences manger_id = getSharedPreferences("manager_id",MODE_PRIVATE);
                String s_id = manger_id.getString("manager_id","");
                stringMap.put("s_id",s_id);


                return stringMap;
            }
        };



        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);



    }

    public void init1(final String p_id,final String p_name,final String price ,final String no_of_stock ,final int sl_no,final TableLayout stk )
    {



            TableRow tbrow1 = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText("" + sl_no);
            t1v.setPadding(10, 10, 10, 10);
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.CENTER);
            tbrow1.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText("" + p_id);
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.CENTER);
            tbrow1.addView(t2v);
            TextView t3v = new TextView(this);
            t3v.setText("" + p_name);
            t3v.setTextColor(Color.BLACK);
            t3v.setGravity(Gravity.CENTER);
            tbrow1.addView(t3v);
            TextView t4v = new TextView(this);
            t4v.setText("" +no_of_stock);
            t4v.setTextColor(Color.BLACK);
            t4v.setGravity(Gravity.CENTER);
            tbrow1.addView(t4v);
            TextView t5v = new TextView(this);
            t5v.setText("Rs" + price);
            t5v.setTextColor(Color.BLACK);
            t5v.setGravity(Gravity.CENTER);
            tbrow1.addView(t5v);
            stk.addView(tbrow1);



    }

}

