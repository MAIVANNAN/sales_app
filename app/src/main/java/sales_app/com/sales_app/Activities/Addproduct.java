package sales_app.com.sales_app.Activities;

import android.app.Activity;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sales_app.com.sales_app.R;
import sales_app.com.sales_app.models.Area;

public class Addproduct extends AppCompatActivity {
    private Spinner spinner;
    private List<Area> areaList ;

    private ArrayList<String> arrayList;




    private TextInputEditText gname, price, instock, stk,dId;
    TextInputLayout gname_label, price_label,stock_label;
    Button addbtn;







    private String TAG = "addCusotmerActivityTAG";
    private Activity activity = Addproduct.this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);
        gname_label = (TextInputLayout) findViewById(R.id.gname_label);
        price_label = (TextInputLayout) findViewById(R.id.price_label);
        stock_label=findViewById(R.id.stock_label);
        gname = (TextInputEditText) findViewById(R.id.goods);
        price =  findViewById(R.id.pri);
        instock =  findViewById(R.id.stock);
        addbtn =  findViewById(R.id.btnaddProduct);








        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goodsName = gname.getText().toString();
                String goodsPrice = price.getText().toString();
                String Instockgoods = instock.getText().toString();





                if (!validategoodsName() || !validatePrice() || !validateStock()  ) {

                } else {


                    signup(goodsName, goodsPrice, Instockgoods);
                    SharedPreferences preferences = getSharedPreferences("Mypref", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.putString("gName", goodsName);
                    editor.putString("gPrice", goodsPrice);
                    editor.putString("InGoods", Instockgoods);


                    editor.commit();


                }

            }

        });
    }









    public void signup(final String gName, final String gPrice, final String InGoods){



        SharedPreferences LINK = getSharedPreferences("MAIN_LINK",0);
        String link1 = LINK.getString("MAIN_LINK","");
        Log.i("maivannan", "" + link1);
        String URL_add_product = link1+"add_product.php";
        Log.i("maivannan", "" + URL_add_product);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adding ");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(Addproduct.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_add_product, new Response.Listener<String>() {
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
                Toast.makeText(Addproduct.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> stringMap = new HashMap<>();
                SharedPreferences manger_id = getSharedPreferences("manager_id",MODE_PRIVATE);
                String crea_id = manger_id.getString("manager_id","");
                stringMap.put("name",gName);
                stringMap.put("price",gPrice);
                stringMap.put("stock",InGoods);
                stringMap.put("s_id",crea_id);



                Log.i("maivannan",""+stringMap.toString());

                return stringMap;
            }
        };

        requestQueue.add(stringRequest);

    }
















    private boolean validategoodsName()
    {
        String Salesname=gname.getText().toString().trim();
        if (Salesname.isEmpty())
        {
            gname_label.setError("Name Field can't be empty");
            return false;
        }
        else
        {
            gname_label.setError(null);
            return true;
        }
    }
    private boolean validateStock()
    {
        String Salesemail=instock.getText().toString().trim();
        if (Salesemail.isEmpty())
        {
            stock_label.setError("Email Field can't be empty");
            return false;
        }
        else
        {
            stock_label.setError(null);
            return true;
        }
    }
    private boolean validatePrice()
    {
        String Salesphone=price.getText().toString().trim();
        if (Salesphone.isEmpty())
        {
            price_label.setError("Phone number Field can't be empty");
            return false;
        }
        else
        {
            price_label.setError(null);
            return true;
        }
    }






}
