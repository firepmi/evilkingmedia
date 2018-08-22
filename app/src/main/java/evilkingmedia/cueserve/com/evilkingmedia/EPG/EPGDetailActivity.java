package evilkingmedia.cueserve.com.evilkingmedia.EPG;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import evilkingmedia.cueserve.com.evilkingmedia.Constant;
import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.model.MoviesModel;

public class EPGDetailActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BindListDetailsEPGAdpater mAdapter;
    private ProgressDialog mProgressDialog;
    private List<MoviesModel> epgModelList = new ArrayList<>();
    private ImageView ivNext,ivPrev, ivUp, ivDown;
    private LinearLayout ll_search,ll_categories;
    private String url;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_film);
        url = getIntent().getStringExtra("url");
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
        new  prepareEPGData().execute();
    }

    private class prepareEPGData extends AsyncTask<Void, Void, Void> {
        String desc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(EPGDetailActivity.this);
            mProgressDialog.setTitle("");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect(url).timeout(10000).userAgent("Mozilla").get();
                Elements main = doc.select("div[class=channel-list]");
                Elements c = main.select("div[class=channels]");
                Elements cat = c.select("div[class=programs]");
                Elements mElementUrl = cat.select("a");

                for(int i=0;i<mElementUrl.size();i++)
                {

                    String hour = mElementUrl.get(i).select("div[class=hour]").text();
                    String program_category = mElementUrl.get(i).select("div[class=program-category]").text();
                    String program_title = mElementUrl.get(i).select("div[class=program-title]").text();

                    MoviesModel moviesModel = new MoviesModel();
                    moviesModel.setTitle(program_title);
                    moviesModel.setDuration(hour);
                    moviesModel.setYear(program_category);
                    epgModelList.add(moviesModel);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView
            if (mProgressDialog != null) {
                if(mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
            }

            mAdapter = new BindListDetailsEPGAdpater(epgModelList, EPGDetailActivity.this);
            // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(EPGDetailActivity.this, 3);
            recyclerView.setLayoutManager(mLayoutManager);
            //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.invalidate();
            recyclerView.setAdapter(mAdapter);

        }
    }



}
