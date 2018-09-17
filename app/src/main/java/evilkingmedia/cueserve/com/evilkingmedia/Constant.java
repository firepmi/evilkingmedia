package evilkingmedia.cueserve.com.evilkingmedia;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import evilkingmedia.cueserve.com.evilkingmedia.Musica.MusicActivityCategory;

public class Constant {

    public static String MOVIEURL1 = "http://www.altadefinizione01.zone";
    public static String MOVIEURL1_CINEMA = "/cinema/";
    public static String MOVIEURL1_SUB = "/sub-ita/";
    public static String MOVIEURL1_ATOZ = "/catalog/a/";
    //public static String MOVIEURL2 = "https://filmsenzalimiti.black/genere/film";
    public static String MOVIEURL2 = "https://filmstreaming.gratis/film-archive/";
    public static String MOVIEURL2_DELCINEMA = "https://filmsenzalimiti.black/prime-visioni/";
    public static String MOVIEURL2_FILMDEL2018 = "https://filmstreaming.gratis/film-archive?release-year=2018";
    public static String MOVIEURL2_FILMDEL2017 = "https://filmstreaming.gratis/film-archive?release-year=2017";
    public static String MOVIEURL2_FILMHD = "https://filmsenzalimiti.black/?s=%5BHD%5D";
    public static String MOVIEURL2_search = "https://filmstreaming.gratis/";
    public static String MOVIEURL3 = "https://www.cb01.zone/";
    public static String MOVIEURL3_HD = "https://www.cb01.zone/category/hd-alta-definizione/";
    public static String MOVIESERACH2 = "https://filmsenzalimiti.black/";
    public static String MOVIESERACH3 = " https://www.cb01.zone/cerca/";
    //public static String MOVIEURL4 = "https://ilgeniodellostreaming.eu/film/";
    //public static String MOVIEURL4 = "http://altadefinizione2.com/";
    public static String MOVIEURL4 = "https://streamfilm.club/";
//    public static String MOVIEURL4 = "https://cinema.popcorntv.it/serie/horror";
    public static String MOVIESERACH4 = "http://cinemagratis.online/";
    public static String MOVIEURL4_SUBITA = "https://ilgeniodellostreaming.eu/genere/sub-ita/";
    public static String SERIESURL1 = "http://www.seriehd.me/"; //for server4
    public static String SERIESURL1_SUB = "http://www.seriehd.me/serie-tv-streaming/";
    public static String SERIESURL2 = "https://streaminghd.fun/serietv/";
    //public static String SERIESURL3 = "https://ilgeniodellostreaming.eu/serietv/";
  //   public static String SERIESURL3 = "https://www.tantifilm.uno/serie-tv/";
  //   public static String SERIESURL3 = "https://www.cb01.zone/serietv/";
     public static String SERIESURL3 = "https://www.cb01.news/serietv/";
    public static String SPORTSBYDOCURL = "http://supermyspace.xyz/EKM/sportbydoc.m3u";
    public static String SERIESURL4 = "https://www.filmsenzalimiti.info/serie-tv/";   //for server1
    public static String SERIESURL4_search = "https://www.filmsenzalimiti.info/";
    public static String SPORTSURL1 = "http://hdstreams.club/";
   /* public static String SPORTSURL2 = "http://www.sports-stream.net/";
    public static String SERIESURL4 = "https://www.tantifilm.uno/serie-tv/";
    public static String SERIESURL4_search = "https://www.tantifilm.uno/";*/
    public static String SPORTSURL2 = "https://streamingsports.me/";
    //public static String EPGURL = "https://www.sorrisi.com/guidatv/canali-tv/";
    public static String EPGURL = "http://guidatv.quotidiano.net/";
    public static String SPORTSURL3 = "http://www.sportcategory.com/webmasters/webmaster_iframe.php";
    public static String SPORTSURL4 = "http://www.rojadiracta.me/";
    public static String SPORTSURL5 = "http://toplive.info/";
    public static String SPORTSEPGURL = "https://www.diretta.it/";

    public static String MUSICURL1 = "https://pastebin.com/raw/PUJb9caE/.m3u";
    public static String MUSICURL2 = "http://supermyspace.xyz/LISTE/canalimusicalidoc.m3u";
    public static String MUSICURL3 = "https://pastebin.com/raw/3SmUwbXB/.m3u";
    public static String MUSICURL4 = "http://playlist.autoiptv.net/music.php/.m3u";

    public static  String LIVETV1 = "http://supermyspace.xyz/EKM/ekm1.m3u";
    public static  String LIVETV2 = "http://supermyspace.xyz/EKM/ekm2.m3u";
    public static  String LIVETV3 = "http://supermyspace.xyz/EKM/ekm3.m3u";
    public static  String LIVETV4 = "http://supermyspace.xyz/EKM/ekm4.m3u";
    public static  String LIVETV5 = "http://supermyspace.xyz/EKM/ekm5.m3u";
    public static  String LIVETV6 = "http://supermyspace.xyz/EKM/ekm6.m3u";
    public static  String LIVETV7 = "http://supermyspace.xyz/EKM/ekm7.m3u";
    public static  String LIVETV8 = "http://supermyspace.xyz/EKM/ekm8.m3u";
    public static  String LIVETV9 = "http://supermyspace.xyz/EKM/ekm9.m3u";
    public static  String LIVETV10 = "http://supermyspace.xyz/EKM/ekm10.m3u";

    public static String METEOURL = "http://supermyspace.xyz/LISTE/meteoevil.m3u";

    public static String CARTOONURL1 = "http://www.animehdita.org/";
    public static String CARTOONURL2 = "https://www.tantifilm.gratis/watch-genre/cartoni-animati";

    public static boolean isCategory = false;
    public static String EVILKINGMOVIEURL = "https://www.evilkingmedia.com/film/";
    public static String EVILKINGSERIESURL = "https://www.evilkingmedia.com/serie-tv/";

    public static void playInWuffy(Context ctx, String url) {
        if(!appInstalledOrNot(ctx,"co.wuffy.player")){
            Toast.makeText(ctx, "Installed Wuffy Player To Play This Video",
                    Toast.LENGTH_LONG).show();
        }else{
            Bundle bnd = new Bundle();
            bnd.putString("path",url);
            //   bnd.putString("name", meteolist.get(position).getTitle());
            Intent intent = new Intent();
            intent.setClassName("co.wuffy.player", "org.wuffy.videoplayer.WuffyPlayer");
            intent.putExtras(bnd);
            ctx.startActivity(intent);
        }
    }

    public static boolean appInstalledOrNot(Context ctx,String uri) {
        PackageManager pm = ctx.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public static void openWVCapp(Context ctx, String url)
    {
        ClipboardManager clipboard = (ClipboardManager) ctx.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("",url);
        clipboard.setPrimaryClip(clip);
        Intent launchIntent = ctx.getPackageManager().getLaunchIntentForPackage("com.instantbits.cast.webvideo");
        ctx.startActivity( launchIntent );
    }

    public static void alertDialogWVC(final Context ctx, final String url)
    {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx);
        alertDialog.setTitle("");
        alertDialog.setMessage("Il tuo indirizzo internet è stato copiato. Ora puoi incollarlo su Web Video Caster");


        //This will not allow to close dialogbox until user selects an option
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Sì", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
               openWVCapp(ctx,url);
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //  Action for 'NO' Button

                dialog.cancel();
            }
        });

        //Creating dialog box
        AlertDialog alert = alertDialog.create();
        //Setting the title manually
        //alert.setTitle("AlertDialogExample");
        alert.show();

    }


}
