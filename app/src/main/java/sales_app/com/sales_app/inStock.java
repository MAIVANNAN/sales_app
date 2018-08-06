package sales_app.com.sales_app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

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

import android.view.View;

import sales_app.com.sales_app.Activities.Addproduct;
import sales_app.com.sales_app.Activities.Instock;
import sales_app.com.sales_app.Activities.addSalesEx;
import sales_app.com.sales_app.adapters.productAdapter;
import sales_app.com.sales_app.adapters.salesAdapter;
import sales_app.com.sales_app.models.Product;
import sales_app.com.sales_app.models.salesOfficers;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link inStock.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link inStock#newInstance} factory method to
 * create an instance of this fragment.
 */
public class inStock extends Fragment {
    private List<Product> productList ;
    private RecyclerView recyclerView12;
    private productAdapter mAdapter;
    View v;
    TextView add_product;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public inStock() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment inStock.
     */
    // TODO: Rename and change types and number of parameters
    public static inStock newInstance(String param1, String param2) {
        inStock fragment = new inStock();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



        productList  = new ArrayList<>();





    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_in_stock, container, false);
        // Inflate the layout for this fragment
        add_product = v.findViewById(R.id.add_product_p);
        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Addproduct.class);

                startActivity(intent);

            }
        });







        recyclerView12 =(RecyclerView)v.findViewById(R.id.productRecycleView);
        mAdapter = new productAdapter(productList,getContext());
        recyclerView12.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView12.setAdapter(mAdapter);

        return v ;
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
        SharedPreferences manger_id = getActivity().getSharedPreferences("manager_id",0);
        final String s_id = manger_id.getString("manager_id","");
        SharedPreferences LINK = getActivity().getSharedPreferences("MAIN_LINK",0);
        String link1 = LINK.getString("MAIN_LINK","");
        Log.i("maivannan", "" + link1);

        String URLM1 = link1+"instock_product.php";
        Log.i("maivannan", "" + URLM1);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLM1, new Response.Listener<String>() {

            public void onResponse(String response) {
                Log.i("Hitesh",""+response);
                progressDialog.dismiss();

                try {
                    JSONObject object = new JSONObject(response);
                    JSONObject jsonObj = new JSONObject(object.getString("instock_product_response"));
                    if(jsonObj.getString("errorcode").equals("47000")) {

                        JSONArray array = jsonObj.getJSONArray("area_list");
                        productList.clear();

                        for (int i = 0; i < array.length(); i++) {
                            Log.i("count", "" + array.length());

                            JSONObject o1 = array.getJSONObject(i);

                            String p_id = o1.getString("p_id");
                            String p_name = o1.getString("p_name");
                            String price = o1.getString("price");
                            String no_of_stock = o1.getString("no_of_stock");

                            Product item = new Product(p_id,p_name,price,no_of_stock,s_id);
                            productList.add(item);
                        }
                    }
                    else {
                        Toast.makeText(getActivity().getApplicationContext(),"NO PRODUCTS",Toast.LENGTH_LONG).show();

                    }

                    mAdapter = new productAdapter(productList,getActivity().getApplicationContext());
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    recyclerView12.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                    recyclerView12.setHasFixedSize(true);
                    recyclerView12.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                    recyclerView12.setLayoutManager(mLayoutManager);
                    recyclerView12.setItemAnimator(new DefaultItemAnimator());
                    recyclerView12.setAdapter(mAdapter);












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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
