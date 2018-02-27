package cn.evun.swiperecyclerview.interf;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

/**
 * Created by Shuai.Li13 on 2017/7/11.
 */

public interface  OnListViewListener {
    void onItemClick(AdapterView<?> parent, View view, int position, long id);
    void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);
}
