package net.konyan.twofactor;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by zeta on 1/9/17.
 */
@IgnoreExtraProperties
public class MySession {
    public String code;
    public boolean access;
    public long time;

    public MySession(){}

    public MySession(String code, boolean access, long time) {
        this.code = code;
        this.access = access;
        this.time = time;
    }
}
