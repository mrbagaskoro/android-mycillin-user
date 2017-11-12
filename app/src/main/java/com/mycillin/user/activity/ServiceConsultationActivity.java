package com.mycillin.user.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mycillin.user.R;
import com.mycillin.user.adapter.ConsultationMenuAdapter;
import com.mycillin.user.list.ConsultationMenuList;
import com.mycillin.user.util.RecyclerTouchListener;

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

    public static final String KEY_ID_MENU_01 = "KEY_ID_MENU_01";
    public static final String KEY_ID_MENU_02 = "KEY_ID_MENU_02";
    public static final String KEY_ID_MENU_03 = "KEY_ID_MENU_03";
    public static final String KEY_ID_MENU_04 = "KEY_ID_MENU_04";
    public static final String KEY_ID_MENU_05 = "KEY_ID_MENU_05";
    public static final String KEY_ID_MENU_06 = "KEY_ID_MENU_06";
    public static final String KEY_ID_MENU_07 = "KEY_ID_MENU_07";
    public static final String KEY_ID_MENU_08 = "KEY_ID_MENU_08";
    public static final String KEY_ID_MENU_09 = "KEY_ID_MENU_09";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_consultation);
        ButterKnife.bind(this);

        toolbar.setTitle(R.string.servicesActivity_consultationServiceTitle);

        getConsultationMenu();

        consultationMenuRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), consultationMenuRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ConsultationMenuList list = consultationMenuLists.get(position);

                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void getConsultationMenu() {
        consultationMenuRecyclerView.setLayoutManager(new GridLayoutManager(ServiceConsultationActivity.this, 3));
        consultationMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());

        consultationMenuLists.clear();
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_01, getString(R.string.serviceConsultationActivity_menu1Title)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_02, getString(R.string.serviceConsultationActivity_menu2Title)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_03, getString(R.string.serviceConsultationActivity_menu3Title)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_04, getString(R.string.serviceConsultationActivity_menu4Title)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_05, getString(R.string.serviceConsultationActivity_menu5Title)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_06, getString(R.string.serviceConsultationActivity_menu6Title)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_07, getString(R.string.serviceConsultationActivity_menu7Title)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_08, getString(R.string.serviceConsultationActivity_menu8Title)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_09, getString(R.string.serviceConsultationActivity_menu9Title)));

        consultationMenuAdapter = new ConsultationMenuAdapter(consultationMenuLists);
        consultationMenuRecyclerView.setAdapter(consultationMenuAdapter);
        consultationMenuAdapter.notifyDataSetChanged();
    }
}
