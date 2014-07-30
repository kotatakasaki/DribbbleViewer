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

        getActionBar().setDisplayHomeAsUpEnabled(true);

        String image_url = "";
        String title_text = "";
        String player_text = "";
        if(getIntent().getExtras() != null){
            image_url = getIntent().getExtras().getString("image_url");
            title_text = getIntent().getExtras().getString("title_text");
            player_text = getIntent().getExtras().getString("player_text");
        }

        getActionBar().setTitle(title_text);

        ImageView imageView = (ImageView)findViewById(R.id.detail_dribbble_image);
        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage(image_url,imageView);


        TextView titleView = (TextView)findViewById(R.id.detail_title_text);
        titleView.setText(title_text);

        TextView playerView = (TextView)findViewById(R.id.detail_player_text);
        playerView.setText(player_text);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
