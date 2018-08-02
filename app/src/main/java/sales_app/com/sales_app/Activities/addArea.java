package sales_app.com.sales_app.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

import sales_app.com.sales_app.R;

public class addArea extends AppCompatActivity {
   TextInputEditText arName,arId;
    TextInputLayout Aname_label;
    Button addAreaBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_area);
        arName=findViewById(R.id.addAreaName);
        Aname_label=findViewById(R.id.Aname_label);

        addAreaBtn =findViewById(R.id.btnAddArea);



        addAreaBtn.setOnClickListener(new View.OnClickListener() {

            //String gender = radioGenderButton.getText().toString();


            @Override
            public void onClick(View v) {
                String areaName = arName.getText().toString();





                if (!validateSalesname() ) {

                    Toast.makeText(addArea.this, "Fill all details", Toast.LENGTH_SHORT).show();
                } else {


                    AddAREA(areaName);
                    SharedPreferences preferences = getSharedPreferences("Mypref", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.putString("areaName", areaName);

                    editor.commit();


                }

            }

        });
    }



    public void AddAREA(final String areaName){



        SharedPreferences LINK = getSharedPreferences("MAIN_LINK",0);
        String link1 = LINK.getString("MAIN_LINK","");
        Log.i("maivannan", "" + link1);
        String URL_add_area = link1+"add_area.php";
        Log.i("maivannan", "" + URL_add_area);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adding ");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(addArea.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_add_area, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("maivannan",""+response);
                progressDialog.dismiss();
                finish();











            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Hitesh",""+error);
                Toast.makeText(addArea.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> stringMap = new HashMap<>();
                SharedPreferences manger_id = getSharedPreferences("manager_id",0);
                String s_id = manger_id.getString("manager_id","");
                stringMap.put("area_name",areaName);
                stringMap.put("s_id",s_id);




                Log.i("maivannan",""+stringMap.toString());

                return stringMap;
            }
        };

        requestQueue.add(stringRequest);

    }









    private boolean validateSalesname()
    {
        String Aname=arName.getText().toString().trim();
        if (Aname.isEmpty())
        {
            Aname_label.setError("Field can't be empty");
            return false;
        }
        else
        {
            Aname_label.setError(null);
            return true;
        }
    }




}
