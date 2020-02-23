package com.yb.peopleservice.view.activity.common;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.ToastUtils;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.IntentKeyConstant;
import com.yb.peopleservice.view.base.BaseActivity;

import butterknife.BindView;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.activity.BaseToolbarActivity;

/**
 * 公共的WebView访问界面
 * Created by sts on 2018/6/25.
 *
 * @author daichao
 */

public class WebViewActivity extends BaseToolbarActivity {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    /**
     * 请求的title
     */
    private String serverTitle;
    /**
     * 请求的url
     */
    private String serverUrl;

    private final String textStyle = "<header><meta name='viewport' content='initial-scale=1.0, " +
            "maximum-scale=2.0, minimum-scale=1.1, user-scalable=no'></header>";


    @Override
    public String getTitleName() {
        return serverTitle;
    }

    @Override
    public int contentViewResID() {
        return R.layout.e_actvity_web_view;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        serverTitle = getIntent().getStringExtra(IntentKeyConstant.WEB_VIEW_TITLE);
        serverUrl = getIntent().getStringExtra(IntentKeyConstant.WEB_VIEW_URL);
        if (!TextUtils.isEmpty(serverUrl)) {
            loadWebView();
        } else {
            ToastUtils.showShort("无效的地址链接");
        }
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }


    /**
     * 加载webView
     */
    public void loadWebView() {
        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示
        WebSettings webSetting = webView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);

        if (serverUrl.startsWith("http")) {
            webView.loadUrl(serverUrl);
        } else {
            webView.loadDataWithBaseURL(null, textStyle + serverUrl, "text/html", "UTF-8", null);
        }
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();
        //通过webView加载资源
        webView.setWebViewClient(new WebViewClient() {

            //重写该方法返回true实现webView加载资源
            @Override
            public boolean shouldOverrideUrlLoading(WebView webview, String url) {
//                webview.loadUrl(url);
//                closeIV.setVisibility(View.VISIBLE);
                return false;
            }

            //加载完成取消进度条
            @Override
            public void onPageFinished(WebView view, String url) {
                imgReset();
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar.setProgress(newProgress);//设置进度值
                }
            }
        });

    }

    private void imgReset() {
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
                "}" +
                "})()");
    }

    @Override
    public void clickLeftListener() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }


    /**
     * 点击返回上一页面而不是退出浏览器
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();

        }
        return true;
    }
}
