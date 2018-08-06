package sales_app.com.sales_app;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import sales_app.com.sales_app.adapters.productAdapter;
import sales_app.com.sales_app.models.Product;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link soldItems.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link soldItems#newInstance} factory method to
 * create an instance of this fragment.
 */
public class soldItems extends Fragment {
    private List<Product> productList ;
    private RecyclerView recyclerView12;
    private productAdapter mAdapter;
    View v;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public soldItems() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment soldItems.
     */
    // TODO: Rename and change types and number of parameters
    public static soldItems newInstance(String param1, String param2) {
        soldItems fragment = new soldItems();
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
        productList.add(new Product(1,"juice ","200","21","2"));
        productList.add(new Product(2,"juice ","201","22","2"));
        productList.add(new Product(3,"juice ","202","23","2"));
        productList.add(new Product(4,"juice ","203","24","2"));
        productList.add(new Product(4,"juice ","203","25","2"));
        productList.add(new Product(6,"juice ","204","26","2"));
        productList.add(new Product(7,"juice ","205","27","2"));

        productList.add(new Product(10,"juice ","20","20","2"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_sold_items, container, false);
        // Inflate the layout for this fragment
        recyclerView12 =(RecyclerView)v.findViewById(R.id.productRecycleView2);
        mAdapter = new productAdapter(productList,getContext());
        recyclerView12.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView12.setAdapter(mAdapter);

        return v ;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
