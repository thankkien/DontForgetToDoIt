package com.csetlu.dontforgettodoit;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

public class AddNewActivity extends BottomSheetDialogFragment {

    MainActivity activity;
    ViecCanLamA adapter;
    ViecCanLamM congViecMoi = new ViecCanLamM();
    Button buttonSave;
    EditText editTextCV;
    TextView textViewDate, textViewTime;

    public AddNewActivity(MainActivity activity) {
        this.activity = activity;
        this.adapter = activity.adapter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_add_new, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        buttonSave = v.findViewById(R.id.btnSave);
        editTextCV = v.findViewById(R.id.editTextTask);
        textViewDate = v.findViewById(R.id.editTextDate);
        textViewTime = v.findViewById(R.id.editTextTime);

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
                String Text = editTextCV.getText().toString();
                String date = textViewDate.getText().toString();
                String time = textViewTime.getText().toString();
                if (editTextCV.getText().toString().equals("")) {
                    Toast.makeText(activity.getApplicationContext(), "Task must not be left blank", Toast.LENGTH_SHORT).show();
                } else if (textViewDate.getText().toString().equals("")) {
                    Toast.makeText(activity.getApplicationContext(), "Date must not be left blank", Toast.LENGTH_SHORT).show();
                } else if (textViewTime.getText().toString().equals("")) {
                    Toast.makeText(activity.getApplicationContext(), "Time must not be left blank", Toast.LENGTH_SHORT).show();
                } else {
                    adapter.themCongViec(congViecMoi);
                    setAlarm(Text, date, time);


                }
                dismiss();
            }
        });

        return v;
    }

    public void setAlarm(String text, String date, String time) {
        AlarmManager alarmMgr = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(activity, AlarmReceiver.class);
        i.putExtra("event", text);
        i.putExtra("date", date);
        i.putExtra("time", time);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                activity, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        String datetime = date + " " + time;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            Date date1 = dateFormat.parse(datetime);
            alarmMgr.setExact(AlarmManager.RTC_WAKEUP, date1.getTime(), pendingIntent);


        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}