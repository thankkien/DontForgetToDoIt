package com.csetlu.dontforgettodoit;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddNewActivity extends BottomSheetDialogFragment {

    private final MainActivity activity;
    private final ViecCanLamA adapter;
    private final Bundle congViecMoi = new Bundle();
    private Button buttonSave;
    private EditText editTextCV;
    private TextView textViewDate, textViewTime;

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

        return v;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        editTextCV.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                congViecMoi.putString("congViec", editTextCV.getText().toString());
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
                        congViecMoi.putString("ngay", textViewDate.getText().toString());
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
                        congViecMoi.putString("thoiGian", textViewTime.getText().toString());
                    }
                }, gio, phut, true);
                timePickerDialog.show();
            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextCV.getText().toString().equals("")) {
                    Toast.makeText(activity.getApplicationContext(), "Task must not be left blank", Toast.LENGTH_SHORT).show();
                } else if (textViewDate.getText().toString().equals("")) {
                    Toast.makeText(activity.getApplicationContext(), "Date must not be left blank", Toast.LENGTH_SHORT).show();
                } else if (textViewTime.getText().toString().equals("")) {
                    Toast.makeText(activity.getApplicationContext(), "Time must not be left blank", Toast.LENGTH_SHORT).show();
                } else {
                    adapter.themCongViec(congViecMoi);
                }
                dismiss();
            }
        });
    }
}