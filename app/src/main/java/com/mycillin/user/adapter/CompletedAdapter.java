package com.mycillin.user.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mycillin.user.R;
import com.mycillin.user.fragment.HistoryCompletedFragment;
import com.mycillin.user.list.CompletedList;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mrbagaskoro on 21-Sep-17.
 */

public class CompletedAdapter extends RecyclerView.Adapter<CompletedAdapter.MyViewHolder> {
    private List<CompletedList> completedLists;
    private ArrayList<CompletedList> arrayCompletedLists;
    private HistoryCompletedFragment historyCompletedFragment;

    public CompletedAdapter(List<CompletedList> completedLists, HistoryCompletedFragment historyCompletedFragment) {
        this.completedLists = completedLists;
        this.arrayCompletedLists = new ArrayList<>();
        this.arrayCompletedLists.addAll(completedLists);
        this.historyCompletedFragment = historyCompletedFragment;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_completed_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CompletedList resultList = completedLists.get(position);
        holder.doctorName.setText(resultList.getPartnerName());
        holder.bookType.setText(resultList.getServiceTypeDesc());
        holder.bookDate.setText(resultList.getOrderDate());
        holder.bookTime.setText(resultList.getOrderTime());

        if(resultList.getCancelStatus().equals("N")) {
            holder.bookStatusDesc.setText(resultList.getBookingStatusDesc());
        }
        else {
            holder.bookStatusDesc.setText(resultList.getCancelReasonUser());
            holder.bookStatusDesc.setTextColor(historyCompletedFragment.getResources().getColor(R.color.darkRed));
        }

        if(!resultList.getPartnerPic().equals("")) {
            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.ic_action_user)
                    .fitCenter();

            Glide.with(historyCompletedFragment.getContext())
                    .load(resultList.getPartnerPic())
                    .apply(requestOptions)
                    .into(holder.doctorPic);
        }
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
        private TextView bookStatusDesc;
        private CircleImageView doctorPic;


        public MyViewHolder(View itemView) {
            super(itemView);
            doctorName = itemView.findViewById(R.id.rowCompletedList_tv_doctorName);
            bookType = itemView.findViewById(R.id.rowCompletedList_tv_bookType);
            bookDate = itemView.findViewById(R.id.rowCompletedList_tv_bookDate);
            bookTime = itemView.findViewById(R.id.rowCompletedList_tv_bookTime);
            bookStatusDesc = itemView.findViewById(R.id.rowCompletedList_tv_bookStatusDesc);
            doctorPic = itemView.findViewById(R.id.rowCompletedList_iv_doctorAvatar);
        }
    }
}
