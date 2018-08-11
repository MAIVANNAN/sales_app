package sales_app.com.sales_app.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

import sales_app.com.sales_app.R;
import sales_app.com.sales_app.models.Product;
import sales_app.com.sales_app.models.soldProduct;

public class soldProductAdapter extends RecyclerView.Adapter<soldProductAdapter.MyViewHolder >{
    private List<soldProduct> productList;
    Context context;

    AdapterView.OnItemClickListener mItemClickListener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView PName,Pid,Sl_no,Proprice,ProInStock;
        public TextView id;

        public MyViewHolder(View view) {
            super(view);
            PName = view.findViewById(R.id.Product_name);
            Proprice =view.findViewById(R.id.price_item);
            ProInStock =view.findViewById(R.id.Instock_item);




        }
    }

    public soldProductAdapter(List<soldProduct> productList,Context context) {
        this.productList = productList;
        this.context =context;
    }

    @Override
    public soldProductAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        final RecyclerView.ViewHolder mViewHolder = new MyViewHolder(itemView);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final soldProduct product = productList.get(position);
        //holder.id.setText(product.getP_id());
        holder.PName.setText(product.getP_name());
        holder.ProInStock.setText(product.getQuantity());
        holder.Proprice.setText(product.getPrice());
    }





    @Override
    public int getItemCount() {
        return productList.size();

    }
}
