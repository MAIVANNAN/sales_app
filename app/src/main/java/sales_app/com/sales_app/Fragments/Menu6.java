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
import android.support.v4.widget.SwipeRefreshLayout;
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

import sales_app.com.sales_app.Activities.addArea;
import sales_app.com.sales_app.Activities.addSalesEx;
import sales_app.com.sales_app.Activities.areaDetails;
import sales_app.com.sales_app.adapters.salesAdapter;
import sales_app.com.sales_app.models.Area;
import sales_app.com.sales_app.models.RecyclerTouchListener;
import sales_app.com.sales_app.R;
import sales_app.com.sales_app.adapters.areaAdapter;
import sales_app.com.sales_app.models.salesOfficers;


import static android.content.Context.MODE_PRIVATE;
import static android.media.CamcorderProfile.get;

/**
 * Created by Belal on 18/09/16.
 */


public class Menu6 extends Fragment {
    private List<Area> areaList ;
    private RecyclerView recyclerView6;
    private areaAdapter maAdapter;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_menu_6, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Areas ");
        areaList = new ArrayList<>();

        recyclerView6 = (RecyclerView) view.findViewById(R.id.AreaInfo);




        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabArea);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), addArea.class);

                startActivity(intent);

            }
        });












        recyclerView6.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView6, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent toy6006 = new Intent(getActivity().getApplicationContext(),areaDetails.class);
                toy6006.putExtra("areaName",areaList.get(position).getAName());


                startActivity(toy6006);
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


        SharedPreferences LINK = getActivity().getSharedPreferences("MAIN_LINK",0);
        String link1 = LINK.getString("MAIN_LINK","");
        Log.i("maivannan", "" + link1);
        String URLM6 = link1+"area_list.php";
        Log.i("maivannan", "" + URLM6);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLM6, new Response.Listener<String>() {

            public void onResponse(String response) {
                Log.i("Hitesh",""+response);



                try {


                    JSONObject object = new JSONObject(response);

                    JSONObject jsonObj = new JSONObject(object.getString("area_list_response"));
                    if(jsonObj.getString("errorcode").equals("47000")) {

                        JSONArray area = jsonObj.getJSONArray("area_list");

                        areaList.clear();
                        for (int i = 0; i < area.length(); i++) {


                            JSONObject o = area.getJSONObject(i);

                            String area_name = o.getString("area_name");
                            String area_id = o.getString("a_id");


                            Area item = new Area(area_name, area_id);
                            areaList.add(item);

                        }
                    }
                    else{
                        Toast.makeText(getActivity().getApplicationContext(),"NO AREA",Toast.LENGTH_LONG).show();

                    }
                    maAdapter = new areaAdapter(areaList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    recyclerView6.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                    recyclerView6.setHasFixedSize(true);
                    recyclerView6.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                    recyclerView6.setLayoutManager(mLayoutManager);
                    recyclerView6.setItemAnimator(new DefaultItemAnimator());
                    recyclerView6.setAdapter(maAdapter);











                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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


