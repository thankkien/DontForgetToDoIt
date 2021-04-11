package com.csetlu.dontforgettotoit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.csetlu.dontforgettotoit.Adapter.ViecCanLamA;
import com.csetlu.dontforgettotoit.Controller.CoSoDuLieu;
import com.csetlu.dontforgettotoit.Model.ViecCanLamM;

import java.util.ArrayList;
import java.util.List;

import static com.csetlu.dontforgettotoit.R.id.tasksRecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView VCL_RecyclerView;
    private ViecCanLamA VCL_Adapter;
    private CoSoDuLieu db = new CoSoDuLieu(this);
    private List<ViecCanLamM> dsCongViec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        dsCongViec = new ArrayList<>();

        VCL_RecyclerView = findViewById(tasksRecyclerView);
        VCL_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        VCL_Adapter = new ViecCanLamA(this);
        VCL_RecyclerView.setAdapter(VCL_Adapter);

        db.moCSDL();

        //ViecCanLamM cv = new ViecCanLamM();
        //cv.ganCV("test1");
        //cv.ganTT(0);

        //db.themCongViec(cv);
        //db.themCongViec(cv);
        //db.chuyenTrangThai(1,1);
        //db.suaCongViec(1,"cong viec 1");
        //db.suaThoiGian(1,"24/06/2000 05:30");
        //db.suaThoiGian(2,"30/04/2000 07:00");
        //db.suaCongViec(2,"cong viec 2");
        //db.xoaBanGhi(2);

        VCL_Adapter.ganDsCongViec(db.layDsCongViec());
        //Toast.makeText(getApplicationContext(), dsCongViec.get(2).layCV(), Toast.LENGTH_SHORT).show();
    }
}