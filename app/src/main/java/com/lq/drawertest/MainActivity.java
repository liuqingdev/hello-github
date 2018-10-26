package com.lq.drawertest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.LongDef;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DialogTitle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private DrawerLayout mDrawerLayout;//抽屉布局
    private List<entity> list;
    private MyAdapter myAdapter;
    private ListView leftListView;
    private ImageView mainImg;
    private RelativeLayout topLayout;
    private int mScreenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initList();
        initView();

    }

    private void initView() {
        topLayout = findViewById(R.id.top_layout);
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        mScreenHeight = outMetrics.heightPixels;
        Log.d(TAG, "mScreenHeight: " + mScreenHeight);

        topLayout.getLayoutParams().height = (int) (mScreenHeight *0.328);
        Log.d(TAG,"实际高度："+mScreenHeight *0.328);


        mainImg = findViewById(R.id.main_image);
        mainImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,Object> map = BaseApplication.getMap();
                map.put("key",  list);
                Intent intent = new Intent(MainActivity.this,PopupActivity.class);
                intent.putExtra("data","key");
                startActivity(intent);

            }
        });
        mDrawerLayout = findViewById(R.id.drawer_layout);
        leftListView = findViewById(R.id.mylistview);
        myAdapter = new MyAdapter();
        leftListView.setAdapter(myAdapter);
        leftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                entity hero = list.get(position);
                mainImg.setImageResource(hero.getImageId());
                mDrawerLayout.closeDrawers();
            }
        });
    }

    private void initList() {
        list = new ArrayList<>();
        list.add(new entity(R.mipmap.d1, "aaaa"));
        list.add(new entity(R.mipmap.d2, "bbbbb"));
        list.add(new entity(R.mipmap.d3, "cccc"));
        list.add(new entity(R.mipmap.d4, "cccc"));
        list.add(new entity(R.mipmap.d5, "cccc"));
        list.add(new entity(R.mipmap.d6, "cccc"));
        list.add(new entity(R.mipmap.d7, "cccc"));
        list.add(new entity(R.mipmap.d8, "cccc"));
        list.add(new entity(R.mipmap.d9, "cccc"));
        list.add(new entity(R.mipmap.d10, "cccc"));
        list.add(new entity(R.mipmap.d11, "cccc"));
        list.add(new entity(R.mipmap.d12, "cccc"));
        list.add(new entity(R.mipmap.d13, "cccc"));
        list.add(new entity(R.mipmap.d10, "cccc"));
        list.add(new entity(R.mipmap.d11, "cccc"));
        list.add(new entity(R.mipmap.d12, "cccc"));
        list.add(new entity(R.mipmap.d13, "cccc"));
        list.add(new entity(R.mipmap.d10, "cccc"));
        list.add(new entity(R.mipmap.d11, "cccc"));
        list.add(new entity(R.mipmap.d12, "cccc"));
        list.add(new entity(R.mipmap.d13, "cccc"));
        list.add(new entity(R.mipmap.d10, "cccc"));
        list.add(new entity(R.mipmap.d11, "cccc"));
        list.add(new entity(R.mipmap.d12, "cccc"));
        list.add(new entity(R.mipmap.d13, "cccc"));
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_list, null);
            }
            ImageView img = CommonViewHolder.get(convertView, R.id.item_img);
            TextView text = CommonViewHolder.get(convertView, R.id.item_text);
            entity data = list.get(position);
            img.setImageResource(data.getImageId());
            text.setText(data.getTitle());
            return convertView;
        }
    }
}
