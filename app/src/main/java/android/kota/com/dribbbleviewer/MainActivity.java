package android.kota.com.dribbbleviewer;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
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

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ListActivity implements AbsListView.OnScrollListener{
    private RequestQueue mQueue;
    private List<Dribbble> data_list= new ArrayList<Dribbble>();
    private ListAdapter adapter;
    private View mFooter;
    private int page = 0;
    private boolean reading = false;
    private int MAX_COUNT = 50;
    private String ROOT_URL = "http://api.dribbble.com/shots/";
    private String POPULAR_URL = "popular?page=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getListView().addFooterView(getFooter());


        String url = ROOT_URL + POPULAR_URL + page;

        adapter = new ListAdapter(getApplicationContext(),data_list);
        setListAdapter(adapter);

        mQueue = Volley.newRequestQueue(this);
//        addRequest(url);

        getListView().setOnScrollListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void addRequest(String url){
        mQueue.add(new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject rootObject = response;
                        JSONArray shotArray = null;
                        try {
                            shotArray = rootObject.getJSONArray("shots");
                            for (int i = 0; i < shotArray.length(); i++) {
                                JSONObject jsonObject = shotArray.getJSONObject(i);
                                Dribbble dribbble = new Dribbble();
                                dribbble.setImage_url(jsonObject.getString("image_url"));
                                dribbble.setTitle_text(jsonObject.getString("title"));
                                dribbble.setPlayer_text(jsonObject.getJSONObject("player").getString("name"));
                                data_list.add(dribbble);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override public void onErrorResponse(VolleyError error) {
                        Log.d("TAG", "Network Error");
                    }
                }));

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Dribbble dribbble = (Dribbble)l.getItemAtPosition(position);
        Intent intent = new Intent(MainActivity.this,DetailActivity.class);
        intent.putExtra("image_url", dribbble.getImage_url());
        intent.putExtra("title_text", dribbble.getTitle_text());
        intent.putExtra("player_text", dribbble.getPlayer_text());

        startActivity(intent);
    }

    private View getFooter() {
        if (mFooter == null) {
            mFooter = getLayoutInflater().inflate(R.layout.listview_footer,null);
        }
        return mFooter;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (totalItemCount == firstVisibleItem + visibleItemCount) {
            additionalReading();
        }

    }

    private void additionalReading() {
// 読み込み回数が最大値以上ならスキップ。フッタを消す
        if (page > MAX_COUNT) {
            invisibleFooter();
            return;
        }
// 既に読み込み中ならスキップ
//        if (mQueue.getSequenceNumber() != null && mTask.getStatus() == AsyncTask.Status.RUNNING) {
//            return;
//        }
        String url = ROOT_URL + POPULAR_URL + (page++);
        Log.d("ERROR",url);
        addRequest(url);
    }

    private void invisibleFooter() {
        getListView().removeFooterView(getFooter());
    }


}
