package sales_app.com.sales_app.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import sales_app.com.sales_app.Fragments.Menu1;
import sales_app.com.sales_app.Fragments.Menu2;
import sales_app.com.sales_app.Fragments.Menu3;
import sales_app.com.sales_app.Fragments.Menu4;
import sales_app.com.sales_app.Fragments.Menu5;
import sales_app.com.sales_app.Fragments.Menu6;
import sales_app.com.sales_app.Fragments.Menu7;
import sales_app.com.sales_app.Fragments.Menu8;
import sales_app.com.sales_app.LoginActivity;
import sales_app.com.sales_app.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String PREFS_NAME = "manager_id";
    public static final String PREFS_NAME1 = "login";

    SharedPreferences sharedpreferences;
    TextView company_name;
    View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences comap_name = getSharedPreferences("company_name",0);
        SharedPreferences owner_name = getSharedPreferences("owner_name",0);

        String c_name = comap_name.getString("company_name","");
        String o_name = owner_name.getString("owner_name","");






        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView text1 = (TextView) header.findViewById(R.id.orange);
        text1.setText(c_name);

        TextView text = (TextView) header.findViewById(R.id.textView);
        text.setText(o_name);


        navigationView.setNavigationItemSelectedListener(this);
        displaySelectedScreen(R.id.nav_menu1);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }
    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_menu1:
                fragment = new Menu1();
                break;
            case R.id.nav_menu2:
                fragment = new Menu2();
                break;
            case R.id.nav_menu3:
                fragment = new Menu3();
                break;
            case R.id.nav_menu4:
                fragment = new Menu4();
                break;
            case R.id.nav_menu5:
                fragment = new Menu5();
                break;
            case R.id.nav_menu6:
                fragment = new Menu6();
                break;
            case R.id.nav_menu7:
                fragment = new Menu7();
                break;
            case R.id.nav_menu8:

                SharedPreferences manger_id2 = getSharedPreferences(PREFS_NAME,0);
                SharedPreferences sp = getSharedPreferences(PREFS_NAME1, 0);
                SharedPreferences comap_name = getSharedPreferences("company_name",MODE_PRIVATE);
                SharedPreferences owner_name = getSharedPreferences("owner_name",MODE_PRIVATE);

                sp.edit().putBoolean("logged",false).apply();
                manger_id2.edit().putString("manager_id","0").apply();
                comap_name.edit().putString("company_name","0").apply();
                owner_name.edit().putString("owner_name","0").apply();


                fragment =new Menu8();
                finish();

                break;



        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
