package evilkingmedia.cueserve.com.evilkingmedia.Sports;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import evilkingmedia.cueserve.com.evilkingmedia.Constant;
import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.Sports.adapter.BindListSports1Adapter;
import evilkingmedia.cueserve.com.evilkingmedia.Sports.adapter.SportsEPGAdapter;
import evilkingmedia.cueserve.com.evilkingmedia.model.SportsModel;

public class SportsActivityEPG extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SportsEPGAdapter mAdapter;
    private ProgressDialog mProgressDialog;
    private List<SportsModel> sportsModelList = new ArrayList<>();
    String url ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_film);
        recyclerView = findViewById(R.id.recyclerview);
        if(getIntent().hasExtra("url")) {
            url = getIntent().getStringExtra("url");
        }
        new prepareSportsData().execute();
    }

    private class prepareSportsData extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(SportsActivityEPG.this);
            mProgressDialog.setTitle("");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {
            if(url == null) {

                try {

                    Document doc = Jsoup.connect(Constant.SPORTSEPGURL).timeout(10000).get();
                    System.out.print(doc);


                    //For Categories
                    Elements container = doc.select("div[class=rt-container]");
                    Elements content = container.select("ul[class=gf-menu l1 ]");
                    Elements li = content.select("li");
                    Elements div1 = li.get(0).select("div[class=dropdown columns-1 ]");
                    Elements div = div1.select("div[class=module-content]");
                    Elements divdata = div.select("div");


                    for (int i = 1; i < divdata.size(); i++) {

                        String title = divdata.get(i).select("a").text();
                        String url = divdata.get(i).select("a").attr("href");
                        SportsModel sportsModelUrl = new SportsModel();
                        sportsModelUrl.setTitle(title);
                        sportsModelUrl.setUrl(url);
                        sportsModelList.add(sportsModelUrl);


                    }

                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                try {

                    Document doc = Jsoup.connect(url).timeout(10000).get();
                    System.out.print(doc);


                    //For Categories
                    Elements container = doc.select("div[class=jtable-main-container]");
                    Elements content = container.select("table[class=jtable]");
                    Elements li = content.select("li");
                    Elements div1 = li.get(0).select("div[class=dropdown columns-1 ]");
                    Elements div = div1.select("div[class=module-content]");
                    Elements divdata = div.select("div");


                    for (int i = 1; i < divdata.size(); i++) {

                        String title = divdata.get(i).select("a").text();
                        String url = divdata.get(i).select("a").attr("href");
                        SportsModel sportsModelUrl = new SportsModel();
                        sportsModelUrl.setTitle(title);
                        sportsModelUrl.setUrl(url);
                        sportsModelList.add(sportsModelUrl);


                    }

                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
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


            mAdapter = new SportsEPGAdapter(sportsModelList, SportsActivityEPG.this,  sportsModelList);
            // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(SportsActivityEPG.this, 3);
            recyclerView.setLayoutManager(mLayoutManager);
            //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.invalidate();
            recyclerView.setAdapter(mAdapter);



        }

    }
}


