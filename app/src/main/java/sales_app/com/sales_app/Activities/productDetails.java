package sales_app.com.sales_app.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import sales_app.com.sales_app.R;

public class productDetails extends AppCompatActivity {

    private TextView custName,custEmail,custPhone,custAddress;
    TextView custArea1;
    Button custEditBtn,delButton;
    String getName,getEmail,getPhone,getAddress,getArea,getGST;
    String getC_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        custName = findViewById(R.id.custName);
        custEmail = findViewById(R.id.E_mail);
        custPhone = findViewById(R.id.CustPhone);
        custAddress = findViewById(R.id.CustAddress);



        Intent toy6001 = getIntent();
        Bundle bd = toy6001.getExtras();
        if(bd != null)
        {


            getName = (String) bd.get("PId");
            getEmail = (String) bd.get("PName");
            getPhone = (String) bd.get("PPrice");
            getAddress = (String) bd.get("Pstock");




            custName.setText(getName);
            custEmail.setText(getEmail);
            custPhone.setText(getPhone);
            custAddress.setText(getAddress);



        }


    }
}
