package com.mycillin.user.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mycillin.user.R;
import com.mycillin.user.list.InsuranceList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrbagaskoro on 23-Sep-17.
 */

public class InsuranceAdapter extends RecyclerView.Adapter<InsuranceAdapter.MyViewHolder>{
    private List<InsuranceList> insuranceLists;
    private ArrayList<InsuranceList> arrayInsuranceLists;

    public InsuranceAdapter(List<InsuranceList> insuranceLists) {
        this.insuranceLists = insuranceLists;
        this.arrayInsuranceLists = new ArrayList<>();
        this.arrayInsuranceLists.addAll(insuranceLists);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_insurance_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        InsuranceList resultList = insuranceLists.get(position);
        holder.insuranceCompany.setText(resultList.getInsuranceCompany());
        holder.insurancePolicy.setText(resultList.getInsurancePolicy());
    }

    @Override
    public int getItemCount() {
        return insuranceLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView insuranceCompany;
        private TextView insurancePolicy;

        public MyViewHolder(View itemView) {
            super(itemView);
            insuranceCompany = itemView.findViewById(R.id.rowInsuranceList_tv_insuranceCompany);
            insurancePolicy = itemView.findViewById(R.id.rowInsuranceList_tv_insurancePolicy);
        }
    }
}
