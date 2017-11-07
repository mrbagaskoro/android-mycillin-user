package com.mycillin.user.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.mycillin.user.R;
import com.mycillin.user.adapter.MedicalPersonnelAdapter;
import com.mycillin.user.list.MedicalPersonnelList;
import com.mycillin.user.util.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MedicalPersonnelActivity extends AppCompatActivity {

    @BindView(R.id.medicalPersonnelActivity_toolbar)
    Toolbar toolbar;
    @BindView(R.id.medicalPersonnelActivity_rv_recyclerView)
    RecyclerView medicalPersonnelRecyclerView;

    private List<MedicalPersonnelList> medicalPersonnelLists = new ArrayList<>();
    private MedicalPersonnelAdapter medicalPersonneldAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_personnel);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.medicalPersonnelActivity_title);

        getMedicalPersonnelList();

        medicalPersonnelRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), medicalPersonnelRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                MedicalPersonnelList list = medicalPersonnelLists.get(position);

                Intent intent = new Intent(MedicalPersonnelActivity.this, MedicalPersonnelDetailActivity.class);
                intent.putExtra(MedicalPersonnelDetailActivity.KEY_FLAG_DOCTOR_NAME, list.getDoctorName());
                intent.putExtra(MedicalPersonnelDetailActivity.KEY_FLAG_DOCTOR_TYPE, list.getDoctorType());
                intent.putExtra(MedicalPersonnelDetailActivity.KEY_FLAG_DOCTOR_PERMITT, list.getDoctorPermitt());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void getMedicalPersonnelList() {
        medicalPersonnelRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        medicalPersonnelRecyclerView.setItemAnimator(new DefaultItemAnimator());

        medicalPersonnelLists.clear();
        medicalPersonnelLists.add(new MedicalPersonnelList("dr Andi Husada", "Cardiologist", "291827/II/2011", "husada.jpg"));
        medicalPersonnelLists.add(new MedicalPersonnelList("dr Titin Hutapea", "Dentist", "471827/XI/2016", "hutapea.jpg"));
        medicalPersonnelLists.add(new MedicalPersonnelList("dr Hotman Sitorus", "Neurologist", "362817/IV/2015", "sitorus.jpg"));

        medicalPersonneldAdapter = new MedicalPersonnelAdapter(medicalPersonnelLists, MedicalPersonnelActivity.this);
        medicalPersonnelRecyclerView.setAdapter(medicalPersonneldAdapter);
        medicalPersonneldAdapter.notifyDataSetChanged();
    }
}
