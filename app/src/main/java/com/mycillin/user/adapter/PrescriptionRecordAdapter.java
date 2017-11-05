package com.mycillin.user.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mycillin.user.R;
import com.mycillin.user.list.PrescriptionRecordList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrbagaskoro on 05-Nov-17.
 */

public class PrescriptionRecordAdapter extends RecyclerView.Adapter<PrescriptionRecordAdapter.MyViewHolder> {
    private List<PrescriptionRecordList> prescriptionRecordLists;
    private ArrayList<PrescriptionRecordList> arrayPrescriptionRecordLists;

    public PrescriptionRecordAdapter(List<PrescriptionRecordList> prescriptionRecordLists) {
        this.prescriptionRecordLists = prescriptionRecordLists;
        this.arrayPrescriptionRecordLists = new ArrayList<>();
        this.arrayPrescriptionRecordLists.addAll(prescriptionRecordLists);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_prescription_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PrescriptionRecordList resultList = prescriptionRecordLists.get(position);
        holder.prescriptionName.setText(resultList.getPrescriptionName());
        holder.prescriptionQty.setText(resultList.getPrescriptionQty());
        holder.prescriptionUnit.setText(resultList.getUnitId());
        holder.prescriptionDosage.setText(resultList.getDosageId());
        holder.prescriptionInstruction.setText(resultList.getInstructionId());
    }

    @Override
    public int getItemCount() {
        return prescriptionRecordLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView prescriptionName;
        private TextView prescriptionQty;
        private TextView prescriptionUnit;
        private TextView prescriptionDosage;
        private TextView prescriptionInstruction;

        public MyViewHolder(View itemView) {
            super(itemView);
            prescriptionName = itemView.findViewById(R.id.rowPrescriptionList_tv_prescriptionName);
            prescriptionQty = itemView.findViewById(R.id.rowPrescriptionList_tv_prescriptionQty);
            prescriptionUnit = itemView.findViewById(R.id.rowPrescriptionList_tv_prescriptionUnit);
            prescriptionDosage = itemView.findViewById(R.id.rowPrescriptionList_tv_prescriptionDosage);
            prescriptionInstruction = itemView.findViewById(R.id.rowPrescriptionList_tv_prescriptionInstruction);
        }
    }
}
