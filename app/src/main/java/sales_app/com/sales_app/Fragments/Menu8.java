package sales_app.com.sales_app.Fragments;

/**
 * Created by hp on 29-05-2018.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sales_app.com.sales_app.Activities.MainActivity;
import sales_app.com.sales_app.LoginActivity;
import sales_app.com.sales_app.R;

/**
 * Created by Belal on 18/09/16.
 */


public class Menu8 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_menu_8, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles

        Intent i = new Intent(getActivity().getApplicationContext(),LoginActivity.class);
        startActivity(i);
    }


}