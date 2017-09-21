package com.mycillin.user.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mycillin.user.R;
import com.mycillin.user.list.InProgressList;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mrbagaskoro on 21-Sep-17.
 */

public class InProgressAdapter extends RecyclerView.Adapter<InProgressAdapter.MyViewHolder> {
    private List<InProgressList> inProgressLists;
    private ArrayList<InProgressList> arrayInProgressLists;

    public InProgressAdapter(List<InProgressList> inProgressLists) {
        this.inProgressLists = inProgressLists;
        this.arrayInProgressLists = new ArrayList<>();
        this.arrayInProgressLists.addAll(inProgressLists);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_in_progress_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        InProgressList resultList = inProgressLists.get(position);
        holder.doctorName.setText(resultList.getBookDoctor());
        holder.bookType.setText(resultList.getBookType());
        holder.bookDate.setText(resultList.getBookDate());
        holder.bookTime.setText(resultList.getBookTime());
    }

    @Override
    public int getItemCount() {
        return inProgressLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView doctorName;
        private TextView bookType;
        private TextView bookDate;
        private TextView bookTime;
        private CircleImageView doctorPic;


        public MyViewHolder(View itemView) {
            super(itemView);
            doctorName = itemView.findViewById(R.id.rowInProgressList_tv_doctorName);
            bookType = itemView.findViewById(R.id.rowInProgressList_tv_bookType);
            bookDate = itemView.findViewById(R.id.rowInProgressList_tv_bookDate);
            bookTime = itemView.findViewById(R.id.rowInProgressList_tv_bookTime);
            doctorPic = itemView.findViewById(R.id.rowInProgressList_iv_doctorAvatar);
        }
    }
}
