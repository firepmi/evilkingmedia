package evilkingmedia.cueserve.com.evilkingmedia.Musica;

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
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import evilkingmedia.cueserve.com.evilkingmedia.Constant;
import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.adapter.MusicAdapterServer1;
import evilkingmedia.cueserve.com.evilkingmedia.model.MeteoModel;

public class MusicaActivityServer4 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MusicAdapterServer1 mAdapter;
    private ProgressDialog mProgressDialog;
    ImageView ivUp, ivDown;
    int corePoolSize = 60;
    String Currenturl;
    android.support.v7.widget.SearchView search;
    int i = 0;
    String Category = "";
    int maximumPoolSize = 80;
    int keepAliveTime = 10;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);
    ArrayList<String> allURls = new ArrayList<String>();
    ArrayList<String> allURl = new ArrayList<String>();
    ArrayList<String> alltitle = new ArrayList<String>();
    private List<MeteoModel> meteolist = new ArrayList<>();
    SearchView searchview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_musica_server1);

        mProgressDialog = new ProgressDialog(MusicaActivityServer4.this);
        //  setContentView(R.layout.gridview_list);
        recyclerView = findViewById(R.id.recyclerview);
        ivUp = findViewById(R.id.ivUp);
        ivDown = findViewById(R.id.ivDown);

        searchview = findViewById(R.id.searchView);
        new prepareMovieData(Constant.MUSICURL4).execute();

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

        searchview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                searchview.setFocusable(true);
                searchview.setFocusableInTouchMode(true);
                return false;
            }
        });

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

    private class prepareMovieData extends AsyncTask<String, Void, Void> {
        String titles;
        String title;
        String title1;
        String ftitle;
        String mainurl;
        String videourl;
        String ptitle;

        public prepareMovieData(String mainurl) {
            this.mainurl = mainurl;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.setTitle("");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {

            try {

                URL urls = new URL(mainurl);
                BufferedReader in = new BufferedReader(new InputStreamReader(urls
                        .openStream()));
                String str;
                while ((str = in.readLine()) != null) {

                    allURls.add(str);


                }
                String url = null;
                for (int i = 0; i < allURls.size(); i++) {
                    url = allURls.get(i).toString();

                    if (allURls.get(i).toString().contains("#EXTINF") || allURls.get(i).toString().contains("#EXTM3U")) {

                    }
                    else{
                        allURl.add(allURls.get(i).toString());
                    }
                }

                for (int i = 0; i < allURls.size(); i++) {

                    Iterator<String> j = allURls.iterator();
                    while (j.hasNext())
                    {
                        String s = j.next();
                        if (s == null || s.isEmpty())
                        {
                            j.remove();
                        }
                    }
                    if (i <= allURls.size() - 2) {
                        if (allURls.get(i + 1).toString().contains("#EXTINF")) {

                            String[] separated = allURls.get(i + 1).toString().split(",");
                            for (String item : separated) {
                                System.out.println("item = " + item);
                            }
                            title = separated[1];

                           /* String[] separated1 = title.split("\\[");
                            for (String item : separated1) {
                                System.out.println("item = " + item);
                            }
                            title1 = separated1[0];
*/
                           /* String[] separated2 = title1.split("\\[");
                            for (String item : separated2) {
                                System.out.println("item = " + item);
                            }
                            ftitle = separated2[0];*/
                            alltitle.add(title);


                        } else {
                            Log.e("string", "not title");
                        }

                    } else {
                        if (allURls.get(i).toString().contains("http")) {

                            Log.e("string", "not title");
                        } else {
                            String[] separated = allURls.get(i).toString().split(",");
                            for (String item : separated) {
                                System.out.println("item = " + item);
                            }
                            title = separated[1];
                        }

                    }


                }

                for (int i = 0; i < allURl.size(); i++) {
                    MeteoModel meteo = new MeteoModel();
                    ptitle = alltitle.get(i).toString();
                    meteo.setTitle(ptitle);
                    Iterator<String> j = allURl.iterator();
                    while (j.hasNext())
                    {
                        String s = j.next();
                        if (s == null || s.isEmpty())
                        {
                            j.remove();
                        }
                    }
                    meteo.setUrl(allURl.get(i));
                    meteolist.add(meteo);
                }

                //meteo.setUrl(videourl);

                Log.e("data", meteolist + "");
                Log.e("size", alltitle.size() + "" + "" + allURl.size() + "");
                in.close();

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

            mAdapter = new MusicAdapterServer1(meteolist, MusicaActivityServer4.this);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(MusicaActivityServer4.this, 2);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.invalidate();
            recyclerView.setAdapter(mAdapter);
        }
    }

}


