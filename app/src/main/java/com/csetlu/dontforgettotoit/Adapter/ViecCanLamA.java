package com.csetlu.dontforgettotoit.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.csetlu.dontforgettotoit.Controller.CoSoDuLieu;
import com.csetlu.dontforgettotoit.MainActivity;
import com.csetlu.dontforgettotoit.Model.ViecCanLamM;
import com.csetlu.dontforgettotoit.R;
import com.csetlu.dontforgettotoit.TaskOptionActivity;

import java.util.List;

public class ViecCanLamA extends RecyclerView.Adapter<ViecCanLamA.ViewHolder> {

    private MainActivity activity;
    private CoSoDuLieu db;
    private List<ViecCanLamM> dsCongViec;

    public ViecCanLamA(CoSoDuLieu db, MainActivity activity) {
        this.db = db;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_task, parent, false);
        return new ViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    public void onBindViewHolder(ViewHolder holder, int position) {
        db.moCSDL();
        ViecCanLamM item = dsCongViec.get(position);
        holder.textViewcongViec.setText(item.layCV() + "\n" + item.layThoiGian());
        if (!toBool(item.layTT())){
            holder.backgroundCongViec.setBackgroundResource(R.color.design_default_color_background);
        } else {
            holder.backgroundCongViec.setBackgroundResource(R.color.green_400);
        }
        holder.backgroundCongViec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskOptionActivity LuaChonBS = new TaskOptionActivity();
                LuaChonBS.show(activity.getSupportFragmentManager(), LuaChonBS.getTag());
            }
        });
//        holder.backgroundCongViec.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!toBool(item.layTT())){
//                    holder.backgroundCongViec.setBackgroundResource(R.color.design_default_color_background);
//                    item.ganTT(1);
//                    db.chuyenTrangThai(item.layMaCV(),1);
//                }else {
//                    holder.backgroundCongViec.setBackgroundResource(R.color.green_400);
//                    item.ganTT(0);
//                    db.chuyenTrangThai(item.layMaCV(),0);
//                }
//            }
//        });
//        holder.backgroundCongViec.setOnLongClickListener(new CheckBox.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                Toast.makeText(activity.getApplicationContext(), "co chay", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return dsCongViec.size();
    }

    private boolean toBool(int n) {
        return n != 0; //1 = true; 0 = false
    }

    public void themCongViec(ViecCanLamM congViecMoi) {
        db.moCSDL();
        db.themCongViec(congViecMoi);
        dsCongViec.add(congViecMoi);
        notifyItemInserted(dsCongViec.indexOf(congViecMoi));
    }

    public void suaCongViec(ViecCanLamM congViecMoi) {
        db.moCSDL();
        db.themCongViec(congViecMoi);
        dsCongViec.add(congViecMoi);
        notifyItemInserted(dsCongViec.lastIndexOf(congViecMoi));
    }

    public void ganDsCongViec(List<ViecCanLamM> dsCongViec) {
        this.dsCongViec = dsCongViec;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewcongViec;
        RelativeLayout backgroundCongViec;

        ViewHolder(View view) {
            super(view);
            textViewcongViec = view.findViewById(R.id.textViewCongViec);
            backgroundCongViec = view.findViewById(R.id.backgroundCongViec);
        }


    }
}
