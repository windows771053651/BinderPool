package com.hopeman.binder.remote;

import com.hopeman.constraintlayout.remote.IWebActions;
import com.hopeman.constraintlayout.web.WebActionsCallback;

/**
 * Created by songhongyang419 on 2019/1/29.
 */
public class IWebBinder extends IWebActions.Stub {

    @Override
    public boolean executeWebActions(String action, WebActionsCallback callback) {
        try {
            // 这里是所有自定义协议分析入口，此处action转给URLRouter分析，再做具体功能分发，调用callback对应的方法
            callback.callback(200, action);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

}
