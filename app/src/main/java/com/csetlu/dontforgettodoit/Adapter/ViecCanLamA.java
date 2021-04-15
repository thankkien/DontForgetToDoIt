package com.csetlu.dontforgettodoit.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.csetlu.dontforgettodoit.Controller.CoSoDuLieu;
import com.csetlu.dontforgettodoit.MainActivity;
import com.csetlu.dontforgettodoit.Model.ViecCanLamM;
import com.csetlu.dontforgettodoit.R;
import com.csetlu.dontforgettodoit.TaskOptionActivity;

import java.util.List;

public class ViecCanLamA extends RecyclerView.Adapter<ViecCanLamA.ViewHolder> {

    private MainActivity activity;
    private CoSoDuLieu db;
    private List<ViecCanLamM> dsCongViec;

    public ViecCanLamA(MainActivity activity) {
        this.activity = activity;
        this.db = activity.database();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_task, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        db.moCSDL();
        ViecCanLamM item = dsCongViec.get(position);
        holder.textViewCongViec.setText(item.layCV());
        holder.textViewNgayGio.setText(item.layNgay() + " " + item.layThoiGian());
//        if (!toBool(item.layTT())){
//            holder.backgroundCongViec.setBackgroundResource(R.color.design_default_color_background);
//        } else {
//            holder.backgroundCongViec.setBackgroundResource(R.color.md_light_green_400);
//        }
        holder.backgroundCongViec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskOptionActivity LuaChonBS = new TaskOptionActivity(activity, item);
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
        int maCVmoi = (int) db.themCongViec(congViecMoi);
        congViecMoi.ganMaCV(maCVmoi);
        dsCongViec.add(congViecMoi);
        notifyItemInserted(dsCongViec.indexOf(congViecMoi));
    }

    public void suaCongViec(ViecCanLamM congViecCu, ViecCanLamM congViecMoi) {
        db.moCSDL();
        db.suaCongViec(congViecMoi);
        dsCongViec.set(dsCongViec.indexOf(congViecCu), congViecMoi);
        notifyItemChanged(dsCongViec.indexOf(congViecMoi));
    }

    public void xoaCongViec(ViecCanLamM congViec) {
        db.moCSDL();
        db.xoaBanGhi(congViec.layMaCV());
        notifyItemRemoved(dsCongViec.indexOf(congViec));
        dsCongViec.remove(congViec);
    }

    public void ganDsCongViec(List<ViecCanLamM> dsCongViec) {
        this.dsCongViec = dsCongViec;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCongViec;
        TextView textViewNgayGio;
        RelativeLayout backgroundCongViec;

        ViewHolder(View view) {
            super(view);
            textViewCongViec = view.findViewById(R.id.textViewCongViec);
            textViewNgayGio = view.findViewById(R.id.textViewNgayGio);
            backgroundCongViec = view.findViewById(R.id.backgroundCongViec);
        }
    }
}
