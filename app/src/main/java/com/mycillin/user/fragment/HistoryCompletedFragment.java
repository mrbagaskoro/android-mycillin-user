package com.mycillin.user.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mycillin.user.R;
import com.mycillin.user.adapter.CompletedAdapter;
import com.mycillin.user.list.CompletedList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryCompletedFragment extends Fragment {

    @BindView(R.id.historyCompletedFragment_rv_recyclerView)
    RecyclerView completedRecyclerView;

    private List<CompletedList> completedListList = new ArrayList<>();
    private CompletedAdapter completedAdapter;

    public HistoryCompletedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history_completed, container, false);
        ButterKnife.bind(this, rootView);

        getCompletedList();

        return rootView;
    }

    public void getCompletedList() {
        completedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        completedRecyclerView.setItemAnimator(new DefaultItemAnimator());

        completedListList.clear();
        completedListList.add(new CompletedList("pic_01.jpg", "dr Andi Husada", "Visit request to home", "07 September 2017", "15.00 WIB"));
        completedListList.add(new CompletedList("pic_01.jpg", "dr Titin Hutapea", "Visit request to clinic", "27 Agustus 2017", "10.00 WIB"));
        completedListList.add(new CompletedList("pic_01.jpg", "dr Hotman Sitorus", "Visit request to home", "11 Maret 2017", "20.00 WIB"));

        completedAdapter = new CompletedAdapter(completedListList);
        completedRecyclerView.setAdapter(completedAdapter);
        completedAdapter.notifyDataSetChanged();
    }
}
