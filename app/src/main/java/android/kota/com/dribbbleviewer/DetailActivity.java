package android.kota.com.dribbbleviewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by takasakikota on 2014/07/30.
 */
public class DetailActivity extends Activity{

    @Override
    public void onCreate(Bundle SavedInstance){
        super.onCreate(SavedInstance);
        setContentView(R.layout.activity_detail);

        //戻るボタンの追加
        getActionBar().setDisplayHomeAsUpEnabled(true);

        String image_url = "";
        String title_text = "";
        String player_text = "";

        //インテントの確認
        if(getIntent().getExtras() != null){
            image_url = getIntent().getExtras().getString("image_url");
            title_text = getIntent().getExtras().getString("title_text");
            player_text = getIntent().getExtras().getString("player_text");
        }

        //アクションバーにタイトルを表示
        getActionBar().setTitle(title_text);

        //画像の表示
        ImageView imageView = (ImageView)findViewById(R.id.detail_dribbble_image);
        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage(image_url,imageView);

        //タイトルの表示
        TextView titleView = (TextView)findViewById(R.id.detail_title_text);
        titleView.setText(title_text);

        //プレイヤーの表示
        TextView playerView = (TextView)findViewById(R.id.detail_player_text);
        playerView.setText(player_text);

    }

    //戻るボタンの挙動
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                //詳細画面を閉じる
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
