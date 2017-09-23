package com.mycillin.user.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mycillin.user.R;
import com.mycillin.user.list.PaymentList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrbagaskoro on 23-Sep-17.
 */

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MyViewHolder> {
    private List<PaymentList> paymentLists;
    private ArrayList<PaymentList> arrayPaymentLists;

    public PaymentAdapter(List<PaymentList> paymentLists) {
        this.paymentLists = paymentLists;
        this.arrayPaymentLists = new ArrayList<>();
        this.arrayPaymentLists.addAll(paymentLists);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_payment_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PaymentList resultList = paymentLists.get(position);
        holder.paymentName.setText(resultList.getPaymentName());
        holder.paymentAccount.setText(resultList.getPaymentAccount());
    }

    @Override
    public int getItemCount() {
        return paymentLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView paymentName;
        private TextView paymentAccount;

        public MyViewHolder(View itemView) {
            super(itemView);
            paymentName = itemView.findViewById(R.id.rowPaymentList_tv_paymentName);
            paymentAccount = itemView.findViewById(R.id.rowPaymentList_tv_paymentAccount);
        }
    }
}
