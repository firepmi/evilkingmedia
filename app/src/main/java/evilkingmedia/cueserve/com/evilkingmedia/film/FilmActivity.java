package evilkingmedia.cueserve.com.evilkingmedia.film;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.adapter.BindListAdapter;
import evilkingmedia.cueserve.com.evilkingmedia.model.MoviesModel;

public class FilmActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BindListAdapter mAdapter;
    private List<MoviesModel> movieList = new ArrayList<>();
    private ProgressDialog mProgressDialog;
    private int elementsize;
    int corePoolSize = 60;
    int maximumPoolSize = 80;
    int keepAliveTime = 10;

    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);

        recyclerView = findViewById(R.id.recyclerview);

        new prepareMovieData().execute();
    }


    private class prepareMovieData extends AsyncTask<Void, Void, Void> {
        String desc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(FilmActivity.this);
            mProgressDialog.setTitle("");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document doc = Jsoup.connect("http://www.altadefinizione01.zone/").get();
                // Using Elements to get the Meta data
                Elements images = doc.select("#dle-content img[src~=(?i)\\.(png|jpe?g|gif)]");
                elementsize = images.size();

                for (int i = 0; i < elementsize; i++) {
                    Elements mElementImage = doc.select("#dle-content img[src~=(?i)\\.(png|jpe?g|gif)]");
                    String Image = mElementImage.attr("src");
                    Log.d("image", Image);
                    MoviesModel movie = new MoviesModel(Image);
                    movieList.add(movie);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView

            mAdapter = new BindListAdapter(movieList, FilmActivity.this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            mProgressDialog.dismiss();
        }
    }



}

