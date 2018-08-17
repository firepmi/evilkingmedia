package evilkingmedia.cueserve.com.evilkingmedia.model;

import java.io.Serializable;

public class SportsModel implements Serializable{

    private String date;
    private String time;
    private String url;
    private String category;
    private String channel;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "SportsModel{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", url='" + url + '\'' +
                ", category='" + category + '\'' +
                ", channel='" + channel + '\'' +
                '}';
    }
}
