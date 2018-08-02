package sales_app.com.sales_app.Fragments;

/**
 * Created by hp on 29-05-2018.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import sales_app.com.sales_app.Activities.Addproduct;
import sales_app.com.sales_app.Activities.Instock;
import sales_app.com.sales_app.Activities.UnAvailablegoods;
import sales_app.com.sales_app.Activities.Solditems;
import sales_app.com.sales_app.R;
import sales_app.com.sales_app.adapters.productAdapter;
import sales_app.com.sales_app.models.Product;

/**
 * Created by Belal on 18/09/16.
 */


public class Menu5 extends Fragment {

    private List<Product> productsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private productAdapter mpAdapter;

    Button btn1, btn2, btn3, btn4;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_menu_5, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Product Details");




        btn1 = (Button)view. findViewById(R.id.btnsold);
        btn2 = (Button)view.findViewById(R.id.btnAvailgoods);
        btn3 = (Button)view. findViewById(R.id.btnInstock);
        btn4 = (Button)view.findViewById(R.id.btnAddProduct);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sold=new Intent(getActivity(),Solditems.class);
                startActivity(sold);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent unavail=new Intent(getActivity(),UnAvailablegoods.class);
                startActivity(unavail);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Instockpro=new Intent(getActivity(),Instock.class);
                startActivity(Instockpro);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Addpro=new Intent(getActivity(),Addproduct.class);
                startActivity(Addpro);
            }
        });




    }







}
