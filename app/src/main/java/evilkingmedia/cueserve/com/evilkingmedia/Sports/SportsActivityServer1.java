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
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import evilkingmedia.cueserve.com.evilkingmedia.Constant;
import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.Sports.adapter.BindListSports1Adapter;
import evilkingmedia.cueserve.com.evilkingmedia.model.SportsModel;

public class SportsActivityServer1 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BindListSports1Adapter mAdapter;
    private List<SportsModel> sportsModelList = new ArrayList<>();
    private List<SportsModel> sportsModelUrlList = new ArrayList<>();
    private ArrayList<String> dateArrayList = new ArrayList<>();
    private ArrayList<String> timeArrayList = new ArrayList<>();
    private ArrayList<String> teamArrayList = new ArrayList<>();
    private Map<String, String> urlStringMap = new HashMap<String, String>();
    private ProgressDialog mProgressDialog;
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

        new prepareSportsData().execute();
    }

    private class prepareSportsData extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(SportsActivityServer1.this);
            mProgressDialog.setTitle("");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {

            //Movie1
            try {
               /* System.setProperty("http.proxyUser", "username");
                System.setProperty("http.proxyPassword", "password");
                System.setProperty("https.proxyHost", "127.0.0.1");
                System.setProperty("https.proxyPort", "3128");*/
                Document doc = Jsoup.connect(Constant.SPORTSURL1).timeout(10000).get();
                System.out.print(doc);


                //For Categories
                Elements container = doc.select("div[class=container]");
                Elements content = container.select("div[class=page-content]");

                Elements h1 = content.select("h1");
                Elements h2 = content.select("h2");
                Elements p = content.select("p");

                Log.e("size",h1.size() + " "+ h2.size());
                for(int i=0;i<h1.size();i++)
                {
                    if(i > 0) {
                        dateArrayList.add(h1.get(i).text());
                    }
                }
                for(int i=0;i<h2.size();i++)
                {
                    timeArrayList.add(h2.get(i).text());

                }

                for(int i = 0;i<p.size();i++)
                {
                    Elements span = p.get(i).select("span");

                    System.out.print(span);

                    Elements a = p.get(i).select("a");
                    for (int j = 0; j < span.size(); j++)
                    {
                        String team = null, url = null, category = null, channel = null;
                        SportsModel sportsModelUrl = new SportsModel();
                        //Log.e("data", a.get(j).attr("href"));

                       // if (j <= 5) {
                            Node node = span.get(j).nextSibling();
                            Log.e("Node", node.toString());
                            team = node.toString().replaceAll("&nbsp;", "");
                            Elements mElementUrl = a.get(j).select("a");
                            url = mElementUrl.attr("href");
                            category = String.valueOf(i);
                            channel = mElementUrl.text();

                        //}
                        /*else{
                            Elements mElementUrl = a.get(j).select("a");
                            url = mElementUrl.attr("href");
                            category = String.valueOf(i);


                        }*/

                        sportsModelUrl.setCategory(category);
                        sportsModelUrl.setUrl(url);
                        sportsModelUrl.setChannel(channel);
                        sportsModelUrl.setTeam1(team);
                        sportsModelUrlList.add(sportsModelUrl);


                    }

                }

                for(int data=0;data<timeArrayList.size();data++)
                {
                    SportsModel sportsModel = new SportsModel();
                    sportsModel.setDate(dateArrayList.get(data).toString());
                    sportsModel.setTime(timeArrayList.get(data).toString());

                    sportsModelList.add(sportsModel);
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


                    mAdapter = new BindListSports1Adapter(sportsModelList, SportsActivityServer1.this,  sportsModelUrlList);
                    // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(SportsActivityServer1.this, 3);
                    recyclerView.setLayoutManager(mLayoutManager);
                    //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.invalidate();
                    recyclerView.setAdapter(mAdapter);



            }

        }
    }

