// WebActionsCallbacks.aidl
package com.hopeman.constraintlayout.web;

interface WebActionsCallback {
    void callback(int responseCode, String action);
    void onAneLicaiCallback(int requestCode, String scheme);
    void getToken();
    void getUserInfo();
    void reload();
}
