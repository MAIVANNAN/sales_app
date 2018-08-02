package sales_app.com.sales_app.Activities;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sales_app.com.sales_app.R;
import sales_app.com.sales_app.adapters.areaAdapter;
import sales_app.com.sales_app.models.Area;

public class UnAvailablegoods extends AppCompatActivity {
    private List<Area> areaList ;
    private RecyclerView recyclerView6;
    private areaAdapter maAdapter;
    String URL_fetch_data="http://6f46f287.ngrok.io/php_login/area_list.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availablegoods);
        areaList = new ArrayList<>();



        init();
    }
    private void loadRecyclerViewData(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_fetch_data, new Response.Listener<String>() {

            public void onResponse(String response) {
                Log.i("Hitesh",""+response);



                try {


                    JSONObject object = new JSONObject(response);
                    JSONObject object1 = object.getJSONObject("area_list_response");
                    Log.i("maivannan",""+object1);
                    JSONObject jsonObj = new JSONObject(object.getString("area_list_response"));
                    if(jsonObj.getString("errorcode").equals("47000")) {

                        JSONArray area = jsonObj.getJSONArray("area_list");

                        for (int i = 0; i < area.length(); i++) {


                            JSONObject o = area.getJSONObject(i);

                            String area_name = o.getString("area_name");
                            String area_id = o.getString("a_id");


                            Area item = new Area(area_name, area_id);
                            areaList.add(item);

                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"NO GOODS UNAVAILABLE",Toast.LENGTH_LONG).show();

                    }











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

                SharedPreferences manger_id = getSharedPreferences("manager_id",MODE_PRIVATE);
                String s_id = manger_id.getString("manager_id","");
                stringMap.put("s_id",s_id);



                return stringMap;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);



    }







    public void init()
    {
        TableLayout stk1=(TableLayout)findViewById(R.id.table_main1);
        TableRow tb1=new TableRow(this);
        TextView t=new TextView(this);
        t.setText("slno");
        t.setTextColor(Color.BLACK);
        t.setPadding(20,20,20,20);
        tb1.addView(t);
        TextView t1=new TextView(this);
        t1.setText("Goodsname");
        t1.setTextColor(Color.BLACK);
        t1.setPadding(20,20,20,20);
        tb1.addView(t1);
        TextView t2=new TextView(this);
        t2.setText("Goodsquantity");
        t2.setTextColor(Color.BLACK);
        t1.setPadding(20,20,20,20);
        tb1.addView(t2);
        stk1.addView(tb1);
        for (int i=0;i<35;i++)
        {
            TableRow tb2=new TableRow(this);
            TextView t20=new TextView(this);
            t20.setText("" +i);
            t20.setTextColor(Color.BLACK);
            t20.setPadding(20,20,20,20);
            t20.setGravity(Gravity.CENTER);
            tb2.addView(t20);
            TextView t3=new TextView(this);
            t3.setText("Parleg" +i);
            t3.setTextColor(Color.BLACK);
            t3.setPadding(20,20,20,20);
            t3.setGravity(Gravity.CENTER);
            tb2.addView(t3);
            TextView t4=new TextView(this);
            t4.setText("25" +i);
            t4.setTextColor(Color.BLACK);
            t4.setPadding(20,20,20,20);
            t4.setGravity(Gravity.CENTER);
            tb2.addView(t4);
            stk1.addView(tb2);
        }
    }
}
