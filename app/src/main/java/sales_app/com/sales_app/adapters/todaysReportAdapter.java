package sales_app.com.sales_app.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;


import sales_app.com.sales_app.models.salesOfficers;
import sales_app.com.sales_app.R;


public class todaysReportAdapter extends RecyclerView.Adapter<todaysReportAdapter.MyViewHolder> {

    private List<salesOfficers> salesOfficersList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView EName, report;
        RelativeLayout todaysreport_layout;



        public MyViewHolder(View view) {
            super(view);
            EName = (TextView) view.findViewById(R.id.EName);
            report = (TextView) view.findViewById(R.id.REPORT);
        }
    }

    public todaysReportAdapter(List<salesOfficers> salesOfficersList) {
        this.salesOfficersList = salesOfficersList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todayreport_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        salesOfficers salesexecutive = salesOfficersList.get(position);
        holder.EName.setText(salesexecutive.getEName());
        holder.report.setText("TODAYS REPORT");



    }

    @Override
    public int getItemCount() {
        return salesOfficersList.size();
    }
}
