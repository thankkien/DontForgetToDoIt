package com.csetlu.dontforgettotoit.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import com.csetlu.dontforgettotoit.Controller.CoSoDuLieu;
import com.csetlu.dontforgettotoit.MainActivity;
import com.csetlu.dontforgettotoit.Model.ViecCanLamM;
import com.csetlu.dontforgettotoit.R;

import java.util.List;

public class ViecCanLamA extends RecyclerView.Adapter<ViecCanLamA.ViewHolder> {

    private MainActivity activity;
    private CoSoDuLieu db;
    private List<ViecCanLamM> dsCongViec;

    public ViecCanLamA(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        ViecCanLamM item = dsCongViec.get(position);
        holder.congViec.setText(item.layCV()+"\n"+item.layThoiGian());
        holder.congViec.setChecked(toBool(item.layTT()));
    }

    @Override
    public int getItemCount() {
        return dsCongViec.size();
    }

    private boolean toBool(int n){
        return n!=0; //1 = true; 0 = false
    }

    public void ganDsCongViec(List<ViecCanLamM> dsCongViec){
        this.dsCongViec = dsCongViec;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox congViec;
        ViewHolder(View view) {
            super(view);
            congViec = view.findViewById(R.id.todoCheckBox);
        }
    }
}
