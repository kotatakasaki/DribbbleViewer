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
public class ListAdapter extends ArrayAdapter{
    private LayoutInflater mInflater;
    private ImageView image_view;
    private TextView title_view;
    private TextView player_view;
    private DisplayImageOptions option;

    public ListAdapter(Context context, List<Dribbble> objects){
        super(context,0,objects);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        option = new DisplayImageOptions.Builder()
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

        final Dribbble item = (Dribbble)this.getItem(position);
        if(item!=null){

            image_view = (ImageView)convertView.findViewById(R.id.dribbble_image);
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item.getImage_url(),image_view, option);

            title_view = (TextView)convertView.findViewById(R.id.title_text);
            title_view.setText(item.getTitle_text());

            player_view = (TextView)convertView.findViewById(R.id.player_text);
            player_view.setText(item.getPlayer_text());
        }
        return convertView;
    }
}
