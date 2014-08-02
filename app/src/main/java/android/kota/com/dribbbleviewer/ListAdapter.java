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
    private ViewHolder holder;
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

            // GridView一コマの中のそれぞれのViewの参照を保持するクラスを生成
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.dribbble_image);
            holder.title = (TextView) convertView.findViewById(R.id.title_text);
            holder.player = (TextView) convertView.findViewById(R.id.player_text);
            // TagにGridViewの1コマの中に設定されたViewの参照を設定
            convertView.setTag(holder);
        } else {
            // TagからGridViewの1コマの中に設定されたViewの参照を取得
            holder = (ViewHolder) convertView.getTag();
        }

        //アイテムの取得
        final Dribbble item = this.getItem(position);
        if(item!=null){

            //画像の表示
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item.getImage_url(),holder.image, mOption);

            //タイトル名の表示
            holder.title.setText(item.getTitle_text());

            //プレイヤー名の表示
            holder.player.setText(item.getPlayer_text());
        }
        return convertView;
    }
}

// GridView一コマの内部の参照を保持する
class ViewHolder {
    ImageView image;
    TextView title;
    TextView player;
}
