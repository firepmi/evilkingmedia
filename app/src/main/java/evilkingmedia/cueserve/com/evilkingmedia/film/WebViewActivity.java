package evilkingmedia.cueserve.com.evilkingmedia.film;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.MediaController;
import android.widget.VideoView;

import evilkingmedia.cueserve.com.evilkingmedia.R;

public class WebViewActivity extends AppCompatActivity {
    String videoPath;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        videoPath = getIntent().getStringExtra("url");
        WebView webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                // TODO show you progress image
                super.onPageStarted(view, url, favicon);
               /* mProgress = new ProgressDialog(WebViewActivity.this);
                mProgress.setMessage("Loading...");
                mProgress.show();*/


            }
            @Override

            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);
            //    mProgress.dismiss();

            }

        });
        webView.setWillNotCacheDrawing(true);
        webView.setDrawingCacheEnabled(false);
        webView.loadUrl(videoPath);

       /* VideoView videoView = (VideoView) findViewById(R.id.web_view);
        MediaController mc = new MediaController(this);
        mc.setAnchorView(videoView);
        mc.setMediaPlayer(videoView);
        Uri video = Uri.parse(videoPath);
        videoView.setMediaController(mc);
        videoView.setVideoURI(video);
        videoView.start();*/
    }
}
