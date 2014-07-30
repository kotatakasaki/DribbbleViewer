package android.kota.com.dribbbleviewer;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class MainActivity extends ListActivity implements AbsListView.OnScrollListener, View.OnClickListener{
    private ListAdapter mAdapter;
    private RequestQueue mQueue;
    private View mFooter; //ロード画像を表示するフッター
    private int mPage; //読み込むページ番号
    private boolean mNowReading; //読み込み中
    private String mKindUrl; //アイテムの種類
    private final int MAX_COUNT = 50; //読み込むページの最大数
    private final String ROOT_URL = "http://api.dribbble.com/shots/";
    private final String POPULAR_URL = "popular?page=";
    private final String DEBUTS_URL = "debuts?page=";
    private final String EVERYONE_URL = "everyone?page=";
    private Button mPopularButton;
    private Button mEveryoneButton;
    private Button mDebutsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        //フッターの追加
        getListView().addFooterView(getFooter());

        //タイトルの追加
        setTitle(R.string.popular_label);

        //アダプターをセット
        mAdapter = new ListAdapter(getApplicationContext());
        setListAdapter(mAdapter);

        //リクエストキューの生成
        mQueue = Volley.newRequestQueue(this);

        //スクロールリスナーをセット
        getListView().setOnScrollListener(this);

        //ボタンのリスナーをセット
        mPopularButton = (Button) findViewById(R.id.popular_button);
        mEveryoneButton = (Button) findViewById(R.id.everyone_button);
        mDebutsButton = (Button) findViewById(R.id.debuts_button);
        mPopularButton.setOnClickListener(this);
        mEveryoneButton.setOnClickListener(this);
        mDebutsButton.setOnClickListener(this);

    }

    private void init(){

        mPage = 0;
        mNowReading = false;
        mKindUrl = POPULAR_URL;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private View getFooter() {
        if (mFooter == null) {
            mFooter = getLayoutInflater().inflate(R.layout.listview_footer,null);
        }
        return mFooter;
    }

    //リクエストキューにリクエストを追加
    public void addRequest(String url){

        //読み込み中=true
        mNowReading = true;

        //リクエストを追加
        mQueue.add(new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject rootObject = response;
                        JSONArray shotArray = null;
                        try {

                            //JSONObjectからJSONArrayを取り出す
                            shotArray = rootObject.getJSONArray("shots");

                            //JSONArrayからデータを取り出す
                            for (int i = 0; i < shotArray.length(); i++) {
                                JSONObject jsonObject = shotArray.getJSONObject(i);
                                Dribbble dribbble = new Dribbble();

                                //画像のURLを取得
                                dribbble.setImage_url(jsonObject.getString("image_url"));

                                //タイトル名を取得
                                dribbble.setTitle_text(jsonObject.getString("title"));

                                //プレイヤー名を取得
                                dribbble.setPlayer_text(jsonObject.getJSONObject("player").getString("name"));

                                //アダプターに追加
                                mAdapter.add(dribbble);
                            }

                            //アダプターの更新
                            mAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {

                            //読み込み中=false
                            mNowReading = false;
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override public void onErrorResponse(VolleyError error) {
                        Log.d("TAG", "Network Error");
                    }
                }));

    }

    //選択されたアイテムの詳細画面へ移動
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        //選択されたアイテムを取得
        Dribbble dribbble = (Dribbble)l.getItemAtPosition(position);

        //インテントを生成
        Intent intent = new Intent(MainActivity.this,DetailActivity.class);

        //選択されたアイテムの内容をセット
        intent.putExtra("image_url", dribbble.getImage_url());
        intent.putExtra("title_text", dribbble.getTitle_text());
        intent.putExtra("player_text", dribbble.getPlayer_text());

        //移動
        startActivity(intent);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    //スクロール判定
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (totalItemCount == firstVisibleItem + visibleItemCount) {
            //追加読み込み
            additionalReading();
        }

    }

    //アイテムの追加読み込み
    private void additionalReading() {

        // 読み込み回数が最大値以上ならスキップ。フッタを消す
        if (mPage > MAX_COUNT) {
            invisibleFooter();
            return;
        }

        // 既に読み込み中ならスキップ
        if (mNowReading) {
            return;
        }

        //次のページのURL生成
        mPage++;
        String url = ROOT_URL + mKindUrl + mPage;

        //URLのアイテムを取得
        addRequest(url);
    }

    //フッターを隠す
    private void invisibleFooter() {
        getListView().removeFooterView(getFooter());
    }

    //表示するアイテムの種類を変更
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.popular_button:

                //URLの変更
                mKindUrl = POPULAR_URL;

                //タイトルを変更
                setTitle(R.string.popular_label);

                //表示アイテムのリセット
                resetViewItems();
                break;
            case R.id.everyone_button:

                //URLの変更
                mKindUrl = EVERYONE_URL;

                //タイトルを変更
                setTitle(R.string.everyone_label);

                //表示アイテムのリセット
                resetViewItems();
                break;
            case R.id.debuts_button:

                //URLの変更
                mKindUrl = DEBUTS_URL;

                //タイトルを変更
                setTitle(R.string.debuts_label);

                //表示アイテムのリセット
                resetViewItems();
                break;
        }

    }

    //表示アイテムをリセット
    private void resetViewItems(){

        //ページをリセット
        mPage = 0;

        //現在のリクエストをキャンセル
        mQueue.cancelAll(new RequestQueue.RequestFilter(){
            @Override
            public boolean apply(Request<?> request){
                return true;
            }
        });

        //アダプターをクリア
        mAdapter.clear();

        //表示アイテムを取得
        String pop_url = ROOT_URL + mKindUrl + mPage;
        addRequest(pop_url);

    }

}
