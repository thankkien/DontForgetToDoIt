package com.csetlu.dontforgettodoit.Adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.csetlu.dontforgettodoit.AlarmReceiver;
import com.csetlu.dontforgettodoit.MainActivity;
import com.csetlu.dontforgettodoit.Model.ViecCanLamM;
import com.csetlu.dontforgettodoit.R;
import com.csetlu.dontforgettodoit.TaskOptionActivity;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ViecCanLamA extends RecyclerView.Adapter<ViecCanLamA.ViewHolder> {

    private final MainActivity activity;
    private final CoSoDuLieu db;
    private List<ViecCanLamM> dsCongViec;

    public ViecCanLamA(MainActivity activity) {
        this.activity = activity;
        this.db = activity.database();
    }

    @Override
    public @NotNull ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_task, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        db.moCSDL();
        ViecCanLamM item = dsCongViec.get(position);
        holder.textViewCongViec.setText(item.layCV());
        holder.textViewNgayGio.setText(item.layNgay() + " " + item.layThoiGian());
        holder.backgroundCongViec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("soThuTu", position);
                bundle.putInt("maCV", item.layMaCV());
                bundle.putString("congViec", item.layCV());
                bundle.putString("ngay", item.layNgay());
                bundle.putString("thoiGian", item.layThoiGian());
                TaskOptionActivity LuaChonBS = new TaskOptionActivity(activity, bundle);
                LuaChonBS.show(activity.getSupportFragmentManager(), LuaChonBS.getTag());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dsCongViec.size();
    }

    public void themCongViec(Bundle bundle) {
        ViecCanLamM congViecMoi = new ViecCanLamM();
        congViecMoi.ganCV(bundle.getString("congViec"));
        congViecMoi.ganNgay(bundle.getString("ngay"));
        congViecMoi.ganThoiGian(bundle.getString("thoiGian"));
        int success = (int) db.themCongViec(congViecMoi);
        db.moCSDL();
        if (success != -1) {
            congViecMoi.ganMaCV(success);
            dsCongViec.add(congViecMoi);
            notifyItemInserted(dsCongViec.indexOf(congViecMoi));
            setAlarm(congViecMoi);
        }
    }

    public void suaCongViec(Bundle bundle) {
        int pos = bundle.getInt("soThuTu");
        ViecCanLamM congViecMoi = new ViecCanLamM();
        congViecMoi.ganMaCV(bundle.getInt("maCV"));
        congViecMoi.ganCV(bundle.getString("congViec"));
        congViecMoi.ganNgay(bundle.getString("ngay"));
        congViecMoi.ganThoiGian(bundle.getString("thoiGian"));
        db.moCSDL();
        int success = db.suaCongViec(congViecMoi);
        if (success == 1) {
            dsCongViec.set(pos, congViecMoi);
            notifyItemChanged(pos);
            setAlarm(congViecMoi);
        }
    }

    public void xoaCongViec(int position) {
        ViecCanLamM congViec = dsCongViec.get(position);
        db.moCSDL();
        int success = db.xoaBanGhi(congViec.layMaCV());
        if (success == 1) {
            notifyItemRemoved(position);
            dsCongViec.remove(position);
            cancelAlarm(congViec);
        }
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

    public void setAlarm(ViecCanLamM congViec) {
        AlarmManager alarmMgr = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(activity, AlarmReceiver.class);
        intent.putExtra("maCV", congViec.layMaCV());
        intent.putExtra("congViec", congViec.layCV());
        intent.putExtra("ngay", congViec.layNgay());
        intent.putExtra("thoiGian", congViec.layThoiGian());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, congViec.layMaCV(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        String datetime = congViec.layNgay() + " " + congViec.layThoiGian();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            Date datetimeParsed = dateFormat.parse(datetime);
            alarmMgr.setExact(AlarmManager.RTC_WAKEUP, datetimeParsed.getTime(), pendingIntent);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void cancelAlarm(ViecCanLamM congViec) {
        AlarmManager alarmMgr = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(activity, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity,
                congViec.layMaCV(), myIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        pendingIntent.cancel();
        alarmMgr.cancel(pendingIntent);
    }
}
