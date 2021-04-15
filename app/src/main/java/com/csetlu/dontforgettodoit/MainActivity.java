package com.csetlu.dontforgettodoit;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.csetlu.dontforgettodoit.Adapter.ViecCanLamA;
import com.csetlu.dontforgettodoit.Controller.CoSoDuLieu;
import com.csetlu.dontforgettodoit.Model.ViecCanLamM;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.csetlu.dontforgettodoit.R.id.tasksRecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView VCL_RecyclerView;
    private FloatingActionButton fab;
    private ViecCanLamA VCL_Adapter;
    private CoSoDuLieu db = new CoSoDuLieu(this);
    private List<ViecCanLamM> dsCongViec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        VCL_RecyclerView = findViewById(tasksRecyclerView);
        VCL_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        VCL_Adapter = new ViecCanLamA(this);
        VCL_RecyclerView.setAdapter(VCL_Adapter);

        db.moCSDL();
        VCL_Adapter.ganDsCongViec(db.layDsCongViec());
//        Toast.makeText(getApplicationContext(), "co chay 1", Toast.LENGTH_SHORT).show();

        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewActivity ThemMoiBS = new AddNewActivity( MainActivity.this );
                ThemMoiBS.show(getSupportFragmentManager(), ThemMoiBS.getTag());
            }
        });
    }

    public ViecCanLamA adapter(){
        return VCL_Adapter;
    }

    public CoSoDuLieu database(){
        return db;
    }
}