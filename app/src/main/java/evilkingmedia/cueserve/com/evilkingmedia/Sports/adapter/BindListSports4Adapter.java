package evilkingmedia.cueserve.com.evilkingmedia.Sports.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.Sports.SportsActivityServer4;
import evilkingmedia.cueserve.com.evilkingmedia.model.SportsModel;

public class BindListSports4Adapter extends RecyclerView.Adapter<BindListSports4Adapter.myview> {
    private List<SportsModel> sportsModelUrlList;
    private List<SportsModel> sportsModelList;
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

    public BindListSports4Adapter(List<SportsModel> sportsModelList, Context context, List<SportsModel> urlList) {
        this.sportsModelList = sportsModelList;
        this.context = context;
        this.sportsModelUrlList = urlList;
    }

    @NonNull
    @Override
    public BindListSports4Adapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gridview_list, parent, false);

        return new BindListSports4Adapter.myview(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final BindListSports4Adapter.myview holder, final int position) {

        final SportsModel sportsModel = sportsModelList.get(position);

        holder.txtMovieTitle.setText(sportsModel.getTitle());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemposition = position;
                Intent i = new Intent(context, SportsActivityServer4.class);
                i.putExtra("url", sportsModelList.get(position).getUrl());
                i.putExtra("position", itemposition + 1);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return sportsModelList.size();
    }

}
