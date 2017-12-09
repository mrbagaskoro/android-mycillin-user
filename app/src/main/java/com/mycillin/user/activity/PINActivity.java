package com.mycillin.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mycillin.user.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PINActivity extends AppCompatActivity {

    @BindView(R.id.pinActivity_tv_messageTxt)
    TextView messageTxt;
    @BindView(R.id.pinActivity_iv_circle1)
    ImageView pinCircle1;
    @BindView(R.id.pinActivity_iv_circle2)
    ImageView pinCircle2;
    @BindView(R.id.pinActivity_iv_circle3)
    ImageView pinCircle3;
    @BindView(R.id.pinActivity_iv_circle4)
    ImageView pinCircle4;
    @BindView(R.id.pinActivity_bt_button0)
    Button button0;
    @BindView(R.id.pinActivity_bt_button1)
    Button button1;
    @BindView(R.id.pinActivity_bt_button2)
    Button button2;
    @BindView(R.id.pinActivity_bt_button3)
    Button button3;
    @BindView(R.id.pinActivity_bt_button4)
    Button button4;
    @BindView(R.id.pinActivity_bt_button5)
    Button button5;
    @BindView(R.id.pinActivity_bt_button6)
    Button button6;
    @BindView(R.id.pinActivity_bt_button7)
    Button button7;
    @BindView(R.id.pinActivity_bt_button8)
    Button button8;
    @BindView(R.id.pinActivity_bt_button9)
    Button button9;
    @BindView(R.id.pinActivity_ib_buttonBackSpace)
    ImageButton buttonBackspace;

    private int pinCounter = 0;
    private String pinValue = "";
    private String pinTemp = "6969";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        ButterKnife.bind(this);

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pinCounter < 4) {
                    pinCounter++;
                    if(pinCounter == 1) {
                        pinCircle1.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "0";
                    }
                    else if(pinCounter == 2) {
                        pinCircle2.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "0";
                    }
                    else if(pinCounter == 3) {
                        pinCircle3.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "0";
                    }
                    else if(pinCounter == 4) {
                        pinCircle4.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "0";

                        if(!pinValue.equals(pinTemp)) {
                            accessDenied();
                        }
                        else {
                            accessGranted();
                        }
                    }
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pinCounter < 4) {
                    pinCounter++;
                    if(pinCounter == 1) {
                        pinCircle1.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "1";
                    }
                    else if(pinCounter == 2) {
                        pinCircle2.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "1";
                    }
                    else if(pinCounter == 3) {
                        pinCircle3.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "1";
                    }
                    else if(pinCounter == 4) {
                        pinCircle4.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "1";

                        if(!pinValue.equals(pinTemp)) {
                            accessDenied();
                        }
                        else {
                            accessGranted();
                        }
                    }
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pinCounter < 4) {
                    pinCounter++;
                    if(pinCounter == 1) {
                        pinCircle1.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "2";
                    }
                    else if(pinCounter == 2) {
                        pinCircle2.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "2";
                    }
                    else if(pinCounter == 3) {
                        pinCircle3.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "2";
                    }
                    else if(pinCounter == 4) {
                        pinCircle4.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "2";

                        if(!pinValue.equals(pinTemp)) {
                            accessDenied();
                        }
                        else {
                            accessGranted();
                        }
                    }
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pinCounter < 4) {
                    pinCounter++;
                    if(pinCounter == 1) {
                        pinCircle1.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "3";
                    }
                    else if(pinCounter == 2) {
                        pinCircle2.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "3";
                    }
                    else if(pinCounter == 3) {
                        pinCircle3.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "3";
                    }
                    else if(pinCounter == 4) {
                        pinCircle4.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "3";

                        if(!pinValue.equals(pinTemp)) {
                            accessDenied();
                        }
                        else {
                            accessGranted();
                        }
                    }
                }
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pinCounter < 4) {
                    pinCounter++;
                    if(pinCounter == 1) {
                        pinCircle1.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "4";
                    }
                    else if(pinCounter == 2) {
                        pinCircle2.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "4";
                    }
                    else if(pinCounter == 3) {
                        pinCircle3.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "4";
                    }
                    else if(pinCounter == 4) {
                        pinCircle4.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "4";

                        if(!pinValue.equals(pinTemp)) {
                            accessDenied();
                        }
                        else {
                            accessGranted();
                        }
                    }
                }
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pinCounter < 4) {
                    pinCounter++;
                    if(pinCounter == 1) {
                        pinCircle1.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "5";
                    }
                    else if(pinCounter == 2) {
                        pinCircle2.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "5";
                    }
                    else if(pinCounter == 3) {
                        pinCircle3.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "5";
                    }
                    else if(pinCounter == 4) {
                        pinCircle4.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "5";

                        if(!pinValue.equals(pinTemp)) {
                            accessDenied();
                        }
                        else {
                            accessGranted();
                        }
                    }
                }
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pinCounter < 4) {
                    pinCounter++;
                    if(pinCounter == 1) {
                        pinCircle1.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "6";
                    }
                    else if(pinCounter == 2) {
                        pinCircle2.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "6";
                    }
                    else if(pinCounter == 3) {
                        pinCircle3.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "6";
                    }
                    else if(pinCounter == 4) {
                        pinCircle4.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "6";

                        if(!pinValue.equals(pinTemp)) {
                            accessDenied();
                        }
                        else {
                            accessGranted();
                        }
                    }
                }
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pinCounter < 4) {
                    pinCounter++;
                    if(pinCounter == 1) {
                        pinCircle1.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "7";
                    }
                    else if(pinCounter == 2) {
                        pinCircle2.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "7";
                    }
                    else if(pinCounter == 3) {
                        pinCircle3.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "7";
                    }
                    else if(pinCounter == 4) {
                        pinCircle4.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "7";

                        if(!pinValue.equals(pinTemp)) {
                            accessDenied();
                        }
                        else {
                            accessGranted();
                        }
                    }
                }
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pinCounter < 4) {
                    pinCounter++;
                    if(pinCounter == 1) {
                        pinCircle1.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "8";
                    }
                    else if(pinCounter == 2) {
                        pinCircle2.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "8";
                    }
                    else if(pinCounter == 3) {
                        pinCircle3.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "8";
                    }
                    else if(pinCounter == 4) {
                        pinCircle4.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "8";

                        if(!pinValue.equals(pinTemp)) {
                            accessDenied();
                        }
                        else {
                            accessGranted();
                        }
                    }
                }
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pinCounter < 4) {
                    pinCounter++;
                    if(pinCounter == 1) {
                        pinCircle1.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "9";
                    }
                    else if(pinCounter == 2) {
                        pinCircle2.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "9";
                    }
                    else if(pinCounter == 3) {
                        pinCircle3.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "9";
                    }
                    else if(pinCounter == 4) {
                        pinCircle4.setImageDrawable(getResources().getDrawable(R.drawable.circle_filled));
                        pinValue = pinValue + "9";

                        if(!pinValue.equals(pinTemp)) {
                            accessDenied();
                        }
                        else {
                            accessGranted();
                        }
                    }
                }
            }
        });

        buttonBackspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pinCounter > 0) {
                    pinCounter--;
                    if(pinValue != null && !pinValue.equals("")) {
                        pinValue = pinValue.substring(0, pinValue.length() - 1);
                    }

                    if(pinCounter == 3) {
                        pinCircle4.setImageDrawable(getResources().getDrawable(R.drawable.circle_empty));
                    }
                    else if(pinCounter == 2) {
                        pinCircle3.setImageDrawable(getResources().getDrawable(R.drawable.circle_empty));
                    }
                    else if(pinCounter == 1) {
                        pinCircle2.setImageDrawable(getResources().getDrawable(R.drawable.circle_empty));
                    }
                    else if(pinCounter == 0) {
                        pinCircle1.setImageDrawable(getResources().getDrawable(R.drawable.circle_empty));
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PINActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void accessGranted() {
        finish();
    }

    private void accessDenied() {
        pinCounter = 0;
        pinValue = "";
        pinCircle1.setImageDrawable(getResources().getDrawable(R.drawable.circle_empty));
        pinCircle2.setImageDrawable(getResources().getDrawable(R.drawable.circle_empty));
        pinCircle3.setImageDrawable(getResources().getDrawable(R.drawable.circle_empty));
        pinCircle4.setImageDrawable(getResources().getDrawable(R.drawable.circle_empty));
        messageTxt.setVisibility(View.VISIBLE);

        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        assert vibrator != null;
        vibrator.vibrate(500);
    }
}
