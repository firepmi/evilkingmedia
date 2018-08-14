package evilkingmedia.cueserve.com.evilkingmedia.adapter;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import evilkingmedia.cueserve.com.evilkingmedia.Constant;
import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.film.FilmActivity;
import evilkingmedia.cueserve.com.evilkingmedia.film.WebViewActivity;
import evilkingmedia.cueserve.com.evilkingmedia.film.WebViewActivityServer3;
import evilkingmedia.cueserve.com.evilkingmedia.model.MoviesModel;
import evilkingmedia.cueserve.com.evilkingmedia.series.SeriesActivityServer1;
import evilkingmedia.cueserve.com.evilkingmedia.series.SeriesActivityServerSub1;


public class BindListSeriesAdapter extends RecyclerView.Adapter<BindListSeriesAdapter.myview> {
    private List<String> movielistFiltered;
    Context context;
    private ProgressDialog mProgressDialog;
    String videoPath;
    private int itemposition;
    BindListAdapter adapter;
    private ArrayList<String> arrayListString;

    public class myview extends RecyclerView.ViewHolder {

        private CardView card_view;
        private TextView txtMovieTitle;
        private View view1, view2;

        public myview(View view) {
            super(view);
            card_view = view.findViewById(R.id.card_view);
            txtMovieTitle = view.findViewById(R.id.txtMovieTitle);
            view1 = view.findViewById(R.id.view1);
            view2 = view.findViewById(R.id.view2);
            view1.setVisibility(View.GONE);
            view2.setVisibility(View.GONE);

        }
    }

    public BindListSeriesAdapter(ArrayList<String> arrayList, Context context) {

        this.context = context;
        this.arrayListString = arrayList;
    }

    @NonNull
    @Override
    public BindListSeriesAdapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gridview_list, parent, false);

        return new myview(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BindListSeriesAdapter.myview holder, final int position) {


        holder.txtMovieTitle.setText(arrayListString.get(position));


        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemposition = position;
                String url = arrayListString.get(itemposition).toString();
                String sub_url;
                switch (itemposition)
                {
                    case 0: url="";
                        break;
                    case 1: url="serie-tv-americane";
                        break;
                    case 2: url="serie-tv-italiane";
                        break;
                    case 15: url="legal-drama";
                        break;
                    case 17: url="medical-drama";
                        break;
                    case 21: url="prison-drama";
                        break;
                    case 24: url="teen-drama";
                        break;

                }
                if (arrayListString.get(itemposition).equalsIgnoreCase("western")) {
                    sub_url = "http://www.seriehd.me/" + url ;

                } else {
                    sub_url  =Constant.SERIESURL1_SUB + url ;

                }

                Intent subCategory = new Intent(context, SeriesActivityServerSub1.class);
                subCategory.putExtra("url",sub_url);
                context.startActivity(subCategory);


            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayListString.size();
    }

}
