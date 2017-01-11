package net.konyan.twofactor;

import android.app.Application;

import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by zeta on 1/9/17.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseMessaging.getInstance().subscribeToTopic("123456");
    }
}
