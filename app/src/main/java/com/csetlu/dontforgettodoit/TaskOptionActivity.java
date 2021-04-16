package com.csetlu.dontforgettodoit;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.csetlu.dontforgettodoit.Adapter.ViecCanLamA;
import com.csetlu.dontforgettodoit.Model.ViecCanLamM;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class TaskOptionActivity extends BottomSheetDialogFragment {

    private final MainActivity activity;
    private final ViecCanLamA adapter;
    private final ViecCanLamM congViecDangChon;
    private Button buttonDel, buttonEdit;

    public TaskOptionActivity(MainActivity activity, ViecCanLamM congViec) {
        this.activity = activity;
        this.adapter = activity.adapter();
        this.congViecDangChon = congViec;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_task_option, container, false);

        buttonEdit = v.findViewById(R.id.btnEdit);
        buttonDel = v.findViewById(R.id.btnDelete);

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditActivity LuaChonBS = new EditActivity(activity, congViecDangChon);
                LuaChonBS.show(activity.getSupportFragmentManager(), LuaChonBS.getTag());
                dismiss();
            }
        });

        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogXacNhan = new AlertDialog.Builder(activity);
                alertDialogXacNhan.setMessage("Do you want to delete this task???");
                alertDialogXacNhan.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        adapter.xoaCongViec(congViecDangChon);
                    }
                });
                alertDialogXacNhan.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialogXacNhan.show();
                cancelAlarm();
            }
        });

        return v;
    }

    public void cancelAlarm() {
        AlarmManager alarmMgr = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(activity, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity,
                congViecDangChon.layMaCV(), myIntent, PendingIntent.FLAG_CANCEL_CURRENT );
        pendingIntent.cancel();
        alarmMgr.cancel(pendingIntent);
        dismiss();
    }
}