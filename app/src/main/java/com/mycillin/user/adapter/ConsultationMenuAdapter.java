package com.mycillin.user.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mycillin.user.R;
import com.mycillin.user.list.ConsultationMenuList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 16003041 on 10/11/2017.
 */

public class ConsultationMenuAdapter extends RecyclerView.Adapter<ConsultationMenuAdapter.MyViewHolder> {
    private List<ConsultationMenuList> consultationMenuLists;
    private ArrayList<ConsultationMenuList> arrayConsultationMenuLists;

    public ConsultationMenuAdapter(List<ConsultationMenuList> consultationMenuLists) {
        this.consultationMenuLists = consultationMenuLists;
        this.arrayConsultationMenuLists = new ArrayList<>();
        this.arrayConsultationMenuLists.addAll(consultationMenuLists);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_menu_consultation_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ConsultationMenuList resultList = consultationMenuLists.get(position);
        holder.title.setText(resultList.getTitle());
    }

    @Override
    public int getItemCount() {
        return consultationMenuLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.rowMenuConsultationList_tv_title);
        }
    }
}
