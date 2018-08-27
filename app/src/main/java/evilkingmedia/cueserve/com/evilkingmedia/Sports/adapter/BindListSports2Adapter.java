package evilkingmedia.cueserve.com.evilkingmedia.Sports.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import evilkingmedia.cueserve.com.evilkingmedia.Constant;
import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.Sports.SportsActivityCatServer1;
import evilkingmedia.cueserve.com.evilkingmedia.film.WebViewActivity;
import evilkingmedia.cueserve.com.evilkingmedia.model.SportsModel;

public class BindListSports2Adapter extends RecyclerView.Adapter<BindListSports2Adapter.myview>  {
private List<SportsModel> sportsModelUrlList;
private List<SportsModel> sportsModelList;
        Context context;
        String videoPath;
private int itemposition;



public class myview extends RecyclerView.ViewHolder {

    private CardView card_view;
    private TextView txtTeam1, txtTeam2, txtTime;


    public myview(View view) {
        super(view);
        card_view = view.findViewById(R.id.card_view);
        txtTeam1 = view.findViewById(R.id.txtTeam1);
        txtTeam2 = view.findViewById(R.id.txtTeam2);
        txtTime = view.findViewById(R.id.txtSportsTime);
    }
}

    public BindListSports2Adapter(List<SportsModel> sportsModelList, Context context, List<SportsModel> urlList) {
        this.sportsModelList = sportsModelList;
        this.context = context;
        this.sportsModelUrlList = urlList;
    }

    @NonNull
    @Override
    public BindListSports2Adapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sports_2, parent, false);

        return new BindListSports2Adapter.myview(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final BindListSports2Adapter.myview holder, final int position) {

        final SportsModel sportsModel = sportsModelList.get(position);

        holder.txtTime.setText(sportsModel.getTime());
        holder.txtTeam1.setText(sportsModel.getTeam1());
        holder.txtTeam2.setText(sportsModel.getTeam2());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemposition = position;
                new prepareSportsUrl(sportsModelList.get(position).getUrl()).execute();
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
            StringBuilder myName = new StringBuilder(mainurl);
            myName.setCharAt(0, ' ');
            String url = Constant.SPORTSURL2+myName;
            Document doc  = Jsoup.connect(url).timeout(10000).get();
            //For Categories
            Elements container = doc.select("div[class=container mtb]");
            Elements table = container.select("table[class=table table-striped]");
            Elements td = table.select("td[class=event-watch]");
            String a = td.select("a").attr("href");

            if(a == null || a.isEmpty())
            {
                streaming = false;
                urldata = table.select("td").text();
            }
            else
            {
                streaming = true;
                Elements mElementUrl = td.select("a");
                String url_str = mElementUrl.attr("href");
                String url1[] = url_str.split("javascript:window.open\\(");
                String url2[] = url1[1].split("\\)");
                String data = url2[0].replace("'","");
                urldata = data;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
    @Override
    protected void onPostExecute (Void result){
        // Set description into TextView
        if(streaming) {
            Intent webIntent = new Intent(context, WebViewActivity.class);
            String SpilString = urldata;
            String[] separated = urldata.split(",");
            for (String item : separated) {
                System.out.println("item = " + item);
            }
            urldata = separated[0];
            //"https://www.youtube.com/embed/yfOTGQNZSOY?autoplay=1"
            //http://www.youtube.com/embed/yfOTGQNZSOY?autoplay=1
            webIntent.putExtra("url", urldata);
            context.startActivity(webIntent);
        }
        else
        {
            Toast.makeText(context,urldata,Toast.LENGTH_LONG).show();
        }





    }

}
}
