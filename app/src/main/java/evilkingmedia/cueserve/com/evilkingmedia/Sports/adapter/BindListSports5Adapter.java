package evilkingmedia.cueserve.com.evilkingmedia.Sports.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import evilkingmedia.cueserve.com.evilkingmedia.Constant;
import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.Sports.SportsActivityServer5;
import evilkingmedia.cueserve.com.evilkingmedia.film.WebViewActivity;
import evilkingmedia.cueserve.com.evilkingmedia.model.SportsModel;

public class BindListSports5Adapter extends RecyclerView.Adapter<BindListSports5Adapter.myview> implements Filterable {
    private List<SportsModel> sportsModelUrlList;
    private List<SportsModel> sportsModelList;
    private List<SportsModel> sportsFilterModelList;
    Context context;
    String videoPath;
    private int itemposition;


    public class myview extends RecyclerView.ViewHolder {

        private CardView card_view;
        private ImageView imgcontent;
        private TextView txtMovieTitle, txtMovieRating, txtMovieYear, txtMovieDuration;
        private View view1, view2;


        public myview(View view) {
            super(view);
            card_view = view.findViewById(R.id.card_view);
            imgcontent = view.findViewById(R.id.imgcontent);
            txtMovieTitle = view.findViewById(R.id.txtMovieTitle);
            txtMovieRating = view.findViewById(R.id.txtMovieRating);
            txtMovieYear = view.findViewById(R.id.txtMovieYear);
            txtMovieDuration = view.findViewById(R.id.txtMovieDuration);
            view1 = view.findViewById(R.id.view1);
            view2 = view.findViewById(R.id.view2);
            view1.setVisibility(View.GONE);
            view2.setVisibility(View.GONE);
        }
    }

    public BindListSports5Adapter(List<SportsModel> sportsModelList, Context context, List<SportsModel> urlList) {
        this.sportsModelList = sportsModelList;
        this.context = context;
        this.sportsModelUrlList = urlList;
        this.sportsFilterModelList = sportsModelList;
    }

    @NonNull
    @Override
    public BindListSports5Adapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gridview_list, parent, false);

        return new BindListSports5Adapter.myview(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final BindListSports5Adapter.myview holder, final int position) {

        final SportsModel sportsModel = sportsModelList.get(position);

        holder.txtMovieTitle.setText(sportsModel.getTeam1());
        holder.txtMovieRating.setText(sportsModel.getTitle());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemposition = position;
                Intent i = new Intent(context, SportsActivityServer5.class);
                i.putExtra("url", sportsModelList.get(position).getUrl());
                i.putExtra("position", itemposition + 1);
                context.startActivity(i);
                //new prepareSportsUrl(Constant.SPORTSURL5+sportsModelList.get(position).getUrl()).execute();
            /*    Intent webIntent = new Intent(context, WebViewActivity.class);
                webIntent.putExtra("url", Constant.SPORTSURL5+sportsModelList.get(position).getUrl());
                context.startActivity(webIntent);*/

            }
        });

    }

    @Override
    public int getItemCount() {
        return sportsModelList.size();
    }

    private class prepareSportsUrl extends AsyncTask<String, Void, Void> {
        String mainurl;
        String urldata;
        boolean streaming = false;
        public prepareSportsUrl(String mainurl) {

            this.mainurl = mainurl;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(String... strings) {

            //Movie1
            try {


                Document doc  = Jsoup.connect(mainurl).timeout(10000).get();
                //For Categories
                Elements container = doc.select("div[class=container]");
                Elements div = container.select("div[class=embed-responsive embed-responsive-16by9]");
                Elements iframe = div.select("iframe[class=embed-responsive-item]");
                urldata= iframe.attr("src");



            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
        @Override
        protected void onPostExecute (Void result){
            // Set description into TextView

             Intent webIntent = new Intent(context, WebViewActivity.class);
                webIntent.putExtra("url", Constant.SPORTSURL5+urldata);
                context.startActivity(webIntent);






        }

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.equals("")) {
                    sportsModelList = sportsFilterModelList;
                } else {
                    List<SportsModel> filteredList = new ArrayList<>();
                    for (SportsModel row : sportsModelList) {
                        if (row.getTeam1().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    sportsModelList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = sportsModelList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                sportsModelList = (ArrayList<SportsModel>) filterResults.values;
                notifyDataSetChanged();


            }
        };
    }

}