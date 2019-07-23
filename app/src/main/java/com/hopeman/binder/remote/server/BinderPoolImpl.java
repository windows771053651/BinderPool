package com.hopeman.binder.remote.server;

import android.os.IBinder;
import android.os.RemoteException;

import com.hopeman.constraintlayout.remote.IBinderPool;
import com.hopeman.binder.BinderPool;
import com.hopeman.binder.remote.IWebBinder;

/**
 * Created by songhongyang419 on 2019/7/22.
 */
public class BinderPoolImpl extends IBinderPool.Stub {

    @Override
    public IBinder queryBinder(int binderCode) throws RemoteException {
        IBinder binder = null;
        switch (binderCode) {
            case BinderPool.BINDER_WEB:
                binder = new IWebBinder();
                break;
        }
        return binder;
    }

}
