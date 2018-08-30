package evilkingmedia.cueserve.com.evilkingmedia.Sports.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.Sports.SportsActivityCatServer1;
import evilkingmedia.cueserve.com.evilkingmedia.Sports.SportsActivityEPG;
import evilkingmedia.cueserve.com.evilkingmedia.film.WebViewActivity;
import evilkingmedia.cueserve.com.evilkingmedia.model.SportsModel;

public class SportsEPGAdapter extends RecyclerView.Adapter<SportsEPGAdapter.myview>  {
    private List<SportsModel> sportsModelUrlList;
    private List<SportsModel> sportsModelList;
    Context context;
    String videoPath;
    private int itemposition;
    private Map<String, String> integerStringMap = new HashMap<String, String>();


    public class myview extends RecyclerView.ViewHolder {

        private CardView card_view;
        private TextView txtDate, txtTime,txtMovieTitle;
        private View view1,view2;
        private LinearLayout ll_bottom;

        public myview(View view) {
            super(view);
            card_view = view.findViewById(R.id.card_view);
            txtDate = view.findViewById(R.id.txtMovieTitle);
            txtTime = view.findViewById(R.id.txtMovieRating);
            view1 = view.findViewById(R.id.view1);
            view2 = view.findViewById(R.id.view2);
            ll_bottom = view.findViewById(R.id.ll_bottom);
            txtMovieTitle = view.findViewById(R.id.txtMovieTitle);
        }
    }

    public SportsEPGAdapter(List<SportsModel> sportsModelList, Context context, List<SportsModel> urlList) {
        this.sportsModelList = sportsModelList;
        this.context = context;
        this.sportsModelUrlList = urlList;
    }

    @NonNull
    @Override
    public SportsEPGAdapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gridview_list, parent, false);

        return new SportsEPGAdapter.myview(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SportsEPGAdapter.myview holder, final int position) {

        final SportsModel sportsModel = sportsModelList.get(position);

        holder.view1.setVisibility(View.GONE);
        holder.view2.setVisibility(View.GONE);
        holder.txtMovieTitle.setText(sportsModelList.get(position).getTitle());



        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemposition = position;


                    Intent sportCategoryIntent = new Intent(context, SportsActivityEPG.class);
                    sportCategoryIntent.putExtra("url", sportsModelList.get(position).getUrl());
                    context.startActivity(sportCategoryIntent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return sportsModelList.size();
    }

    private class prepareSportsUrl extends AsyncTask<String, Void, Void> {
        String mainurl;
        String url;
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
                Elements container = doc.select("center");
                Elements iframe = container.select("iframe");
                url = iframe.attr("src");

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
        @Override
        protected void onPostExecute (Void result){
            // Set description into TextView

            Intent webIntent = new Intent(context, WebViewActivity.class);
            webIntent.putExtra("url", url);
            context.startActivity(webIntent);





        }

    }
}



