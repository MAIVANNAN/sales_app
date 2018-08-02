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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import sales_app.com.sales_app.Activities.MainActivity;
import sales_app.com.sales_app.Activities.todaysReport2;
import sales_app.com.sales_app.R;
import sales_app.com.sales_app.adapters.salesAdapter;
import sales_app.com.sales_app.adapters.todaysReportAdapter;
import sales_app.com.sales_app.models.RecyclerTouchListener;
import sales_app.com.sales_app.models.salesOfficers;
import sales_app.com.sales_app.Fragments.Menu1;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Belal on 18/09/16.
 */


public class Menu4 extends Fragment {
    private List<salesOfficers> salesOfficersList ;
    private RecyclerView recyclerView1;
    private todaysReportAdapter maAdapter;
    FloatingActionButton fab;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_menu_4, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Today's Report ");

        salesOfficersList = new ArrayList<>();
        //you can set the title for your toolbar here for different fragments different titles
        recyclerView1 =   view.findViewById(R.id.Managernames);

        loadRecyclerViewData();









        recyclerView1.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView1, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent toy6001 = new Intent(getActivity(),salesExDetails.class);

                startActivity(toy6001);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));







    }

    private void loadRecyclerViewData(){



        SharedPreferences LINK = getActivity().getSharedPreferences("MAIN_LINK",0);
        String link1 = LINK.getString("MAIN_LINK","");
        Log.i("maivannan", "" + link1);
        String URLM4 = link1+"sales_officer_list.php";
        Log.i("maivannan", "" + URLM4);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLM4, new Response.Listener<String>() {

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
                        String c_id = o.getString("c_id");





                        salesOfficers item = new salesOfficers(s_id,so_name,so_phone,c_id,so_email,so_address,c_name);
                        Log.i("item",""+item.getE_id()+ " "+item.getEPhonenum()+" "+item.getEmail_id()+" "+item.getAreasales()+""+item.getEName());
                        salesOfficersList.add(item);

                    }
                    maAdapter = new todaysReportAdapter(salesOfficersList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    recyclerView1.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                    recyclerView1.setHasFixedSize(true);
                    recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
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



}