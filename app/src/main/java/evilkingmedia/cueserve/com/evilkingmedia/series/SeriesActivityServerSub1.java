package evilkingmedia.cueserve.com.evilkingmedia.series;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.adapter.BindListAdapter;
import evilkingmedia.cueserve.com.evilkingmedia.adapter.BindListSeriesSubServer1;
import evilkingmedia.cueserve.com.evilkingmedia.film.FilmActivity;
import evilkingmedia.cueserve.com.evilkingmedia.model.MoviesModel;

public class SeriesActivityServerSub1 extends AppCompatActivity {
    private String url;
    private RecyclerView recyclerView;
    private BindListSeriesSubServer1 mAdapter;
    private List<MoviesModel> movieList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_film);
        url = getIntent().getStringExtra("url");
        recyclerView = findViewById(R.id.recyclerview);
         new prepareSeriesSubCatergory().execute();
    }

    private class prepareSeriesSubCatergory extends AsyncTask<Void, Void, Void> {
        String desc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                Document doc = Jsoup.connect(url).timeout(10000).get();

                Elements link = doc.select("div[class=contenedor]");
                //     Elements data = link.select("div[class=items]");
                Elements image = link.select("div[class=imagen]");
                Elements name = image.select("h2");
                Elements imagestr = image.select("img");
                for(int i=0;i<imagestr.size();i++) {
                    MoviesModel movie = new MoviesModel();
                    String img_str = imagestr.get(i).attr("src");
                    String name_str = name.get(i).text();
                    movie.setImage(img_str);
                    movie.setTitle(name_str);
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


            mAdapter = new BindListSeriesSubServer1(movieList, SeriesActivityServerSub1.this);
            // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(SeriesActivityServerSub1.this, 3);
            recyclerView.setLayoutManager(mLayoutManager);
            //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.invalidate();
            recyclerView.setAdapter(mAdapter);
        }
    }

}
