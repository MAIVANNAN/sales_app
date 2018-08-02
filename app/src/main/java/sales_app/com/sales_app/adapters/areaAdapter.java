package sales_app.com.sales_app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

import sales_app.com.sales_app.models.Area;
import sales_app.com.sales_app.R;

public class areaAdapter extends RecyclerView.Adapter<areaAdapter.MyViewHolder > {



    private List<Area> AreaList;
    Context context;

    AdapterView.OnItemClickListener mItemClickListener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView AName;
        public TextView id;

        public MyViewHolder(View view) {
            super(view);
            AName = view.findViewById(R.id.AName);
            id =view.findViewById(R.id.Aid);


        }
    }

    public areaAdapter(List<Area> AreaList) {
        this.AreaList = AreaList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.arealayout, parent, false);
        final RecyclerView.ViewHolder mViewHolder = new MyViewHolder(itemView);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Area area = AreaList.get(position);
        holder.AName.setText(area.getAName());
       holder.id.setText(area.getAid());


    }



    @Override
    public int getItemCount() {
        return AreaList.size();

    }
    public void setData(List<String> newData) {
        this.AreaList.clear();
        newData.addAll(newData);
        notifyDataSetChanged();
    }

}


