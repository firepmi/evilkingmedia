package evilkingmedia.cueserve.com.evilkingmedia.film;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.MediaController;
import android.widget.VideoView;

import evilkingmedia.cueserve.com.evilkingmedia.R;

public class WebViewActivity extends AppCompatActivity {
    String videoPath;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        videoPath = getIntent().getStringExtra("url");
        VideoView videoView = (VideoView) findViewById(R.id.web_view);
        MediaController mc = new MediaController(this);
        mc.setAnchorView(videoView);
        mc.setMediaPlayer(videoView);
        Uri video = Uri.parse(videoPath);
        videoView.setMediaController(mc);
        videoView.setVideoURI(video);
        videoView.start();
    }
}
