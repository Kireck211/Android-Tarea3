package mx.iteso.app;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import mx.iteso.app.databinding.ActivityPrivacyPolicyBinding;
import mx.iteso.app.interfaces.WebAppInterface;

public class ActivityPrivacyPolicy extends AppCompatActivity {

    private static final String FILE_ROUTE = "file:///android_asset/PrivacyPolicy.html";

    private ActivityPrivacyPolicyBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_privacy_policy);

        mBinding.wbPrivacyPolicy.loadUrl(FILE_ROUTE);
        WebSettings webSettings = mBinding.wbPrivacyPolicy.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mBinding.wbPrivacyPolicy.addJavascriptInterface(new WebAppInterface(this), "Android");
        mBinding.wbPrivacyPolicy.setWebViewClient(new MyWebViewClient());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mBinding.wbPrivacyPolicy.canGoBack()) {
            mBinding.wbPrivacyPolicy.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    
    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                ActivityPrivacyPolicy.this.startActivity(intent);
                return true;
            }
            return false;
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }
    }
}
