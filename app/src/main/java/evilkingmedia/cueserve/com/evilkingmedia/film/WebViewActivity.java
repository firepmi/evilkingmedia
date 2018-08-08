package evilkingmedia.cueserve.com.evilkingmedia.film;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import evilkingmedia.cueserve.com.evilkingmedia.R;

public class WebViewActivity extends AppCompatActivity {
    String videoPath;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        int i = 0;
        videoPath = getIntent().getStringExtra("url");
        WebView webView = findViewById(R.id.web_view);


      /*  webView.setVideoURI(Uri.parse(videoPath));
        webView.setBufferSize(2048);
        webView.setMediaController(null);
        webView.start();*/

        /*final MediaController mediacontroller = new MediaController(this);
        mediacontroller.setAnchorView(webView);


        webView.setMediaController(mediacontroller);
        webView.setVideoURI(Uri.parse(videoPath));
        webView.requestFocus();*/

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        webView.setWebViewClient(new WebViewClient(){


            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                if (url.equals(videoPath)) {
                    view.loadUrl(url);
                }

                return true; // then it is not handled by default action
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO show you progress image

                super.onPageStarted(view, url, favicon);
                mProgress = new ProgressDialog(WebViewActivity.this);
                mProgress.setMessage("Loading...");
                mProgress.show();

            }

            @Override

            public void onPageFinished(WebView view, String url) {


                super.onPageFinished(view, url);
                mProgress.dismiss();
            }
        });
       /* webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                // TODO show you progress image
                if(url == videoPath){
                    super.onPageStarted(view, url, favicon);
                    mProgress = new ProgressDialog(WebViewActivity.this);
                    mProgress.setMessage("Loading...");
                    mProgress.show();
                }
                else{
                    Log.e("error link","different url");
                }



            }
            @Override

            public void onPageFinished(WebView view, String url) {

                if(url == videoPath){
                    super.onPageFinished(view, url);
                    mProgress.dismiss();
                }
                else{
                    Log.e("error link","different url");
                }


            }

        });*/
        webView.setWillNotCacheDrawing(true);
        webView.setDrawingCacheEnabled(false);
        webView.loadDataWithBaseURL(videoPath, "", "video/divx", "UTF-8", "");
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
