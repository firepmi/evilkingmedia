package evilkingmedia.cueserve.com.evilkingmedia.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.Sports.WebViewActivitySports3;
import evilkingmedia.cueserve.com.evilkingmedia.film.FilmActivityServer4;
import evilkingmedia.cueserve.com.evilkingmedia.film.WebViewActivity;
import evilkingmedia.cueserve.com.evilkingmedia.film.WebViewActivityServer3;
import evilkingmedia.cueserve.com.evilkingmedia.film.WebViewActivityServer4;
import evilkingmedia.cueserve.com.evilkingmedia.model.MoviesModel;
import evilkingmedia.cueserve.com.evilkingmedia.series.WebViewActivitySeries2;

public class BindListAdapterServer4 extends RecyclerView.Adapter<BindListAdapterServer4.myview> {
    private List<MoviesModel> movielistFiltered;
    private List<MoviesModel> moviesList;
    Context context;
    private ProgressDialog mProgressDialog;
    String videoPath;
    private int itemposition;
    BindListAdapter adapter;


    public class myview extends RecyclerView.ViewHolder {

        private CardView card_view;
        private ImageView imgcontent;
        private TextView txtMovieTitle, txtMovieRating, txtMovieYear, txtMovieDuration;
        View view1, view2;

        public myview(View view) {
            super(view);
            card_view = view.findViewById(R.id.card_view);
            imgcontent = view.findViewById(R.id.imgcontent);
            txtMovieTitle = view.findViewById(R.id.txtMovieTitle);
            view1 = view.findViewById(R.id.view1);
            view2 = view.findViewById(R.id.view2);
            txtMovieRating = view.findViewById(R.id.txtMovieRating);
            txtMovieYear = view.findViewById(R.id.txtMovieYear);
            txtMovieDuration = view.findViewById(R.id.txtMovieDuration);
            view1.setVisibility(View.GONE);
            view2.setVisibility(View.GONE);
            txtMovieRating.setVisibility(View.GONE);
            txtMovieYear.setVisibility(View.GONE);
            txtMovieDuration.setVisibility(View.GONE);

        }
    }

    public BindListAdapterServer4(List<MoviesModel> moviesList, Context context) {
        this.moviesList = moviesList;
        this.context = context;
        this.movielistFiltered = moviesList;
    }

    @NonNull
    @Override
    public BindListAdapterServer4.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gridview_list, parent, false);

        return new myview(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BindListAdapterServer4.myview holder, final int position) {

        final MoviesModel movie = moviesList.get(position);

        if (movie.getImage() == "") {
            holder.imgcontent.setImageResource(R.color.colorWhite);
            //Picasso.with(context).load(R.drawable.ic_image).into(holder.imgcontent);
        } else {
            Picasso.with(context).load(movie.getImage()).into(holder.imgcontent);
        }

        holder.txtMovieTitle.setText(movie.getTitle());
       /* holder.txtMovieRating.setText(movie.getRating());
        holder.txtMovieYear.setText(movie.getYear());
        holder.txtMovieDuration.setText(movie.getDuration());*/

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent webIntent = new Intent(context, WebViewActivity.class);
                webIntent.putExtra("url", "https://speedvideo.net/embed-nalqx7svhsmq-607x360.html");
                context.startActivity(webIntent);*/
                itemposition = position;
                new prepareMovieData().execute();

            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    private class prepareMovieData extends AsyncTask<Void, Void, Void> {
        String desc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setTitle("");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {


                Document doc = Jsoup.connect(moviesList.get(itemposition).getUrl()).timeout(10000).maxBodySize(0).get();
                Elements data = doc.select("div[class=leftC]");
                Elements video = data.select("div[class=filmcontent]").select("div[class=filmicerik]");
                String src = video.select("iframe").attr("src");
              /*  Elements iframe = doc.getElementsByTag("iframe");


                Log.e("body", src);
*/
                videoPath =  src;


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
            Intent webIntent = new Intent(context, WebViewActivityServer4.class);
           // webIntent.putExtra("url", "https://streaminghd.fun/embed/kiBCh34WrAM/Accusato_speciale_%5BHD%5D_%282012%29_Bluray_1080p.mp4");
            webIntent.putExtra("url", videoPath);
            context.startActivity(webIntent);
        }
    }

}
