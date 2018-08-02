package sales_app.com.sales_app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

import sales_app.com.sales_app.R;
import sales_app.com.sales_app.models.Product;

public class productAdapter extends RecyclerView.Adapter<productAdapter.MyViewHolder > {



    private List<Product> productList;
    Context context;

    AdapterView.OnItemClickListener mItemClickListener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView PName,Pid,Sl_no,Proprice,ProInStock;
        public TextView id;

        public MyViewHolder(View view) {
            super(view);
            PName = view.findViewById(R.id.productName);
            Pid =view.findViewById(R.id.p_id);
            Sl_no =view.findViewById(R.id.sl_no);
            Proprice =view.findViewById(R.id.goodsPrice);
            ProInStock =view.findViewById(R.id.Ingoods);




        }
    }

    public productAdapter(List<Product> productList,Context context) {
        this.productList = productList;
        this.context =context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.productlayout, parent, false);
        final RecyclerView.ViewHolder mViewHolder = new MyViewHolder(itemView);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Product product = productList.get(position);
        //holder.id.setText(product.getP_id());
        holder.PName.setText(product.getP_name());
        holder.Pid.setText(product.getP_id());
        holder.ProInStock.setText(product.getInstock());
        holder.Proprice.setText(product.getPrice());




        //holder.id.setText(area.getAid());


    }



    @Override
    public int getItemCount() {
        return productList.size();

    }
}