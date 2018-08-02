package sales_app.com.sales_app.Activities;

import android.content.Intent;
import sales_app.com.sales_app.Activities.areaDetails;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import sales_app.com.sales_app.R;

public class areaDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_details);
        TextView AName = findViewById(R.id.areaName);
        TextView Aid = findViewById(R.id.area_id);

        Intent toy6006 = getIntent();
        Bundle bd = toy6006.getExtras();
        if (bd != null) {
            String aName = (String) bd.get("areaName");
            String aid =(String) bd.get("area_id");
            AName.setText(aName);
            Aid.setText(aid);







        }
    }





}
