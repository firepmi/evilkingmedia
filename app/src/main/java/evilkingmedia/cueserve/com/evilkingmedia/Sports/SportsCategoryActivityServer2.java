package evilkingmedia.cueserve.com.evilkingmedia.Sports;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import evilkingmedia.cueserve.com.evilkingmedia.Constant;
import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.Sports.adapter.BindListSports2Adapter;
import evilkingmedia.cueserve.com.evilkingmedia.Sports.adapter.BindListSportsCategory2Adapter;
import evilkingmedia.cueserve.com.evilkingmedia.model.SportsModel;

public class SportsCategoryActivityServer2 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BindListSportsCategory2Adapter mAdapter;
    private List<SportsModel> sportsModelList = new ArrayList<>();
    private List<SportsModel> sportsModelUrlList = new ArrayList<>();
    private List<SportsModel> movieurlList = new ArrayList<>();
    private ArrayList<String> dateArrayList = new ArrayList<>();
    private ArrayList<String> timeArrayList = new ArrayList<>();
    private Map<String, String> urlStringMap = new HashMap<String, String>();
    private ProgressDialog mProgressDialog;
    private ImageView ivNext, ivPrev, ivUp, ivDown;
    private LinearLayout ll_search, ll_categories;
    private Button btnhome, btncategory;
    Boolean isNext;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sports_category_server2);
        recyclerView = findViewById(R.id.recyclerview);

        ivNext = findViewById(R.id.ivNext);
        ivPrev = findViewById(R.id.ivPrev);
        ivUp = findViewById(R.id.ivUp);
        ivDown = findViewById(R.id.ivDown);
        ll_search = findViewById(R.id.ll_search);
        ll_categories = findViewById(R.id.categories);
        btnhome = findViewById(R.id.btnhome);
        btncategory = findViewById(R.id.btncategory);
        isNext = false;

        ivNext.setVisibility(View.GONE);
        ivPrev.setVisibility(View.GONE);

        new prepareSportsData(Constant.SPORTSURL2).execute();

        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SportsCategoryActivityServer2.this, SportsActivityServer2.class);
                startActivity(i);
            }
        });

        btncategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private class prepareSportsData extends AsyncTask<String, Void, Void> {
        String url;

        public prepareSportsData(String nextPageUrl) {
            this.url = nextPageUrl;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(SportsCategoryActivityServer2.this);
            mProgressDialog.setTitle("");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {

            //Movie1
            try {
                // Connect to the web site
                Document doc = Jsoup.connect(url).timeout(10000).get();

                SportsModel moviesurl = new SportsModel();
                moviesurl.setCurrentUrl(url);
                movieurlList.add(moviesurl);
                //For Categories
                Elements container = doc.getElementsByClass("navbar-collapse collapse navbar-right").select("li[class=menu]");
                for (int i = 0; i < container.size(); i++) {

                    String title = container.get(i).getElementsByTag("a").text();
                    String url = container.get(i).getElementsByTag("a").attr("href");
                    Log.e("data", title + " " + url);
                    SportsModel sports = new SportsModel();
                    sports.setTitle(title);
                    sports.setUrl(url);
                    sportsModelList.add(sports);
                }


                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView

            mProgressDialog.dismiss();


            mAdapter = new BindListSportsCategory2Adapter(sportsModelList, SportsCategoryActivityServer2.this, sportsModelUrlList);
            // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(SportsCategoryActivityServer2.this, 2);
            recyclerView.setLayoutManager(mLayoutManager);
            //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.invalidate();
            recyclerView.setAdapter(mAdapter);


        }

    }
}

