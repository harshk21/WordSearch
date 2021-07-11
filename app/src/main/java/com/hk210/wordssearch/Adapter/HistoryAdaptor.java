package com.hk210.wordssearch.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.hk210.wordssearch.Model.ModelHistory;
import com.hk210.wordssearch.R;

import java.util.List;

public class HistoryAdaptor extends RecyclerView.Adapter<HistoryAdaptor.MyViewHolder> {

    private Context context;
    private List<ModelHistory> data;

    public HistoryAdaptor(Context context, List<ModelHistory> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_horizontal, parent, false));
    }

    @Override
    public void onBindViewHolder(HistoryAdaptor.MyViewHolder holder, int position) {
        holder.sea_made.setText(data.get(position).getSearchHistory());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView sea_made;

        public MyViewHolder(View itemView) {
            super(itemView);
            sea_made = itemView.findViewById(R.id.search_text_view);
        }
    }
}
