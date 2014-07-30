package android.kota.com.dribbbleviewer;

/**
 * Created by takasakikota on 2014/07/30.
 */
public class Dribbble {
    private String image_url;
    private String title_text;
    private String player_text;

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setTitle_text(String title_text) {
        this.title_text = title_text;
    }

    public void setPlayer_text(String player_text) {
        this.player_text = player_text;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getTitle_text() {
        return title_text;
    }

    public String getPlayer_text() {
        return player_text;
    }
}
