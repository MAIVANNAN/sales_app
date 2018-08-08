package sales_app.com.sales_app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
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
import java.util.zip.Inflater;

import sales_app.com.sales_app.Activities.addArea;
import sales_app.com.sales_app.Fragments.salesExDetails;
import sales_app.com.sales_app.adapters.areaAdapter;
import sales_app.com.sales_app.models.Area;
import sales_app.com.sales_app.models.RecyclerTouchListener;
import android.widget.Button;

import static android.content.Context.MODE_PRIVATE;

public class areadialog extends AppCompatDialogFragment {
   private TextView areaName;
    private RecyclerView recyclerView6;
    private List<Area> areaList ;
    private areaAdapter maAdapter;
    TextView add_Area;
    private ExampleDialogListener listener;
    Button add_area;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view  = inflater.inflate(R.layout.layout_dialog,null);

        recyclerView6 =   view.findViewById(R.id.dialogArea);
        areaList = new ArrayList<>();


        recyclerView6.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView6, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String areaName = areaList.get(position).getAName();
                String area_id=areaList.get(position).getAid();
                listener.applyTexts(areaName,area_id);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        add_area= view.findViewById(R.id.dialogAddArea);
        add_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), addArea.class);

                startActivity(intent);

            }
        });




        builder.setView(view ).setTitle("areaList").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        areaName = view.findViewById(R.id.textView5);
        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (ExampleDialogListener) context;

        }catch (ClassCastException e ){
            throw new ClassCastException(context.toString()+"must implement ");
        }


    }

    public interface ExampleDialogListener{
        void applyTexts(String area,String a_id);
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
