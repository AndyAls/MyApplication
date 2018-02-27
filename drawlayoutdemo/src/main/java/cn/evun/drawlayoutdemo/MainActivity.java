package cn.evun.drawlayoutdemo;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout layout;
    private RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initRecyerView();
    }

    private void initRecyerView() {

        list = (RecyclerView) findViewById(R.id.list);
        List<String> mList=new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            mList.add("中华人民共和国"+i);
        }
        list.setHasFixedSize(true);
        LinearLayoutManager layout=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        list.setLayoutManager(layout);
        MyAdapter adapter = new MyAdapter(mList);
        list.setAdapter(adapter);
    }

    private void initView() {

        NavigationView view = (NavigationView) findViewById(R.id.nv);
        layout = (DrawerLayout) findViewById(R.id.drawerlayout);
        final Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("中华人民共和国");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, layout,toolbar,R.string.app_name,R.string.bottom_sheet_behavior);
        final TextInputLayout inputLayout = (TextInputLayout) findViewById(R.id.text);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(true);
        layout.addDrawerListener(toggle);
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                CharSequence title = item.getTitle();
                Toast.makeText(MainActivity.this, title, Toast.LENGTH_SHORT).show();
                layout.closeDrawers();
                return true;
            }
        });

        layout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                toolbar.setTitle("打开状态");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                toolbar.setTitle("关闭状态");
                invalidateOptionsMenu();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        boolean drawerOpen = layout.isDrawerOpen(list);
        menu.findItem(R.id.item).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }
}
