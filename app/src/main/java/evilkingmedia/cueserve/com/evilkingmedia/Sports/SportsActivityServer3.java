package evilkingmedia.cueserve.com.evilkingmedia.Sports;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
import evilkingmedia.cueserve.com.evilkingmedia.Sports.adapter.BindListSportsPlay3Adapter;
import evilkingmedia.cueserve.com.evilkingmedia.model.SportsModel;

public class SportsActivityServer3 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BindListSports3Adapter mAdapter;
    private BindListSportsPlay3Adapter mAdapter2;
    private List<SportsModel> sportsModelList = new ArrayList<>();
    private List<SportsModel> sportsModelUrlList = new ArrayList<>();
    private List<SportsModel> movieurlList = new ArrayList<>();
    private ArrayList<String> dateArrayList = new ArrayList<>();
    private ArrayList<String> timeArrayList = new ArrayList<>();
    private Map<String, String> urlStringMap = new HashMap<String, String>();
    private ProgressDialog mProgressDialog;
    android.support.v7.widget.SearchView search;
    private ImageView ivNext, ivPrev, ivUp, ivDown;
    private LinearLayout ll_search, ll_categories;
    Boolean isNext, Category = false;
    String returnvalue = "demo";
    int i = 0, position;
    private String previousurl, selectedid, categoryid;
    Button btnhome, btncategory;
    SearchView searchview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sports_server3);
        recyclerView = findViewById(R.id.recyclerview);
        btnhome = findViewById(R.id.btnhome);
        btncategory = findViewById(R.id.btncategory);
        ivNext = findViewById(R.id.ivNext);
        ivPrev = findViewById(R.id.ivPrev);
        ivUp = findViewById(R.id.ivUp);
        ivDown = findViewById(R.id.ivDown);
        ll_search = findViewById(R.id.ll_search);
        ll_categories = findViewById(R.id.categories);
        isNext = false;
        ivNext.setVisibility(View.GONE);
        ivPrev.setVisibility(View.GONE);
        categoryid = getIntent().getStringExtra("categoryid");
        if (categoryid == null) {
            categoryid = "t1";
        }
        selectedid = categoryid + "-content";
        previousurl = getIntent().getStringExtra("url");
        position = getIntent().getIntExtra("position", 0);

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

        ivUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.smoothScrollBy(0, -200);
            }
        });

        ivDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.smoothScrollBy(0, 200);
            }
        });

        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new prepareSportsData(Constant.SPORTSURL3).execute();
            }
        });

        btncategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Category = true;
                new prepareSportsData(Constant.SPORTSURL3).execute();
            }
        });
        searchview = findViewById(R.id.searchView);

        searchview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                searchview.setFocusable(true);
                searchview.setFocusableInTouchMode(true);
                return false;
            }
        });
        if (previousurl == null) {
            new prepareSportsData(Constant.SPORTSURL3).execute();
        } else {
            new prepareSportsData(previousurl).execute();
        }

        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                mAdapter.getFilter().filter(query);
                return false;
            }
        });


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

            String urldata = null;
            //Movie1
            try {
                // Connect to the web site
                doc = Jsoup.connect(url).timeout(20000).get();
                try {

                    SportsModel moviesurl = new SportsModel();
                    moviesurl.setCurrentUrl(url);
                    movieurlList.add(moviesurl);
                    //For Categories

                    //System.out.print(doc);

                    if (Category == true) {

                        sportsModelList.clear();
                        Elements tabsdata = doc.select("#tabs").select("a");

                        for (int i = 0; i < tabsdata.size(); i++) {

                            String id = tabsdata.get(i).getElementsByTag("a").attr("id");
                            String title = tabsdata.get(i).getElementsByTag("a").text();
                            SportsModel sports = new SportsModel();
                            sports.setTitle(title);
                            sports.setId(id);
                            sportsModelList.add(sports);
                        }

                        System.out.print(tabsdata);


                    } else {
                        if (previousurl == null) {
                            sportsModelList.clear();
                            Elements data = doc.getElementsByClass("content").select("#" + selectedid);

                            System.out.print(data);

                            Elements rowdata = data.select(".slide");

                            for (int i = 0; i < rowdata.size(); i++) {

                                String title = rowdata.get(i).getElementsByClass("name").text();
                                SportsModel sports = new SportsModel();
                                sports.setTitle(title);
                                // sports.setTime(time);
                                sports.setUrl(url);
                                //Log.e("title", time + " " + title);
                                sportsModelList.add(sports);
                            }

                        } else {
                            sportsModelList.clear();
                            Elements data = doc.getElementsByClass("slide");

                            Elements videourl = data.get(position).getElementsByClass("slide_content").select(".link");

                            Log.e("sizeofurl", videourl.size() + "");

                            for (int i = 0; i < videourl.size(); i++) {

                                String url = videourl.get(i).getElementsByTag("a").attr("onclick");
                                String SpilString = url;
                                String[] separated = url.split("\\(");
                                for (String item : separated) {
                                    System.out.println("item = " + item);
                                }
                                url = separated[1];
                                String[] separated1 = url.split("\\)");
                                for (String item : separated1) {
                                    System.out.println("item = " + item);
                                }
                                url = separated1[0];
                                url = url.replace("'", "");
                                SportsModel sports = new SportsModel();
                                sports.setUrl(url);
                                sports.setTitle(videourl.get(i).getElementsByTag("a").text());
                                sportsModelList.add(sports);

                            }


                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView

            mProgressDialog.dismiss();

            if (previousurl == null || Category == true) {

                mAdapter = new BindListSports3Adapter(sportsModelList, SportsActivityServer3.this, sportsModelUrlList);
                // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(SportsActivityServer3.this, 3);
                recyclerView.setLayoutManager(mLayoutManager);
                //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.invalidate();
                recyclerView.setAdapter(mAdapter);
                //ivNext.setVisibility(View.VISIBLE);

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


            } else {
                mAdapter2 = new BindListSportsPlay3Adapter(sportsModelList, SportsActivityServer3.this, sportsModelUrlList);
                // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(SportsActivityServer3.this, 3);
                recyclerView.setLayoutManager(mLayoutManager);
                //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.invalidate();
                recyclerView.setAdapter(mAdapter2);
                //ivNext.setVisibility(View.VISIBLE);

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
