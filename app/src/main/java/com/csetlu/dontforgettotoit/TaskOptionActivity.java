package com.csetlu.dontforgettotoit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.csetlu.dontforgettotoit.Adapter.ViecCanLamA;
import com.csetlu.dontforgettotoit.Model.ViecCanLamM;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class TaskOptionActivity extends BottomSheetDialogFragment {

    ViecCanLamM congViecMoi = new ViecCanLamM();
    ViecCanLamA adapter;
    Button buttonSave;
    EditText editTextCV;
    TextView textViewDate, textViewTime;

    public TaskOptionActivity(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_task_option, container, false);

        return v;
    }
}