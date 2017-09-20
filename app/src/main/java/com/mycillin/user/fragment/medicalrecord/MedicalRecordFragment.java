package com.mycillin.user.fragment.medicalrecord;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mycillin.user.R;
import com.mycillin.user.adapter.MedicalRecordAdapter;
import com.mycillin.user.list.MedicalRecordList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class MedicalRecordFragment extends Fragment {


    @BindView(R.id.medicalRecordFragment_et_dropdown)
    TextView tvDropdwon;
    @BindView(R.id.medicalRecordFragment_rv_recyclerView)
    RecyclerView medicalRecordRecyclerView;

    private List<MedicalRecordList> medicalRecordLists = new ArrayList<>();
    private MedicalRecordAdapter medicalRecordAdapter;
    private ArrayList<String> items = new ArrayList<>();

    public MedicalRecordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_medical_record, container, false);
        ButterKnife.bind(this, rootView);

        getActivity().setTitle(R.string.nav_medical_record);

        final SpinnerDialog spinnerDialog = new SpinnerDialog(getActivity(), items, getString(R.string.servicesActivity_dropdownTitle), R.style.DialogAnimations_SmileWindow);

        items.add("Me");
        items.add("Wife");
        items.add("Son");
        items.add("Daughter");

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                tvDropdwon.setText(s);
            }
        });

        tvDropdwon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialog.showSpinerDialog();
            }
        });

        getMedicalRecordList();

        return rootView;
    }

    private void getMedicalRecordList() {
        medicalRecordRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        medicalRecordRecyclerView.setItemAnimator(new DefaultItemAnimator());

        medicalRecordLists.clear();
        medicalRecordLists.add(new MedicalRecordList("10", "JAN", "2017", "dr Andi Husada"));
        medicalRecordLists.add(new MedicalRecordList("22", "MAR", "2017", "dr Hotman Sitorus"));
        medicalRecordLists.add(new MedicalRecordList("07", "MAY", "2017", "dr Andi Husada"));
        medicalRecordLists.add(new MedicalRecordList("05", "JUL", "2017", "dr Andi Husada"));

        medicalRecordAdapter = new MedicalRecordAdapter(medicalRecordLists, getActivity());
        medicalRecordRecyclerView.setAdapter(medicalRecordAdapter);
        medicalRecordAdapter.notifyDataSetChanged();
    }
}
