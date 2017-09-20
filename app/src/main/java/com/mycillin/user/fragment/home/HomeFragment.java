package com.mycillin.user.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mycillin.user.R;
import com.mycillin.user.activity.service.ServiceBookDoctorActivity;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class HomeFragment extends Fragment {

    @BindView(R.id.homeFragment_et_dropdown)
    TextView tvDropdwon;
    @BindView(R.id.homeFragment_cv_carouselView)
    CarouselView carouselView;

    @BindView(R.id.homeFragment_ll_bookADoctorService)
    LinearLayout goToBookDoctorService;
    @BindView(R.id.homeFragment_ll_medicalReservationService)
    LinearLayout goToMedicalReservationService;
    @BindView(R.id.homeFragment_ll_consultationService)
    LinearLayout goToConsultationService;
    @BindView(R.id.homeFragment_ll_ambulanceService)
    LinearLayout goToAmbulanceService;
    @BindView(R.id.homeFragment_ll_redeemPrescriptionService)
    LinearLayout goToRedeemPrescriptionService;

    private ArrayList<String> items = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);

        final SpinnerDialog spinnerDialog = new SpinnerDialog(getActivity(), items, getString(R.string.servicesActivity_dropdownTitle), R.style.DialogAnimations_SmileWindow);

        items.add("Me");
        items.add("Wife");
        items.add("Son");
        items.add("Daughter");

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                tvDropdwon.setText(s);
            }
        });

        tvDropdwon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialog.showSpinerDialog();
            }
        });

        goToBookDoctorService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ServiceBookDoctorActivity.class);
                startActivity(intent);
            }
        });

        final int[] sampleImages = {R.drawable.promo_1, R.drawable.promo_2,};
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sampleImages[position]);
            }
        });


        return rootView;
    }
}
