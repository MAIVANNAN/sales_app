package sales_app.com.sales_app.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import sales_app.com.sales_app.R;

public class Solditems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solditems);
        init();
    }

    public void init() {
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tb = new TableRow(this);
        TextView tv = new TextView(this);
        tv.setText("Salesmanid");
        tv.setTextColor(Color.BLACK);
        tv.setPadding(10, 10, 10, 10);
        tb.addView(tv);
        TextView tv1 = new TextView(this);
        tv1.setText("Customername");
        tv1.setTextColor(Color.BLACK);
        tv1.setPadding(10, 10, 10, 10);
        tb.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText("Productname");
        tv2.setTextColor(Color.BLACK);
        tv2.setPadding(10, 10, 10, 10);
        tb.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText("Quantity");
        tv3.setTextColor(Color.BLACK);
        tv3.setPadding(10, 10, 10, 10);
        tb.addView(tv3);
        TextView tv4 = new TextView(this);
        tv4.setText("Price");
        tv4.setTextColor(Color.BLACK);
        tv4.setPadding(10, 10, 10, 10);
        tb.addView(tv4);
        stk.addView(tb);
        for (int i = 0; i < 35; i++) {
            TableRow tbrow1 = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText("" + i);
            t1v.setPadding(10, 10, 10, 10);
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.CENTER);
            tbrow1.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText("nivedita" + i);
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.CENTER);
            tbrow1.addView(t2v);
            TextView t3v = new TextView(this);
            t3v.setText("Parleg" + i);
            t3v.setTextColor(Color.BLACK);
            t3v.setGravity(Gravity.CENTER);
            tbrow1.addView(t3v);
            TextView t4v = new TextView(this);
            t4v.setText("25" + i);
            t4v.setTextColor(Color.BLACK);
            t4v.setGravity(Gravity.CENTER);
            tbrow1.addView(t4v);
            TextView t5v = new TextView(this);
            t5v.setText("Rs" + i);
            t5v.setTextColor(Color.BLACK);
            t5v.setGravity(Gravity.CENTER);
            tbrow1.addView(t5v);
            stk.addView(tbrow1);
        }
    }
}




