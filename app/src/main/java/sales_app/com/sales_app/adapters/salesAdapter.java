package sales_app.com.sales_app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import sales_app.com.sales_app.R;
import sales_app.com.sales_app.models.salesOfficers;


public class salesAdapter extends RecyclerView.Adapter<salesAdapter.MyViewHolder> {
    private Context context;

    private List<salesOfficers> salesOfficersList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView EName, Ephonenum;

        public MyViewHolder(View view) {
            super(view);
            EName =  view.findViewById(R.id.EName);
            Ephonenum =  view.findViewById(R.id.Ephonenum);
        }
    }

    public salesAdapter(List<salesOfficers> salesOfficersList,Context context) {
        this.salesOfficersList = salesOfficersList;
        this.context =context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.executive_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        salesOfficers salesexecutive  = salesOfficersList.get(position);
        holder.EName.setText(salesexecutive.getEName());
        holder.Ephonenum.setText(salesexecutive.getEPhonenum());
    }

    @Override
    public int getItemCount() {
        return salesOfficersList.size();
    }
}