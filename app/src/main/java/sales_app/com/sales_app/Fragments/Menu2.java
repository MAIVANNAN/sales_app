package sales_app.com.sales_app.Fragments;

/**
 * Created by hp on 29-05-2018.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
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


import sales_app.com.sales_app.Activities.addCustomers;
import sales_app.com.sales_app.Activities.customerDetails;
import sales_app.com.sales_app.models.Customer;
import sales_app.com.sales_app.R;
import sales_app.com.sales_app.adapters.customerAdapter;
import sales_app.com.sales_app.models.RecyclerTouchListener;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Belal on 18/09/16.
 */


public class Menu2 extends Fragment implements SearchView.OnQueryTextListener {
    private List<Customer> customerList ;
    private RecyclerView recyclerView2;
    private customerAdapter mAdapter;
    public static final String PREFS_NAME1 = "manager_id";
    Toolbar toolbar ;
    TextView add_customer;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments


        return inflater.inflate(R.layout.fragment_menu_2, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Customer details");
        //you can set the title for your toolbar here for different fragments different titles
        toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        customerList = new ArrayList<>();
        recyclerView2 =  (RecyclerView) view.findViewById(R.id.customerNames);
        add_customer = view.findViewById(R.id.createCust);





        add_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), addCustomers.class);

                startActivity(intent);

            }
        });

        recyclerView2.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView2, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent toy6002 = new Intent(getActivity().getApplicationContext(),customerDetails.class);
                toy6002.putExtra("custId",customerList.get(position).getC_id());
                toy6002.putExtra("custIName",customerList.get(position).getName());
                toy6002.putExtra("custIEmail",customerList.get(position).getEmail());
                toy6002.putExtra("custIPhone",customerList.get(position).getPhonenum());
                toy6002.putExtra("custIAddress",customerList.get(position).getAddress());
                toy6002.putExtra("custIArea",customerList.get(position).getArea());
                toy6002.putExtra("custGST",customerList.get(position).getGST_number());
                startActivity(toy6002);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));











    }


    @Override
    public void onResume() {
        super.onResume();
        loadRecyclerViewData2();

    }
    private void loadRecyclerViewData2(){

        SharedPreferences LINK = getActivity().getSharedPreferences("MAIN_LINK",0);
        String link1 = LINK.getString("MAIN_LINK","");
        Log.i("maivannan", "" + link1);
        String URLM2 = link1+"customer_list.php";
        Log.i("maivannan", "" + URLM2);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        progressDialog.show();



       final StringRequest stringRequest = new StringRequest(Request.Method.POST,URLM2 , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Hitesh",""+response);
                progressDialog.dismiss();

                try {
                    JSONObject object = new JSONObject(response);
                    JSONObject jsonObj = new JSONObject(object.getString("customer_list_response"));
                    if(jsonObj.getString("errorcode").equals("47000")) {

                        JSONArray array = jsonObj.getJSONArray("customer_list");
                        customerList.clear();

                        for (int i = 0; i < array.length(); i++) {
                            Log.i("count", "" + array.length());

                            JSONObject o1 = array.getJSONObject(i);


                            String c_id = o1.getString("c_id");
                            String c_name = o1.getString("c_name");
                            String c_address = o1.getString("c_address");
                            String c_phone = o1.getString("c_phone");
                            String c_email = o1.getString("c_email");
                            String a_id = o1.getString("a_id");
                            String area_name = o1.getString("area_name");
                            String GST = o1.getString("GST_number");



                            Customer item = new Customer(c_id, c_name, c_address, c_phone, c_email, a_id, area_name,GST);
                            customerList.add(item);

                        }
                    }
                    else {
                        Toast.makeText(getActivity().getApplicationContext(),"NO CUSTOMER",Toast.LENGTH_LONG).show();

                    }
                    mAdapter = new customerAdapter(customerList,getActivity().getApplicationContext());
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    recyclerView2.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                    recyclerView2.setHasFixedSize(true);
                    recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                    recyclerView2.setLayoutManager(mLayoutManager);
                    recyclerView2.setItemAnimator(new DefaultItemAnimator());
                    recyclerView2.setAdapter(mAdapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity().getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                }){
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               Map<String,String> stringMap = new HashMap<>();
               SharedPreferences manger_id = getActivity().getSharedPreferences("manager_id",MODE_PRIVATE);
               String s_id = manger_id.getString("manager_id","");
               stringMap.put("s_id",s_id);


               return stringMap;
           }
       };



        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);



    }
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<Customer> newList = new ArrayList<>();
        for (Customer customer :customerList)
        {
            String name = customer.getName().toLowerCase();
            if(name.contains(newText))
                newList.add(customer);
        }
        mAdapter.setFilter(newList);
        return true;
    }






}
