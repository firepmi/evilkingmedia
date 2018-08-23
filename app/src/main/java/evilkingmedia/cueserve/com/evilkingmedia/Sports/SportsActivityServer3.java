package evilkingmedia.cueserve.com.evilkingmedia.Sports;

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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import evilkingmedia.cueserve.com.evilkingmedia.Constant;
import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.Sports.adapter.BindListSports3Adapter;
import evilkingmedia.cueserve.com.evilkingmedia.model.SportsModel;

public class SportsActivityServer3 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BindListSports3Adapter mAdapter;
    private List<SportsModel> sportsModelList = new ArrayList<>();
    private List<SportsModel> sportsModelUrlList = new ArrayList<>();
    private List<SportsModel> movieurlList = new ArrayList<>();
    private ArrayList<String> dateArrayList = new ArrayList<>();
    private ArrayList<String> timeArrayList = new ArrayList<>();
    private Map<String, String> urlStringMap = new HashMap<String, String>();
    private ProgressDialog mProgressDialog;
    private ImageView ivNext, ivPrev, ivUp, ivDown;
    private LinearLayout ll_search, ll_categories;
    Boolean isNext;
    int i = 0;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sports_server3);
        recyclerView = findViewById(R.id.recyclerview);
        ivNext = findViewById(R.id.ivNext);
        ivPrev = findViewById(R.id.ivPrev);
        ivUp = findViewById(R.id.ivUp);
        ivDown = findViewById(R.id.ivDown);
        ll_search = findViewById(R.id.ll_search);
        ll_categories = findViewById(R.id.categories);
        isNext = false;
        ivNext.setVisibility(View.GONE);
        ivPrev.setVisibility(View.GONE);

        url = getIntent().getStringExtra("url");

        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                new NextPagedata().execute();
            }
        });

        ivPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i--;
                new PreviousPagedata().execute();
            }
        });
        if (url == null) {
            new prepareSportsData(Constant.SPORTSURL3).execute();
        } else {
            new prepareSportsData(url).execute();
        }


    }

    private class prepareSportsData extends AsyncTask<String, Void, Void> {
        Document doc = null;
        String url;

        public prepareSportsData(String nextPageUrl) {
            this.url = nextPageUrl;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(SportsActivityServer3.this);
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
                doc = Jsoup.connect(url).timeout(10000).get();

                SportsModel moviesurl = new SportsModel();
                moviesurl.setCurrentUrl(url);
                movieurlList.add(moviesurl);
                //For Categories

                Elements data = doc.getElementsByClass("lshpanel");
                Elements table = data.select("table");
                Elements row = table.select("tr");
                for (int i = 0; i < row.size(); i++) {

                    String title = row.get(i).getElementsByClass("lshevent").text();
                    String time = row.get(i).getElementsByClass("lshstart_time").text();
                    Log.e("title", time);
                    SportsModel sports = new SportsModel();
                    sports.setTitle(title);
                    sports.setTime(time);
                    sportsModelList.add(sports);
                }
                //System.out.print(row);
               /* Elements container = doc.select("div[class=container mtb]");
                // Elements content = container.select("div[class=col-lg-9]");
                Elements tbody = container.select("tbody");
                Elements tr = tbody.select("tr");



                for(int i=0;i<tr.size();i++)
                {
                    String url = tr.get(i).attr("data-href").toString();
                    String td_time = tr.get(i).select("td[class=event-time]").text();
                    String td_team1 = tr.get(i).select("td[class=event-home]").text();
                    String td_team2 = tr.get(i).select("td[class=event-away]").text();
                    SportsModel sportsModel = new SportsModel();
                    sportsModel.setTime(td_time);
                    sportsModel.setTeam1(td_team1);
                    sportsModel.setTeam2(td_team2);
                    sportsModel.setUrl(url);
                    sportsModelList.add(sportsModel);
                }
*/

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


            mAdapter = new BindListSports3Adapter(sportsModelList, SportsActivityServer3.this, sportsModelUrlList);
            // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(SportsActivityServer3.this, 3);
            recyclerView.setLayoutManager(mLayoutManager);
            //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.invalidate();
            recyclerView.setAdapter(mAdapter);
            ivNext.setVisibility(View.VISIBLE);

            try {
                if (isNext == true) {
                    ivPrev.setVisibility(View.VISIBLE);
                } else {
                    ivPrev.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                ivPrev.setVisibility(View.GONE);
            }
            if (i != 0) {

                ivPrev.setVisibility(View.VISIBLE);
            } else {
                ivPrev.setVisibility(View.GONE);
            }


            Elements main = doc.select("ul[class=pagination]");

            if (main.size() == 0) {
                ivNext.setVisibility(View.GONE);
            } else {
                ivNext.setVisibility(View.VISIBLE);
            }


        }

    }

    private class NextPagedata extends AsyncTask<String, Void, Void> {
        String NextPageUrl = null;
        String newurl = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(SportsActivityServer3.this);
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
                //  isNext = true;
                String result;
                Document doc = null;
                for (int i = 0; i < movieurlList.size(); i++) {

                    newurl = movieurlList.get(i).getCurrentUrl();
                }
                doc = Jsoup.connect(newurl).timeout(10000).get();
                Elements main = doc.select("div[class=container mtb]");
                for (Element urls : main.select("ul[class=pagination]")) {
                    //perform your data extractions here.
                    for (Element urlss : urls.getElementsByTag("li")) {
                        for (Element nexturl : urlss.getElementsByTag("a")) {
                            result = urlss != null ? urlss.absUrl("href") : null;
                            Log.d("Urls", String.valueOf(nexturl));
                            NextPageUrl = nexturl.attr("href");
                            Log.d("Urls", NextPageUrl);
                            SportsModel sportsModel = new SportsModel();
                            sportsModel.setUrl(NextPageUrl);
                            sportsModelList.add(sportsModel);
                        }
                    }
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
                mProgressDialog.dismiss();
            }
            sportsModelList.clear();
            ivPrev.setVisibility(View.VISIBLE);
            NextPageUrl = "https://streamingsports.me/" + NextPageUrl;
            new prepareSportsData(NextPageUrl).execute();
        }

        ;
    }


    private class PreviousPagedata extends AsyncTask<String, Void, Void> {
        String PrevPageUrl = null;
        String newurl = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(SportsActivityServer3.this);
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
                isNext = true;
                String result;
                Document doc = null;
                for (int i = 0; i < movieurlList.size(); i++) {

                    newurl = movieurlList.get(i).getCurrentUrl();
                }
                doc = Jsoup.connect(newurl).timeout(10000).get();
                movieurlList.clear();
                for (Element urls : doc.getElementsByClass("page_nav")) {
                    //perform your data extractions here.
                    for (Element urlss : urls.getElementsByTag("i")) {
                        for (Element nexturl : urlss.getElementsByTag("a")) {
                            result = urlss != null ? urlss.absUrl("href") : null;
                            Log.d("Urls", String.valueOf(nexturl));
                            PrevPageUrl = nexturl.attr("href");
                            Log.d("Urls", PrevPageUrl);
                            SportsModel movieurl = new SportsModel();
                            movieurl.setCurrentUrl(PrevPageUrl);
                            movieurlList.add(movieurl);
                            break;
                        }
                        break;
                    }
                    break;
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
                mProgressDialog.dismiss();
            }
            sportsModelList.clear();
            ivPrev.setVisibility(View.VISIBLE);
            PrevPageUrl = "https://streamingsports.me/" + PrevPageUrl;
            new prepareSportsData(PrevPageUrl).execute();
        }

        ;
    }
}
