package com.hk210.wordssearch.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.hk210.wordssearch.Model.DataModel;
import com.hk210.wordssearch.R;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder2> {
    private final Context context;
    private final List<DataModel> fetchedData;

    public DataAdapter(Context context, List<DataModel> fetchedData) {
        this.context = context;
        this.fetchedData = fetchedData;
    }


    @Override
    public MyViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder2(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_view, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder2 holder, int position) {
        holder.t1.setText(fetchedData.get(position).getWord());
        holder.t2.setText(fetchedData.get(position).getScore());

    }

    @Override
    public int getItemCount() {
        return fetchedData.size();
    }


    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        public TextView t1, t2;

        public MyViewHolder2(View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.search_result_word);
            t2 = itemView.findViewById(R.id.search_result_score);
        }
    }
}
