package evilkingmedia.cueserve.com.evilkingmedia.EPG;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import evilkingmedia.cueserve.com.evilkingmedia.Constant;
import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.Sports.SportsActivityServer1;
import evilkingmedia.cueserve.com.evilkingmedia.Sports.adapter.BindListSports1Adapter;
import evilkingmedia.cueserve.com.evilkingmedia.adapter.BindListAdapter;
import evilkingmedia.cueserve.com.evilkingmedia.model.MoviesModel;
import evilkingmedia.cueserve.com.evilkingmedia.model.SportsModel;

public class EPGActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BindListEPGAdapter mAdapter;
    private ProgressDialog mProgressDialog;
    private List<MoviesModel> epgModelList = new ArrayList<>();
    private ImageView ivNext,ivPrev, ivUp, ivDown;
    private LinearLayout ll_search,ll_categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_film);
        recyclerView = findViewById(R.id.recyclerview);

        ivNext = findViewById(R.id.ivNext);
        ivPrev = findViewById(R.id.ivPrev);
        ivUp = findViewById(R.id.ivUp);
        ivDown = findViewById(R.id.ivDown);
        ll_search = findViewById(R.id.ll_search);
        ll_categories = findViewById(R.id.categories);

        ivPrev.setVisibility(View.GONE);
        ivUp.setVisibility(View.GONE);
        ivDown.setVisibility(View.GONE);
        ivNext.setVisibility(View.GONE);
        ll_search.setVisibility(View.GONE);
        ll_categories.setVisibility(View.GONE);
        new prepareEPGData().execute();
    }

    private class prepareEPGData extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(EPGActivity.this);
            mProgressDialog.setTitle("");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {

            //Movie1
            try {

                Document doc = Jsoup.connect(Constant.EPGURL).timeout(10000).userAgent("Mozilla").get();

                //For Categories
                Elements container = doc.select("div[class=container wrapper-skin-qn testata-guidatv]");
                Elements content = container.select("section[class=page-content]");

                Elements channels = content.select("div[class=channels]");
                Elements sections = channels.select("section[class=channel channel-thumbnail]");

                for(int i=0;i<sections.size();i++)
                {
                    Elements mElementUrl = sections.get(i).select("a");
                    String urlstr = mElementUrl.attr("href");
                    String url =  urlstr.substring(1);
                    String channelName = mElementUrl.select("div[class=channel-name]").text();

                    MoviesModel moviesModel = new MoviesModel();
                    moviesModel.setUrl(Constant.EPGURL+url);
                    moviesModel.setTitle(channelName);
                    epgModelList.add(moviesModel);
                }
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute (Void result){
            // Set description into TextView
            if (mProgressDialog != null) {
                if(mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
            }


            mAdapter = new BindListEPGAdapter(epgModelList, EPGActivity.this);
            // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(EPGActivity.this, 3);
            recyclerView.setLayoutManager(mLayoutManager);
            //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.invalidate();
            recyclerView.setAdapter(mAdapter);



        }

    }
}



