package com.mycillin.user.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mycillin.user.R;
import com.mycillin.user.activity.HistoryInProgressDetailActivity;
import com.mycillin.user.adapter.InProgressAdapter;
import com.mycillin.user.list.InProgressList;
import com.mycillin.user.util.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryInProgressFragment extends Fragment {

    @BindView(R.id.historyInProgressFragment_rv_recyclerView)
    RecyclerView inProgressRecyclerView;

    private List<InProgressList> inProgressLists = new ArrayList<>();
    private InProgressAdapter inProgressAdapter;

    public HistoryInProgressFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history_in_progress, container, false);
        ButterKnife.bind(this, rootView);

        getInProgressList();

        return rootView;
    }

    public void getInProgressList() {
        inProgressRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        inProgressRecyclerView.setItemAnimator(new DefaultItemAnimator());

        inProgressLists.clear();
        inProgressLists.add(new InProgressList("pic_01.jpg", "dr Andi Husada", "Visit request to home", "07 September 2017", "15.00 WIB"));

        inProgressAdapter = new InProgressAdapter(inProgressLists);
        inProgressRecyclerView.setAdapter(inProgressAdapter);
        inProgressAdapter.notifyDataSetChanged();

        inProgressRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), inProgressRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                InProgressList list = inProgressLists.get(position);

                Intent intent = new Intent(getContext(), HistoryInProgressDetailActivity.class);
                intent.putExtra(HistoryInProgressDetailActivity.KEY_FLAG_DOCTOR_NAME, list.getBookDoctor());
                intent.putExtra(HistoryInProgressDetailActivity.KEY_FLAG_DOCTOR_DATE, list.getBookDate());
                intent.putExtra(HistoryInProgressDetailActivity.KEY_FLAG_DOCTOR_TIME, list.getBookTime());
                intent.putExtra(HistoryInProgressDetailActivity.KEY_FLAG_DOCTOR_TYPE, list.getBookType());
                intent.putExtra(HistoryInProgressDetailActivity.KEY_FLAG_DOCTOR_PIC, list.getDoctorPic());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
}