package com.mycillin.user.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mycillin.user.R;
import com.mycillin.user.list.AccountList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrbagaskoro on 20/09/2017.
 */

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.MyViewHolder> {
    private List<AccountList> accountLists;
    private ArrayList<AccountList> arrayAccountLists;

    public AccountAdapter(List<AccountList> accountLists) {
        this.accountLists = accountLists;
        this.arrayAccountLists = new ArrayList<>();
        this.arrayAccountLists.addAll(accountLists);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_account_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AccountList resultList = accountLists.get(position);
        holder.accountName.setText(resultList.getAccountName());
    }

    @Override
    public int getItemCount() {
        return accountLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView accountName;

        public MyViewHolder(View itemView) {
            super(itemView);
            accountName = itemView.findViewById(R.id.rowAccountList_tv_accountNameText);
        }
    }
}
