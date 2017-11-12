package com.mycillin.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mycillin.user.R;
import com.mycillin.user.list.InsuranceList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrbagaskoro on 23-Sep-17.
 */

public class InsuranceAdapter extends RecyclerView.Adapter<InsuranceAdapter.MyViewHolder>{
    private Context context;
    private List<InsuranceList> insuranceLists;
    private ArrayList<InsuranceList> arrayInsuranceLists;

    public InsuranceAdapter(Context context, List<InsuranceList> insuranceLists) {
        this.context = context;
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

        Glide.with(context).load(resultList.getInsuranceImageUrl()).into(holder.insuranceImage);
        holder.insurancePolicyNo.setText(resultList.getPolicyNo());
        holder.insuranceProvider.setText(resultList.getInsuranceProviderDesc());
        holder.insuredName.setText(resultList.getInsuredName());
        holder.insuranceHolder.setText(resultList.getInsuranceHolderName());
    }

    @Override
    public int getItemCount() {
        return insuranceLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView insuranceImage;
        private TextView insurancePolicyNo;
        private TextView insuranceProvider;
        private TextView insuredName;
        private TextView insuranceHolder;

        public MyViewHolder(View itemView) {
            super(itemView);
            insuranceImage = itemView.findViewById(R.id.rowInsuranceList_iv_insuranceImage);
            insurancePolicyNo = itemView.findViewById(R.id.rowInsuranceList_tv_insurancePolicyNo);
            insuranceProvider = itemView.findViewById(R.id.rowInsuranceList_tv_insurancePolicyProvider);
            insuredName = itemView.findViewById(R.id.rowInsuranceList_tv_insuredName);
            insuranceHolder = itemView.findViewById(R.id.rowInsuranceList_tv_insurancePolicyHolder);
        }
    }
}
