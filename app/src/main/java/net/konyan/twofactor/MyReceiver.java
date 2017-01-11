package net.konyan.twofactor;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

public class MyReceiver extends BroadcastReceiver {

    public MyReceiver() {
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getStringExtra(Util.KEY_DATA_CODE);

        String action = intent.getAction();

        Log.d("act >>> ", action);

        if(Util.YES_ACTION.equals(action)) {
            Log.v("shuffTest","Pressed YES");

            if (data != null ){
                Log.d("action ", data);
                String s[] = data.split("_");
                writeSession(s[0], s[1], true);
            }

        } else if(Util.NO_ACTION.equals(action)) {
            Log.v("shuffTest","Pressed NO");
            if (data != null ){
                Log.d("action ", data);
                String s[] = data.split("_");
                writeSession(s[0], s[1], false);
            }

        } /*else if(NO_ACTION.equals(action)) {
            Log.v("shuffTest","Pressed MAYBE");
        }*/

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(01);
    }

    void writeSession(String code, String userID, boolean access){
        MySession session = new MySession(code, access, System.currentTimeMillis());

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("reciever", dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("receiver", databaseError.getDetails());
            }
        });
        mDatabase.child("session").child(userID).setValue(session);


    }

}
