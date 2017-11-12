package com.mycillin.user.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.mycillin.user.R;
import com.mycillin.user.adapter.ConsultationMenuAdapter;
import com.mycillin.user.list.ConsultationMenuList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceConsultationActivity extends AppCompatActivity {

    @BindView(R.id.serviceConsultationActivity_toolbar)
    Toolbar toolbar;

    @BindView(R.id.serviceConsultationActivity_rv_recyclerView)
    RecyclerView consultationMenuRecyclerView;

    private List<ConsultationMenuList> consultationMenuLists = new ArrayList<>();
    private ConsultationMenuAdapter consultationMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_consultation);
        ButterKnife.bind(this);

        toolbar.setTitle(R.string.servicesActivity_consultationServiceTitle);

        getConsultationMenu();
    }

    private void getConsultationMenu() {
        consultationMenuRecyclerView.setLayoutManager(new GridLayoutManager(ServiceConsultationActivity.this, 3));
        consultationMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());

        consultationMenuLists.clear();
        consultationMenuLists.add(new ConsultationMenuList("", "1"));
        consultationMenuLists.add(new ConsultationMenuList("", "2"));
        consultationMenuLists.add(new ConsultationMenuList("", "3"));
        consultationMenuLists.add(new ConsultationMenuList("", "4"));
        consultationMenuLists.add(new ConsultationMenuList("", "5"));
        consultationMenuLists.add(new ConsultationMenuList("", "6"));
        consultationMenuLists.add(new ConsultationMenuList("", "7"));
        consultationMenuLists.add(new ConsultationMenuList("", "8"));
        consultationMenuLists.add(new ConsultationMenuList("", "9"));

        consultationMenuAdapter = new ConsultationMenuAdapter(consultationMenuLists);
        consultationMenuRecyclerView.setAdapter(consultationMenuAdapter);
        consultationMenuAdapter.notifyDataSetChanged();
    }
}
