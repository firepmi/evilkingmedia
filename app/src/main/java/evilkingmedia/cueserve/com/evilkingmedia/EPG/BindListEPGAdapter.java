package evilkingmedia.cueserve.com.evilkingmedia.EPG;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import evilkingmedia.cueserve.com.evilkingmedia.Constant;
import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.adapter.BindListAdapter;
import evilkingmedia.cueserve.com.evilkingmedia.film.WebViewActivity;
import evilkingmedia.cueserve.com.evilkingmedia.model.MoviesModel;

public class BindListEPGAdapter extends RecyclerView.Adapter<BindListEPGAdapter.myview> implements Filterable {
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
        private TextView txtMovieTitle;
        private View view1, view2;
        private LinearLayout ll_bottom;

        public myview(View view) {
            super(view);
            card_view = view.findViewById(R.id.card_view);
            imgcontent = view.findViewById(R.id.imgcontent);
            txtMovieTitle = view.findViewById(R.id.txtMovieTitle);
            view1 = view.findViewById(R.id.view1);
            view2 = view.findViewById(R.id.view2);
            ll_bottom = view.findViewById(R.id.ll_bottom);

        }
    }

    public BindListEPGAdapter(List<MoviesModel> moviesList, Context context) {
        this.moviesList = moviesList;
        this.context = context;
        this.movielistFiltered = moviesList;
    }

    @NonNull
    @Override
    public BindListEPGAdapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gridview_list, parent, false);

        return new BindListEPGAdapter.myview(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BindListEPGAdapter.myview holder, final int position) {

        final MoviesModel movie = moviesList.get(position);

        Picasso.with(context).load(Constant.MOVIEURL1+movie.getImage()).into(holder.imgcontent);
        holder.txtMovieTitle.setText(movie.getTitle());
        holder.txtMovieTitle.setGravity(View.TEXT_ALIGNMENT_CENTER);
        holder.view1.setVisibility(View.GONE);
        holder.view2.setVisibility(View.GONE);
        holder.ll_bottom.setVisibility(View.GONE);
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemposition = position;
                Intent detailIntent = new Intent(context,EPGDetailActivity.class);
                detailIntent.putExtra("url",(moviesList.get(itemposition).getUrl()));
                context.startActivity(detailIntent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    private class prepareEPGData extends AsyncTask<Void, Void, Void> {
        String desc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                Document doc = Jsoup.connect(moviesList.get(itemposition).getUrl()).timeout(10*10000).userAgent("Mozilla").get();
                Elements main = doc.select("div[class=gtv-page]");
                Elements c = main.select("div[class=gtv-wrapper gtv-sitewidth]");
                Elements cat = c.select("main[class=gtv-content]");
                Elements category = cat.select("section[class=gtv-mod21]");
                Elements articles = category.select("arcticle[class=gtv-program   ]");

                for(int i=0;i<articles.size();i++)
                {
                    Elements a = articles.get(i).select("a");
                    String img = a.get(0).select("img").attr("src");

                    Elements program_title = articles.get(i).select("gtv-program-title");
                    String title = program_title.select("a").text();
                    Log.e("","");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView


        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.equals("")) {
                    moviesList = movielistFiltered;
                } else {
                    List<MoviesModel> filteredList = new ArrayList<>();
                    for (MoviesModel row : movielistFiltered) {
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    moviesList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = moviesList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                moviesList = (ArrayList<MoviesModel>) filterResults.values;
                notifyDataSetChanged();


            }
        };
    }



}
