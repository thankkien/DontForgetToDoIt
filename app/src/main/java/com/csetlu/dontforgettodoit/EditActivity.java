package com.csetlu.dontforgettodoit;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.csetlu.dontforgettodoit.Adapter.ViecCanLamA;
import com.csetlu.dontforgettodoit.Model.ViecCanLamM;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditActivity extends BottomSheetDialogFragment {

    private MainActivity activity;
    ViecCanLamM congViecCu;
    ViecCanLamM congViecMoi = new ViecCanLamM();
    ViecCanLamA adapter;
    Button buttonSave;
    EditText editTextCV;
    TextView textViewDate, textViewTime;

    public EditActivity(MainActivity activity, ViecCanLamM congViec) {
        this.activity = activity;
        this.adapter = activity.adapter();
        this.congViecCu = congViec;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_edit, container, false);

        buttonSave = v.findViewById(R.id.btnSave);
        editTextCV = v.findViewById(R.id.editTextTask);
        textViewDate = v.findViewById(R.id.editTextDate);
        textViewTime = v.findViewById(R.id.editTextTime);

        congViecMoi.ganCV(congViecCu.layCV());
        congViecMoi.ganMaCV(congViecCu.layMaCV());
        congViecMoi.ganNgay(congViecCu.layNgay());
        congViecMoi.ganThoiGian(congViecCu.layThoiGian());

        editTextCV.setText(congViecCu.layCV());
        textViewDate.setText(congViecCu.layNgay());
        textViewTime.setText(congViecCu.layThoiGian());

        editTextCV.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                congViecMoi.ganCV(editTextCV.getText().toString());
            }
        });

        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int ngay = calendar.get(Calendar.DATE);
                int thang = calendar.get(Calendar.MONTH);
                int nam = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        textViewDate.setText(simpleDateFormat.format(calendar.getTime()));

                        congViecMoi.ganNgay(textViewDate.getText().toString());
                    }
                }, nam, thang, ngay);
                datePickerDialog.show();
            }
        });

        textViewTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int gio = calendar.get(Calendar.HOUR_OF_DAY);
                int phut = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                        calendar.set(0, 0, 0, hourOfDay, minute);
                        textViewTime.setText(simpleDateFormat.format(calendar.getTime()));

                        congViecMoi.ganThoiGian(textViewTime.getText().toString());
                    }
                }, gio, phut, true);
                timePickerDialog.show();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextCV.getText().toString().equals(""))
                {
                    Toast.makeText(activity.getApplicationContext(), "Task must not be left blank", Toast.LENGTH_SHORT).show();
                    editTextCV.setText(congViecCu.layCV());
                } else if (textViewDate.getText().toString().equals(""))
                {
                    Toast.makeText(activity.getApplicationContext(), "Date must not be left blank", Toast.LENGTH_SHORT).show();
                } else if (textViewTime.getText().toString().equals(""))
                {
                    Toast.makeText(activity.getApplicationContext(), "Time must not be left blank", Toast.LENGTH_SHORT).show();
                } else {
                    adapter.suaCongViec(congViecCu, congViecMoi);
                    setAlarm(congViecMoi.layCV(),congViecMoi.layNgay(),congViecMoi.layThoiGian());
                }
                dismiss();
            }
        });

        return v;
    }
    private void setAlarm(String text, String date, String time) {
        AlarmManager am = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(activity.getApplicationContext(), AlarmReceiver.class);
        intent.putExtra("event", text);
        intent.putExtra("time", date);
        intent.putExtra("date", time);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity.getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String dateandtime = date + " " + time;
        DateFormat formatter = new SimpleDateFormat("d-M-yyyy hh:mm");
        try {
            Date date1 = formatter.parse(dateandtime);
            am.set(AlarmManager.RTC_WAKEUP, date1.getTime(), pendingIntent);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        activity.finish();

    }
}