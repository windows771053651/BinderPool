package com.hopeman.binder.remote;

import android.graphics.Bitmap;
import android.os.RemoteException;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hopeman.constraintlayout.remote.IWebActions;
import com.hopeman.constraintlayout.web.WebActionsCallback;

/**
 * Created by songhongyang419 on 2019/4/22.
 */
public class HopeWebViewClient extends WebViewClient {

    private WebView webView;

    private IWebActions iWebActions;

    public HopeWebViewClient(WebView webView, IWebActions iWebActions) {
        this.webView = webView;
        this.iWebActions = iWebActions;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return false;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        try {
            if (iWebActions != null) {
                iWebActions.executeWebActions(url, new WebActionsCallback.Stub() {
                    @Override
                    public void callback(int responseCode, String action) {

                    }

                    @Override
                    public void onAneLicaiCallback(int requestCode, String scheme) throws RemoteException {

                    }

                    @Override
                    public void getToken() throws RemoteException {

                    }

                    @Override
                    public void getUserInfo() throws RemoteException {

                    }

                    @Override
                    public void reload() throws RemoteException {
                    }
                });
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }
}
