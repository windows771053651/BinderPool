package com.hopeman.binder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.hopeman.binder.remote.server.BinderPoolService;
import com.hopeman.constraintlayout.remote.IBinderPool;

import java.util.concurrent.CountDownLatch;

/**
 * Created by songhongyang419 on 2019/7/22.
 */

public class BinderPool {

    public static final int BINDER_NONE = -1;

    public static final int BINDER_WEB = 1;

    private static volatile BinderPool instance;

    private Context context;

    private IBinderPool iBinderPool;

    private CountDownLatch connectBinderPoolCountDownLatch;

    private BinderPool(Context context) {
        this.context = context.getApplicationContext();
        bindService();
    }

    public static BinderPool getInstance(Context context) {
        if (instance == null) {
            synchronized(BinderPool.class) {
                if (instance == null) {
                    instance = new BinderPool(context);
                }
            }
        }

        return instance;
    }

    private synchronized void bindService() {
        connectBinderPoolCountDownLatch = new CountDownLatch(1);

        Intent intent = new Intent(context, BinderPoolService.class);
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);

        try {
            connectBinderPoolCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iBinderPool = IBinderPool.Stub.asInterface(service);

            connectBinderPoolCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iBinderPool = null;
            bindService();
        }
    };

    public IBinder queryBinder(int binderCode) {
        IBinder iBinder = null;
        if (iBinderPool != null) {
            try {
                iBinder = iBinderPool.queryBinder(binderCode);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        return iBinder;
    }
}
