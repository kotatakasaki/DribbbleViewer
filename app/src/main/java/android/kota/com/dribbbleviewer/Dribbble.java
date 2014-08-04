package android.kota.com.dribbbleviewer;

/**
 * Created by takasakikota on 2014/07/30.
 */
public class Dribbble {
    private String mImageUrl; //画像のURL
    private String mPlayerImageUrl; //プレイヤー画像のURL
    private String mTitleText; //タイトル名
    private String mPlayerText; //プレイヤー名
    private String mViewsText; //ビュー数
    private String mCommentsText; //コメント数
    private String mLikesText; //ライク数

    public void setImage_url(String image_url) {
        this.mImageUrl = image_url;
    }

    public void setPlayerImageUrl(String mPlayerImageUrl) {
        this.mPlayerImageUrl = mPlayerImageUrl;
    }

    public void setTitle_text(String title_text) {
        this.mTitleText = title_text;
    }

    public void setPlayer_text(String player_text) {
        this.mPlayerText = player_text;
    }

    public void setmViewsText(String mViewsText) {
        this.mViewsText = mViewsText;
    }

    public void setmCommentsText(String mCommentsText) {
        this.mCommentsText = mCommentsText;
    }

    public void setmLikesText(String mLikesText) {
        this.mLikesText = mLikesText;
    }

    public String getImage_url() {
        return mImageUrl;
    }

    public String getmPlayerImageUrl() {
        return mPlayerImageUrl;
    }

    public String getTitle_text() {
        return mTitleText;
    }

    public String getPlayer_text() {
        return mPlayerText;
    }

    public String getmViewsText() {
        return mViewsText;
    }

    public String getmCommentsText() {
        return mCommentsText;
    }

    public String getmLikesText() {
        return mLikesText;
    }
}
