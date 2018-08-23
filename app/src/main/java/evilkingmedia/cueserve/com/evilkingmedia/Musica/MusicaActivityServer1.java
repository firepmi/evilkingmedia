package evilkingmedia.cueserve.com.evilkingmedia.Musica;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import evilkingmedia.cueserve.com.evilkingmedia.R;

public class MusicaActivityServer1 extends AppCompatActivity {

    private Button btnclick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musica_server1);

        btnclick = findViewById(R.id.btnclick);

        btnclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Bundle bnd = new Bundle();
                bnd.putString("path", "https://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_1mb.mp4");
                bnd.putString("name", "demo");*/
               /* Intent intent = new Intent();
                intent.setClassName("co.wuffy.player", "org.wuffy.videoplayer.WuffyMainActivity");*/
                //intent.putExtras(bnd);
                /*startActivity(intent);*/
            }
        });


    }

}
