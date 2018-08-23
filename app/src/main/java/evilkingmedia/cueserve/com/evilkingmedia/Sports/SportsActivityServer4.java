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
import evilkingmedia.cueserve.com.evilkingmedia.Sports.adapter.BindListSports4Adapter;
import evilkingmedia.cueserve.com.evilkingmedia.Sports.adapter.BindListSportsLinks4Adapter;
import evilkingmedia.cueserve.com.evilkingmedia.model.SportsModel;

public class SportsActivityServer4 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BindListSports4Adapter mAdapter;
    private BindListSportsLinks4Adapter mAdapter2;
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
    int position;
    private String previousurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sports_server4);
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
        position = getIntent().getIntExtra("position", 0);
        previousurl = getIntent().getStringExtra("url");

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
        if (previousurl == null) {
            new prepareSportsData(Constant.SPORTSURL4).execute();
        } else {
            new prepareSportsData(previousurl).execute();
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
            mProgressDialog = new ProgressDialog(SportsActivityServer4.this);
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


                if (previousurl == null) {
                    Elements title = doc.getElementsByClass("menutitle");

                    Log.e("title size", title.size() + "");
                    for (int i = 0; i < title.size(); i++) {
                        String stitle = title.get(i).text();
                        SportsModel sports = new SportsModel();
                        sports.setTitle(stitle);
                        sports.setUrl(Constant.SPORTSURL4);
                        sportsModelList.add(sports);
                    }
                } else {
                    position = position - 1;
                    Element menu = doc.select("span[class=submenu]").select("table").get(position);
                    Elements row = menu.select("tr");

                    for (int i = 0; i < row.size(); i++) {
                        Elements column = row.select("td").select("a");

                        for(int j = 0 ;j <column.size();j++){
                            String link = column.get(j).select("a").text();
                            String linkurl = column.get(j).select("a").attr("href");
                            Log.e("row", link + "" + linkurl);

                            SportsModel sports = new SportsModel();
                            sports.setTitle(link);
                            sports.setUrl(linkurl);
                            sportsModelList.add(sports);


                        }
                        break;

                    }


                }
                // System.out.print(row);


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


            if (previousurl == null) {
                mAdapter = new BindListSports4Adapter(sportsModelList, SportsActivityServer4.this, sportsModelUrlList);
                // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(SportsActivityServer4.this, 2);
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


            } else {

                mAdapter2 = new BindListSportsLinks4Adapter(sportsModelList, SportsActivityServer4.this, sportsModelUrlList);
                // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(SportsActivityServer4.this, 3);
                recyclerView.setLayoutManager(mLayoutManager);
                //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.invalidate();
                recyclerView.setAdapter(mAdapter2);
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

    }

    private class NextPagedata extends AsyncTask<String, Void, Void> {
        String NextPageUrl = null;
        String newurl = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(SportsActivityServer4.this);
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
            mProgressDialog = new ProgressDialog(SportsActivityServer4.this);
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
