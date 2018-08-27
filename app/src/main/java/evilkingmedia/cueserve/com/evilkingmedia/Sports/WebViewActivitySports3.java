package evilkingmedia.cueserve.com.evilkingmedia.Sports;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import evilkingmedia.cueserve.com.evilkingmedia.R;

public class WebViewActivitySports3 extends AppCompatActivity {
    String videoPath;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        int i = 0;
        videoPath = getIntent().getStringExtra("url");
        WebView webView = findViewById(R.id.web_view);

        mProgress = new ProgressDialog(WebViewActivitySports3.this);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        webView.setWebViewClient(new WebViewClient() {

            int i = 0;
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

              /*  if (i == 0 || i == 1) {
                    videoPath = url;
                    i++;
                }*/
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
               /* if (url.equals(videoPath)) {*/
                    view.loadUrl(url);

                //}

                return true; // then it is not handled by default action
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO show you progress image
                super.onPageStarted(view, url, favicon);
                mProgress.setMessage("Loading...");
                mProgress.show();

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgress.dismiss();
            }
        });

        webView.setWillNotCacheDrawing(true);
        webView.setDrawingCacheEnabled(false);
        webView.loadUrl(videoPath);

    }
}
