package com.mycillin.user.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mycillin.user.R;
import com.mycillin.user.activity.ChatActivity;
import com.mycillin.user.activity.MainActivity;
import com.mycillin.user.util.SessionManager;

import java.util.Map;

import static com.mycillin.user.activity.ChatActivity.KEY_FLAG_CHAT_PATIENT_ID;
import static com.mycillin.user.activity.ChatActivity.KEY_FLAG_CHAT_PATIENT_NAME;
import static com.mycillin.user.activity.ChatActivity.KEY_FLAG_CHAT_USER_ID;
import static com.mycillin.user.activity.ChatActivity.KEY_FLAG_CHAT_USER_NAME;

public class FireBaseMessagingServices extends FirebaseMessagingService {
    private final String EXTRA_NOTIFICATION_REQUEST = "REQUEST";
    private final String EXTRA_NOTIFICATION_CHAT = "CHAT";
    private final String EXTRA_NOTIFICATION_BLAST = "BLAST";

    private static final String TAG = "firebase";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("#8#8#", "onMessageReceived: "+remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("#8#8#", "onMessageReceived: payload"+remoteMessage.getData());
            handleNow();

            /* Check if data needs to be processed by long running job */
            /*if (true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                //scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }*/
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("#8#8#", "onMessageReceived: "+remoteMessage.getNotification().getBody());

            if (remoteMessage.getNotification().getBody() != null) {
                String messageFbase = remoteMessage.getNotification().getBody();
                Map<String, String> getData = remoteMessage.getData();
                if (messageFbase.contains("Request")) {
                    sendNotification(remoteMessage.getNotification().getBody(), "", EXTRA_NOTIFICATION_REQUEST,getData);
                } else if (messageFbase.contains("Chat")) {
                    sendNotification(remoteMessage.getNotification().getBody(), "", EXTRA_NOTIFICATION_CHAT,getData);
                } else {
                    sendNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle(), EXTRA_NOTIFICATION_BLAST,getData);
                }
            }
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /*private void scheduleJob() {
        // [START dispatch_job]
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job myJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class)
                .setTag("my-job-tag")
                .build();
        dispatcher.schedule(myJob);
        // [END dispatch_job]
    }*/

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d("#8#8#", "handleNow: ");
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody, String titleMessage, String flagFrom,Map<String, String> getData) {
        SessionManager sessionManager = new SessionManager(this);
        switch (flagFrom) {
            case EXTRA_NOTIFICATION_REQUEST:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_ONE_SHOT);

                String channelId = getString(R.string.default_notification_channel_id);
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder =
                        new NotificationCompat.Builder(this, channelId)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle(sessionManager.getUserFullName())
                                .setContentText("You Have New Request")
                                .setAutoCancel(true)
                                .setSound(defaultSoundUri)
                                .setContentIntent(pendingIntent);

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                if (notificationManager != null) {
                    notificationManager.notify(0, notificationBuilder.build());
                }
                break;
            case EXTRA_NOTIFICATION_CHAT:
                Intent intentChat = new Intent(this, ChatActivity.class);
                intentChat.putExtra(KEY_FLAG_CHAT_PATIENT_ID, getData.get(KEY_FLAG_CHAT_PATIENT_ID));
                intentChat.putExtra(KEY_FLAG_CHAT_PATIENT_NAME, getData.get(KEY_FLAG_CHAT_PATIENT_NAME));
                intentChat.putExtra(KEY_FLAG_CHAT_USER_ID, getData.get(KEY_FLAG_CHAT_USER_ID));
                intentChat.putExtra(KEY_FLAG_CHAT_USER_NAME, getData.get(KEY_FLAG_CHAT_USER_NAME));
                intentChat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntentChat = PendingIntent.getActivity(this, 0, intentChat,
                        PendingIntent.FLAG_ONE_SHOT);

                String channelIdChat = getString(R.string.default_notification_channel_id);
                Uri defaultSoundUriChat = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilderChat =
                        new NotificationCompat.Builder(this, channelIdChat)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle(sessionManager.getUserFullName())
                                .setContentText("You Have New Chat")
                                .setAutoCancel(true)
                                .setSound(defaultSoundUriChat)
                                .setContentIntent(pendingIntentChat);

                NotificationManager notificationManagerChat =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                if (notificationManagerChat != null) {
                    notificationManagerChat.notify(0, notificationBuilderChat.build());
                }
                break;
            case EXTRA_NOTIFICATION_BLAST:
                Intent intentBlast = new Intent(this, MainActivity.class);
                intentBlast.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntentBlast = PendingIntent.getActivity(this, 0, intentBlast,
                        PendingIntent.FLAG_ONE_SHOT);

                String channelIdBlast = getString(R.string.default_notification_channel_id);
                Uri defaultSoundUriBlast = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilderBlast =
                        new NotificationCompat.Builder(this, channelIdBlast)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle(titleMessage)
                                .setContentText(messageBody)
                                .setAutoCancel(true)
                                .setSound(defaultSoundUriBlast)
                                .setContentIntent(pendingIntentBlast);

                NotificationManager notificationManagerBlast =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                if (notificationManagerBlast != null) {
                    notificationManagerBlast.notify(0, notificationBuilderBlast.build());
                }
                break;
        }
    }
}
