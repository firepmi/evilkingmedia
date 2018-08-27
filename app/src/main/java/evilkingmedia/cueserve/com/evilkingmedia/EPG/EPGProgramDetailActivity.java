package evilkingmedia.cueserve.com.evilkingmedia.EPG;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import evilkingmedia.cueserve.com.evilkingmedia.R;

public class EPGProgramDetailActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    String progarmdetails;
    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epgprogram_detail);

        tvInfo = findViewById(R.id.tvInfo);
        progarmdetails = getIntent().getStringExtra("pdetails");

        new prepareEPGData().execute();

    }

    private class prepareEPGData extends AsyncTask<String, Void, Void> {
        String displaydata;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(EPGProgramDetailActivity.this);
            mProgressDialog.setTitle("");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {

            //Movie1
            try {

                Document doc = Jsoup.connect(progarmdetails).timeout(10000).userAgent("Mozilla").get();

                //For Categories

                //System.out.print(doc);

                Elements data = doc.getElementsByClass("program-description");

                 displaydata = data.text();
                Log.e("display", displaydata);

                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
            }
            tvInfo.setText(displaydata);


        }

    }
}