package com.mycillin.user.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mycillin.user.R;
import com.mycillin.user.list.MedicalPersonnelList;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mrbagaskoro on 21-Sep-17.
 */

public class MedicalPersonnelAdapter extends RecyclerView.Adapter<MedicalPersonnelAdapter.MyViewHolder> {
    private List<MedicalPersonnelList> medicalPersonnelLists;
    private ArrayList<MedicalPersonnelList> ArrayMedicalPersonnelLists;
    private Activity activity;

    public MedicalPersonnelAdapter(List<MedicalPersonnelList> medicalRecordLists, Activity activity) {
        this.medicalPersonnelLists = medicalRecordLists;
        this.ArrayMedicalPersonnelLists = new ArrayList<>();
        this.ArrayMedicalPersonnelLists.addAll(medicalRecordLists);
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_medical_personnel_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final MedicalPersonnelList resultList = medicalPersonnelLists.get(position);
        holder.doctorName.setText(resultList.getDoctorName());
        holder.doctorType.setText(resultList.getDoctorType());
        holder.doctorPermitt.setText(activity.getString(R.string.medicalPersonnelAdapter_doctorPermittTitle) + resultList.getDoctorPermitt());
    }

    @Override
    public int getItemCount() {
        return medicalPersonnelLists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView doctorName;
        private TextView doctorType;
        private TextView doctorPermitt;
        private CircleImageView doctorPic;

        MyViewHolder(View itemView) {
            super(itemView);
            doctorName = itemView.findViewById(R.id.rowMedicalRecordList_tv_doctorName);
            doctorType = itemView.findViewById(R.id.rowMedicalRecordList_tv_doctorType);
            doctorPermitt = itemView.findViewById(R.id.rowMedicalRecordList_tv_doctorPermit);
            doctorPic = itemView.findViewById(R.id.rowMedicalPersonnelList_iv_personnelAvatar);
        }
    }
}
