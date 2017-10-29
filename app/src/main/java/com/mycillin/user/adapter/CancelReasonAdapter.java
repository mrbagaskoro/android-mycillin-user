package com.mycillin.user.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mycillin.user.R;
import com.mycillin.user.list.CancelReasonList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrbagaskoro on 29-Oct-17.
 */

public class CancelReasonAdapter extends RecyclerView.Adapter<CancelReasonAdapter.MyViewHolder> {

    private List<CancelReasonList> cancelReasonLists;
    private ArrayList<CancelReasonList> arrayCancelReasonLists;

    public CancelReasonAdapter(List<CancelReasonList> cancelReasonLists) {
        this.cancelReasonLists = cancelReasonLists;
        this.arrayCancelReasonLists = new ArrayList<>();
        this.arrayCancelReasonLists.addAll(cancelReasonLists);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cancel_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CancelReasonList resultList = cancelReasonLists.get(position);
        holder.cancelReasonDesc.setText(resultList.getCancelReasonDesc());
    }

    @Override
    public int getItemCount() {
        return cancelReasonLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView cancelReasonDesc;

        public MyViewHolder(View itemView) {
            super(itemView);
            cancelReasonDesc = itemView.findViewById(R.id.rowCancelList_ll_reasonsText);
        }
    }
}
