package com.hopeman.binder.remote.server;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;

/**
 * Created by songhongyang419 on 2019/1/29.
 */
public class BinderPoolService extends Service {

    private BinderPoolImpl binderPool;

    @Override
    public void onCreate() {
        binderPool = new BinderPoolImpl();
    }

    @Override
    public IBinder onBind(Intent intent) {
        int check = checkCallingOrSelfPermission("com.hopeman.permission.ACCESS_BINDER_POOL_SERVICE");
        if (check == PackageManager.PERMISSION_DENIED) {
            return null;
        }

        return binderPool;
    }
}
