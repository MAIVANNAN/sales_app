package sales_app.com.sales_app.Fragments;

/**
 * Created by hp on 29-05-2018.
 */


import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import java.util.Objects;

import sales_app.com.sales_app.Activities.addSalesEx;
import sales_app.com.sales_app.R;
import sales_app.com.sales_app.adapters.customerAdapter;
import sales_app.com.sales_app.adapters.salesAdapter;
import sales_app.com.sales_app.models.RecyclerTouchListener;
import sales_app.com.sales_app.models.salesOfficers;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Belal on 18/09/16.
 */


public class Menu1 extends Fragment  {
    private List<salesOfficers> salesOfficersList ;
    private RecyclerView recyclerView1;
    private salesAdapter mAdapter;
    FloatingActionButton fab;
    //String URL_fetch_data="http://6f46f287.ngrok.io/php_login/sales_officer_list.php";
    String c_name,c_id;
    TextView add_officer;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.fragment_menu_1, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles

        Objects.requireNonNull(getActivity()).setTitle("sales Executive details ");
        salesOfficersList = new ArrayList<>();
        //you can set the title for your toolbar here for different fragments different titles
        recyclerView1 =   view.findViewById(R.id.salesExRecycle);
        add_officer = view.findViewById(R.id.createsalesEx);













        add_officer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), addSalesEx.class);

                startActivity(intent);

            }
        });



        recyclerView1.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView1, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent toy6001 = new Intent(getActivity().getApplicationContext(),salesExDetails.class);
                toy6001.putExtra("ExID",salesOfficersList.get(position).getE_id());
                toy6001.putExtra("ExName",salesOfficersList.get(position).getEName());
                toy6001.putExtra("ExEmail",salesOfficersList.get(position).getEmail_id());
                toy6001.putExtra("ExPhone",salesOfficersList.get(position).getEPhonenum());
                toy6001.putExtra("ExAddress",salesOfficersList.get(position).getAddress());
                toy6001.putExtra("ExArea",salesOfficersList.get(position).getAreasales());
                toy6001.putExtra("EXPassword",salesOfficersList.get(position).getPasswordsales());

                startActivity(toy6001);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }





    @Override
    public void onResume() {
        super.onResume();
        loadRecyclerViewData();

    }
    private void loadRecyclerViewData(){


        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        progressDialog.show();
        SharedPreferences LINK = getActivity().getSharedPreferences("MAIN_LINK",0);
        String link1 = LINK.getString("MAIN_LINK","");
        Log.i("maivannan", "" + link1);

        String URLM1 = link1+"sales_officer_list.php";
        Log.i("maivannan", "" + URLM1);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLM1, new Response.Listener<String>() {

            public void onResponse(String response) {
                Log.i("Hitesh",""+response);
                progressDialog.dismiss();

                try {
                    JSONObject object = new JSONObject(response);
                    JSONObject jsonObj = new JSONObject(object.getString("sales_officers_list_response"));
                    if(jsonObj.getString("errorcode").equals("47000")) {
                        JSONArray array1 = jsonObj.getJSONArray("sales_officers_list");


                        salesOfficersList.clear();
                        Log.i("maivannan outer array", "" + array1);

                        for (int i = 0; i < array1.length(); i++) {

                            JSONArray jsonArraysubject;
                            JSONObject jsonObj2;
                            JSONObject o = array1.getJSONObject(i);


                            String s_id = o.getString("so_id");
                            String so_name = o.getString("so_name");
                            String so_address = o.getString("so_address");
                            String so_email = o.getString("so_email");
                            String so_phone = o.getString("so_phone");

                            jsonObj2 = array1.getJSONObject(i);

                            jsonArraysubject = jsonObj2.getJSONArray("sales_officers_customer_list");

                            for(int j=0 ;j<jsonArraysubject.length();j++) {
                                JSONObject o1 = jsonArraysubject.getJSONObject(j);

                                c_name = o1.getString("c_name");
                                c_id = o1.getString("c_id");
                            }
                            Log.i(" ",c_name);




                            salesOfficers item = new salesOfficers(s_id, so_name, so_phone,so_address,so_email, c_id,c_name);
                            salesOfficersList.add(item);
                        }
                    }
                    else {
                        Toast.makeText(getActivity().getApplicationContext(),"NO SALES OFFICERS",Toast.LENGTH_LONG).show();

                    }

                    mAdapter = new salesAdapter(salesOfficersList,getActivity().getApplicationContext());
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    recyclerView1.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                    recyclerView1.setHasFixedSize(true);
                    recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                    recyclerView1.setLayoutManager(mLayoutManager);
                    recyclerView1.setItemAnimator(new DefaultItemAnimator());
                    recyclerView1.setAdapter(mAdapter);












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

                SharedPreferences manger_id = getActivity().getSharedPreferences("manager_id",0);
                String s_id = manger_id.getString("manager_id","");
                stringMap.put("s_id",s_id);
                Log.i("sales list input",stringMap.toString());

                return stringMap;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);



    }



}
