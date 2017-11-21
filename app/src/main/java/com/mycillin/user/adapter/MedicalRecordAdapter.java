package com.mycillin.user.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mycillin.user.R;
import com.mycillin.user.activity.MedicalRecordDetailActivity;
import com.mycillin.user.list.MedicalRecordList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrbagaskoro on 20-Sep-17.
 */

public class MedicalRecordAdapter extends RecyclerView.Adapter<MedicalRecordAdapter.MyViewHolder> {
    private List<MedicalRecordList> medicalRecordLists;
    private ArrayList<MedicalRecordList> ArrayMedicalRecordLists;
    private Activity activity;

    public MedicalRecordAdapter(List<MedicalRecordList> medicalRecordLists, Activity activity) {
        this.medicalRecordLists = medicalRecordLists;
        this.ArrayMedicalRecordLists = new ArrayList<>();
        this.ArrayMedicalRecordLists.addAll(medicalRecordLists);
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_medical_record_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final MedicalRecordList resultList = medicalRecordLists.get(position);
        holder.day.setText(resultList.getDay());
        holder.month.setText(resultList.getMonth());
        holder.year.setText(resultList.getYear());
        holder.doctorName.setText(resultList.getPartnerName());
    }

    @Override
    public int getItemCount() {
        return medicalRecordLists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView day;
        private TextView month;
        private TextView year;
        private TextView doctorName;

        MyViewHolder(View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.rowMedicalRecordList_tv_day);
            month = itemView.findViewById(R.id.rowMedicalRecordList_tv_month);
            year = itemView.findViewById(R.id.rowMedicalRecordList_tv_year);
            doctorName = itemView.findViewById(R.id.rowMedicalRecordList_tv_doctorName);
        }
    }
}
