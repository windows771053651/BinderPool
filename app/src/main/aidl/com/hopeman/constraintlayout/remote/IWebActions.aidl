// IWebActions.aidl
package com.hopeman.constraintlayout.remote;

// Declare any non-default types here with import statements
import com.hopeman.constraintlayout.web.WebActionsCallback;

interface IWebActions {
    boolean executeWebActions(String action, in WebActionsCallback callback);
}
