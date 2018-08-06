package sales_app.com.sales_app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sales_app.com.sales_app.models.Customer;
import sales_app.com.sales_app.R;

public class customerAdapter extends RecyclerView.Adapter<customerAdapter.MyViewHolder> {
    private Context context;

    private List<Customer> customerList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Name, phonenum, age;

        public MyViewHolder(View view) {
            super(view);
            Name = (TextView) view.findViewById(R.id.Name);
            phonenum = (TextView) view.findViewById(R.id.phonenum);
        }
    }

    public customerAdapter(List<Customer> customerList,Context context) {
        this.customerList = customerList;
        this.context =context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Customer customer = customerList.get(position);
        holder.Name.setText(customer.getName());

        holder.phonenum.setText(customer.getPhonenum());
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }


    public void setFilter(ArrayList<Customer> newList)
    {

        customerList = new ArrayList<>();
        customerList.addAll(newList);
        notifyDataSetChanged();
    }
}