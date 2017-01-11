package net.konyan.twofactor;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static net.konyan.twofactor.Util.NO_ACTION;
import static net.konyan.twofactor.Util.YES_ACTION;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    final String LOG_TAG = MyFirebaseMessagingService.class.getSimpleName();

    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        Log.d(LOG_TAG, "From: " + remoteMessage.getFrom());
        Log.d(LOG_TAG, "Message Id: " + remoteMessage.getMessageId());
        Log.d(LOG_TAG, "Message: " + remoteMessage.getData().get("message"));
        //Log.d(LOG_TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        noti(remoteMessage.getData().get("title"),
                remoteMessage.getData().get("message"),
                remoteMessage.getData().get("code"));

    }

    private void noti(String title, String message, String code){

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setAutoCancel(true);

        Intent yesReceive = new Intent();
        yesReceive.putExtra(Util.KEY_DATA_CODE, code);
        yesReceive.setAction(YES_ACTION);
        PendingIntent pendingIntentYes =
                PendingIntent.getBroadcast(this, 12345, yesReceive, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.addAction(R.drawable.ic_action_name, "Yes", pendingIntentYes);

        Intent noReceive = new Intent();
        yesReceive.setAction(NO_ACTION);
        yesReceive.putExtra(Util.KEY_DATA_CODE, code);
        PendingIntent pendingIntentNo =
                PendingIntent.getBroadcast(this, 12345, noReceive, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.addAction(R.drawable.ic_action_no, "No", pendingIntentNo);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(01, mBuilder.build());

    }

}
