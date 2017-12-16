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
import com.mycillin.user.fragment.HistoryInProgressFragment;
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
    private HistoryInProgressFragment historyInProgressFragment;

    public InProgressAdapter(List<InProgressList> inProgressLists, HistoryInProgressFragment historyInProgressFragment) {
        this.inProgressLists = inProgressLists;
        this.arrayInProgressLists = new ArrayList<>();
        this.arrayInProgressLists.addAll(inProgressLists);
        this.historyInProgressFragment = historyInProgressFragment;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_in_progress_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        InProgressList resultList = inProgressLists.get(position);
        holder.doctorName.setText(resultList.getPartnerName());
        holder.bookType.setText(resultList.getServiceTypeDesc());
        holder.bookDate.setText(resultList.getOrderDate());
        holder.bookTime.setText(resultList.getOrderTime());
        holder.bookStatusDesc.setText(resultList.getBookingStatusDesc());

        if(!resultList.getPartnerPic().equals("")) {
            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.ic_action_user)
                    .fitCenter();

            Glide.with(historyInProgressFragment.getContext())
                    .load(resultList.getPartnerPic())
                    .apply(requestOptions)
                    .into(holder.doctorPic);
        }
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
        private TextView bookStatusDesc;
        private CircleImageView doctorPic;


        public MyViewHolder(View itemView) {
            super(itemView);
            doctorName = itemView.findViewById(R.id.rowInProgressList_tv_doctorName);
            bookType = itemView.findViewById(R.id.rowInProgressList_tv_bookType);
            bookDate = itemView.findViewById(R.id.rowInProgressList_tv_bookDate);
            bookTime = itemView.findViewById(R.id.rowInProgressList_tv_bookTime);
            bookStatusDesc = itemView.findViewById(R.id.rowInProgressList_tv_bookStatusDesc);
            doctorPic = itemView.findViewById(R.id.rowInProgressList_iv_doctorAvatar);
        }
    }
}
