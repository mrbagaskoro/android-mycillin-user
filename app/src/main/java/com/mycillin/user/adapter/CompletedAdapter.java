package com.mycillin.user.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mycillin.user.R;
import com.mycillin.user.list.CompletedList;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mrbagaskoro on 21-Sep-17.
 */

public class CompletedAdapter extends RecyclerView.Adapter<CompletedAdapter.MyViewHolder> {
    private List<CompletedList> completedLists;
    private ArrayList<CompletedList> arraCompletedLists;

    public CompletedAdapter(List<CompletedList> completedLists) {
        this.completedLists = completedLists;
        this.arraCompletedLists = new ArrayList<>();
        this.arraCompletedLists.addAll(completedLists);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_completed_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CompletedList resultList = completedLists.get(position);
        holder.doctorName.setText(resultList.getBookDoctor());
        holder.bookType.setText(resultList.getBookType());
        holder.bookDate.setText(resultList.getBookDate());
        holder.bookTime.setText(resultList.getBookTime());
    }

    @Override
    public int getItemCount() {
        return completedLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView doctorName;
        private TextView bookType;
        private TextView bookDate;
        private TextView bookTime;
        private CircleImageView doctorPic;


        public MyViewHolder(View itemView) {
            super(itemView);
            doctorName = itemView.findViewById(R.id.rowCompletedList_tv_doctorName);
            bookType = itemView.findViewById(R.id.rowCompletedList_tv_bookType);
            bookDate = itemView.findViewById(R.id.rowCompletedList_tv_bookDate);
            bookTime = itemView.findViewById(R.id.rowCompletedList_tv_bookTime);
            doctorPic = itemView.findViewById(R.id.rowCompletedList_iv_doctorAvatar);
        }
    }
}
