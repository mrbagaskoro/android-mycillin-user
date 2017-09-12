package com.mycillin.user.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mycillin.user.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntroFourFragment extends Fragment {

    @BindView(R.id.introFour_bt_getStartedBtn)
    Button getStartedBtn;
    @BindView(R.id.introFour_rg_radioGroup)
    RadioGroup genderRadioGroup;

    private static final String ARG_LAYOUT_RES_ID = "layoutResId";
    private int layoutResId;

    public static IntroFourFragment newInstance(int layoutResId) {
        IntroFourFragment sampleSlide = new IntroFourFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
        sampleSlide.setArguments(args);

        return sampleSlide;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID)) {
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(layoutResId, container, false);
        ButterKnife.bind(this, rootView);

        // set default language = IN
        genderRadioGroup.check(R.id.introFour_rb_radioIn);

        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String genderSelected = ((RadioButton) rootView.findViewById(((RadioGroup) rootView.findViewById(R.id.introFour_rg_radioGroup)).getCheckedRadioButtonId())).getText().toString();
                Toast.makeText(getContext(), genderSelected, Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}
