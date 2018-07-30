package evilkingmedia.cueserve.com.evilkingmedia.model;

public class MoviesModel {

    private String image;
    private String title;
    private String rating;
    private String year;
    private String duration;
    private String url;

    public MoviesModel() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "MoviesModel{" +
                "image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", rating='" + rating + '\'' +
                ", year='" + year + '\'' +
                ", duration='" + duration + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
