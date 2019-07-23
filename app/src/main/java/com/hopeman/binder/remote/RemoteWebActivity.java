package com.hopeman.binder.remote;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.hopeman.binder.BinderPool;
import com.hopeman.binder.App;
import com.hopeman.constraintlayout.R;
import com.hopeman.constraintlayout.remote.IWebActions;

/**
 * Created by songhongyang419 on 2018/12/24.
 */

public class RemoteWebActivity extends Activity {

    private LinearLayout mWebViewContainer;

    private WebView webView;

    private IWebActions iWebActions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_web);

        mWebViewContainer = findViewById(R.id.containerLL);
        init();
    }

    private void init() {
        webView = new WebView(this);
        LinearLayout.LayoutParams flp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mWebViewContainer.removeAllViews();
        mWebViewContainer.addView(webView, 0, flp);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
//        webSettings.setBuiltInZoomControls(true);
        //设置是否开启DOM存储API权限，默认false，未开启，设置为true，WebView能够使用DOM storage API
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getCacheDir().getAbsolutePath();
        //设置当前Application缓存文件路径，Application Cache API能够开启需要指定Application具备写入权限的路径
        webSettings.setAppCachePath(appCachePath);
        //设置Application缓存API是否开启，默认false，设置有效的缓存路径参考setAppCachePath(String path)方法
        webSettings.setAppCacheEnabled(true);
        webSettings.setSavePassword(false);
        //设置在WebView内部是否允许访问文件，默认允许访问。
        webSettings.setAllowFileAccess(true);
        //设置WebView是否使用其内置的变焦机制，该机制结合屏幕缩放控件使用，默认是false，不使用内置变焦机制。
        webSettings.setAllowContentAccess(true);
        webSettings.setUseWideViewPort(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        new Thread(() -> {
            // 获取Binder
            IBinder iBinder = BinderPool.getInstance(App.getInstance()).queryBinder(BinderPool.BINDER_WEB);
            iWebActions = IWebActions.Stub.asInterface(iBinder);

            h.sendEmptyMessageDelayed(0, 0);
        }).start();
    }

    Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (iWebActions != null) {
                webView.setWebViewClient(new HopeWebViewClient(webView, iWebActions));
                webView.loadUrl("https://www.jianshu.com/p/97c922d85152");
            }
        }
    };

    @Override
    protected void onDestroy() {
        clearWebviewCache();
        mWebViewContainer.removeAllViews();
        if (webView != null) {
            webView.removeAllViews();
            webView.destroy();
            webView = null;
        }
        mWebViewContainer = null;
        System.gc();
        super.onDestroy();
    }

    private void clearWebviewCache() {
        webView.clearCache(true);
        webView.clearFormData();
        webView.clearHistory();
    }
}
