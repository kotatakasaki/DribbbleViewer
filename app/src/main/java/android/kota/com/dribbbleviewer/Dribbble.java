package android.kota.com.dribbbleviewer;

/**
 * Created by takasakikota on 2014/07/30.
 */
public class Dribbble {
    private String mImageUrl; //画像のURL
    private String mTitleText; //タイトル名
    private String mPlayerText; //プレイヤー名

    public void setImage_url(String image_url) {
        this.mImageUrl = image_url;
    }

    public void setTitle_text(String title_text) {
        this.mTitleText = title_text;
    }

    public void setPlayer_text(String player_text) {
        this.mPlayerText = player_text;
    }

    public String getImage_url() {
        return mImageUrl;
    }

    public String getTitle_text() {
        return mTitleText;
    }

    public String getPlayer_text() {
        return mPlayerText;
    }
}
