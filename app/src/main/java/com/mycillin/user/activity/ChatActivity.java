package com.mycillin.user.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.mycillin.user.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity {

    public static String KEY_FLAG_CHAT_PATIENT_ID = "CHAT_PATIENT_ID";
    public static String KEY_FLAG_CHAT_PATIENT_NAME = "CHAT_PATIENT_NAME";
    public static String KEY_FLAG_CHAT_USER_ID = "CHAT_USER_ID";
    public static String KEY_FLAG_CHAT_USER_NAME = "CHAT_USER_NAME";
    public static String KEY_FLAG_CHAT_BOOKING_ID = "CHAT_BOOKING_ID";

    @BindView(R.id.chat_ll_reference1)
    LinearLayout layoutRef1;
    @BindView(R.id.chat_rl_reference2)
    RelativeLayout layoutRef2;
    @BindView(R.id.chat_IV_sendButton)
    ImageView ivSend;
    @BindView(R.id.chat_ET_messageArea)
    EditText etMessageArea;
    @BindView(R.id.chat_scrollView)
    ScrollView svScroll;

    private String patientID;
    private String patientName;
    private String doctorID;
    private String doctorName;
    private String bookingID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        patientID = getIntent().getStringExtra(KEY_FLAG_CHAT_PATIENT_ID);
        patientName = getIntent().getStringExtra(KEY_FLAG_CHAT_PATIENT_NAME);
        doctorID = getIntent().getStringExtra(KEY_FLAG_CHAT_USER_ID);
        doctorName = getIntent().getStringExtra(KEY_FLAG_CHAT_USER_NAME);
        bookingID = getIntent().getStringExtra(KEY_FLAG_CHAT_BOOKING_ID);

        svScroll.post(new Runnable() {
            @Override
            public void run() {
                svScroll.fullScroll(View.FOCUS_DOWN);
            }
        });

        final DatabaseReference user2 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://android-mycillin-1507307522195.firebaseio.com/messages/" + patientID + "_" + doctorID + "");
        final DatabaseReference user1 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://android-mycillin-1507307522195.firebaseio.com/messages/" + doctorID + "_" + patientID + "");

        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageText = etMessageArea.getText().toString();
                if (!messageText.equals("")) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", patientName);
                    user1.push().setValue(map);
                    user2.push().setValue(map);
                    etMessageArea.setText("");
                }
            }
        });

        user1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Map<String, Object> map = (Map<String, Object>)dataSnapshot.getValue(Map.class);

                GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {
                };
                Map<String, String> map = dataSnapshot.getValue(genericTypeIndicator);
                String message = map.get("message");
                String userName = map.get("user");

                if (userName.equals(patientName)) {
                    addMessageBox("You:-\n" + message, 1);
                } else {
                    addMessageBox("" + doctorName + " :-\n" + message, 2);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //do nothing
    }

    public void addMessageBox(String message, int type) {
        TextView textView = new TextView(ChatActivity.this);
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if (type == 1) {
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.bubble_in);
        } else {
            lp2.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.bubble_out);
        }
        textView.setLayoutParams(lp2);
        layoutRef1.addView(textView);
        svScroll.fullScroll(View.FOCUS_DOWN);
    }
}
