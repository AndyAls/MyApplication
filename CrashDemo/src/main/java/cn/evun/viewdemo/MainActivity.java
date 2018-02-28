package cn.evun.viewdemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.R.attr.name;

public class MainActivity extends AppCompatActivity {

    ClassInfo clazz = new ClassInfo();
    List<ClassInfo> infos = new ArrayList<>();
    private ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.lv);
        queryInfo();
    }

    private void queryInfo() {

        PackageManager pm = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(pm));
        for (ResolveInfo info : resolveInfos) {
            Drawable drawable = info.loadIcon(pm);
            String appName = info.loadLabel(pm).toString();
            String packageName = info.activityInfo.packageName;
            String name = info.activityInfo.name;
            clazz.setPackageName(packageName);
            clazz.setAppName(appName);
            clazz.setClassName(name);
            infos.add(clazz);
        }

        initListView();
    }

    private void initListView() {
        MyAdapter adapter = new MyAdapter();
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return infos.size();
        }

        @Override
        public Object getItem(int position) {
            return infos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = null;
            if (convertView == null) {

                view = View.inflate(MainActivity.this, R.layout.item, null);
            } else {
                view = convertView;
            }
            TextView tv1 = (TextView) view.findViewById(R.id.tv1);
            TextView tv2 = (TextView) view.findViewById(R.id.tv2);
            TextView tv3 = (TextView) view.findViewById(R.id.tv3);
            ImageView iv= (ImageView) view.findViewById(R.id.iv);
            TextView tv4= (TextView) view.findViewById(R.id.tv4);
            ClassInfo classInfo = infos.get(position);
            tv1.setText(classInfo.getAppName());
            tv2.setText(classInfo.getClassName());
            tv3.setText(classInfo.getPackageName());
            tv4.setText(Html.fromHtml("<title>Html类 安卓_百度搜索</title>"));
            iv.setImageResource(R.drawable.ic_android_black_24dp);
            return view;
        }
    }
}
