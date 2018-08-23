package evilkingmedia.cueserve.com.evilkingmedia.model;

public class MeteoModel {

    private String Title;
    private String Url;

    public MeteoModel() {
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    @Override
    public String toString() {
        return "MeteoModel{" +
                "Title='" + Title + '\'' +
                ", Url='" + Url + '\'' +
                '}';
    }
}
