package android.kota.com.dribbbleviewer;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by takasakikota on 2014/07/30.
 */
public class ListAdapter extends ArrayAdapter<Dribbble>{
    private LayoutInflater mInflater;
    private ImageView mImageView;
    private TextView mTitleView;
    private TextView mPlayerView;
    private DisplayImageOptions mOption; //画像のオプション

    public ListAdapter(Context context){
        super(context,0);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //オプションの設定
        mOption = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .resetViewBeforeLoading(true)
                .build();
    }

    public View getView(final int position,View convertView,ViewGroup parent){
        if(convertView==null){
            convertView = mInflater.inflate(R.layout.row,null);
        }

        //アイテムの取得
        final Dribbble item = this.getItem(position);
        if(item!=null){

            //画像の表示
            mImageView = (ImageView)convertView.findViewById(R.id.dribbble_image);
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item.getImage_url(),mImageView, mOption);

            //タイトル名の表示
            mTitleView = (TextView)convertView.findViewById(R.id.title_text);
            mTitleView.setText(item.getTitle_text());

            //プレイヤー名の表示
            mPlayerView = (TextView)convertView.findViewById(R.id.player_text);
            mPlayerView.setText(item.getPlayer_text());
        }
        return convertView;
    }
}
