package evilkingmedia.cueserve.com.evilkingmedia.model;

public class MoviesModel {

    private String image;

    public MoviesModel(String image) {
       /* this.image = image;*/
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "{" +
                "image='" + image + '\'' +
                '}';
    }
}
