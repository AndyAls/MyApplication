package cn.evun.swiperecyclerview;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SwipeMenuListView mList;
    private ArrayList<Peason> peasons = new ArrayList<>();
    private List<String> contacts = new ArrayList<>();
    private MyAdapter adapter;
    private Button button;
    private int firstVisiblePosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {

        Peason peason = new Peason("张三", "13893849083");
        Peason peason1 = new Peason("李四", "13398288373");
        Peason peason2 = new Peason("王五", "13222449083");
        for (int i = 0; i < 10; i++) {
            peasons.add(peason);
            peasons.add(peason1);
            peasons.add(peason2);
            contacts.add("客户" + i);
        }


    }

    SwipeMenuCreator creator = new SwipeMenuCreator() {
        @Override
        public void create(SwipeMenu menu) {


            SwipeMenuItem phoneMenu = new SwipeMenuItem(MainActivity.this);
            phoneMenu.setBackground(getResources().getDrawable(R.color.yellow));

            phoneMenu.setWidth(dp2Px(50));
            phoneMenu.setIcon(R.drawable.phone);
            menu.addMenuItem(phoneMenu);

            SwipeMenuItem nullMenu = new SwipeMenuItem(MainActivity.this);
            nullMenu.setBackground(getResources().getDrawable(R.color.write));
            nullMenu.setWidth(dp2Px(2));
            menu.addMenuItem(nullMenu);

            SwipeMenuItem deleteMenu = new SwipeMenuItem(MainActivity.this);
            deleteMenu.setBackground(getResources().getDrawable(R.color.yellow));
            deleteMenu.setWidth(dp2Px(50));
            deleteMenu.setIcon(R.drawable.delete);
            menu.addMenuItem(deleteMenu);
        }
    };

    private void initView() {

        mList = (SwipeMenuListView) findViewById(R.id.list);
        button = (Button) findViewById(R.id.button);
        if (adapter == null) {
            adapter = new MyAdapter(this, peasons);
        } else {
            adapter.notifyDataSetChanged();
        }
        mList.setAdapter(adapter);
        mList.setMenuCreator(creator);

        setListener();
    }

    private void setListener() {
        mList.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {
                View v = getViewByPosition(position, mList);
                LinearLayout item = (LinearLayout) v.findViewById(R.id.item);
                TextView tvName = (TextView) v.findViewById(R.id.tv_name);
                TextView tvNum = (TextView) v.findViewById(R.id.tv_number);
                item.setBackgroundColor(getResources().getColor(R.color.green));
                tvName.setTextColor(getResources().getColor(R.color.write));
                tvNum.setTextColor(getResources().getColor(R.color.write));
            }

            @Override
            public void onMenuClose(int position) {

                View v = getViewByPosition(position, mList);
                LinearLayout item = (LinearLayout) v.findViewById(R.id.item);
                TextView tvName = (TextView) v.findViewById(R.id.tv_name);
                TextView tvNum = (TextView) v.findViewById(R.id.tv_number);
                item.setBackgroundColor(getResources().getColor(R.color.write));
                tvName.setTextColor(getResources().getColor(R.color.black));
                tvNum.setTextColor(getResources().getColor(R.color.black));
            }
        });

        mList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                View v = getViewByPosition(position, mList);
                LinearLayout item = (LinearLayout) v.findViewById(R.id.item);
                TextView tvName = (TextView) v.findViewById(R.id.tv_name);
                TextView tvNum = (TextView) v.findViewById(R.id.tv_number);
                switch (index) {
                    case 0:
                        String phone = peasons.get(position).number;
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + phone);
                        intent.setData(data);
                        startActivity(intent);
                        item.setBackgroundColor(getResources().getColor(R.color.write));
                        tvName.setTextColor(getResources().getColor(R.color.black));
                        tvNum.setTextColor(getResources().getColor(R.color.black));
                        break;
                    case 2:
                        peasons.remove(position);
                        adapter.notifyDataSetChanged();
                        item.setBackgroundColor(getResources().getColor(R.color.write));
                        tvName.setTextColor(getResources().getColor(R.color.black));
                        tvNum.setTextColor(getResources().getColor(R.color.black));
                        break;
                }
                return false;
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    int selectPos = -1;

    ListView lv;

    private void showDialog() {

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_contacts);
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        attributes.width = display.getWidth();
        attributes.height = (int) (display.getHeight() * 0.4);
        attributes.gravity = Gravity.BOTTOM;
        dialog.getWindow().setAttributes(attributes);
        dialog.getWindow().setWindowAnimations(R.style.dialog_animation);
        lv = (ListView) dialog.getWindow().findViewById(R.id.lv);
        TextView tvSure = (TextView) dialog.getWindow().findViewById(R.id.tv_sure);
        TextView tvCancle = (TextView) dialog.getWindow().findViewById(R.id.tv_cancel);
        final DialogAdapter dialogAdapter = new DialogAdapter();
        lv.setAdapter(dialogAdapter);
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setText(contacts.get((int) dialogAdapter.getItemId(selectPos)));
                button.setTextColor(getResources().getColor(R.color.colorAccent));
                dialog.dismiss();
            }
        });

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                selectPos = firstVisibleItem + 1;//默认第二条可见条目是选中装态
                dialogAdapter.notifyDataSetChanged();
            }
        });

        //此方法和setOnScrollListener有冲突,有解决好的大神麻烦告知一下
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        //无限循环,默认位置是Integer.MAX_VALUE / 2 位置并位于contacts的第一个元素
        lv.setSelection(Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % contacts.size());

        dialog.show();
    }

    public int dp2Px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }


    /**
     * 获取listview条目布局
     */
    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    private class DialogAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public String getItem(int position) {
            return contacts.get((int) getItemId(position));
        }

        @Override
        public long getItemId(int position) {
            return position % contacts.size();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder;

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_contacts_item, parent, false);
                holder.tv = (TextView) convertView.findViewById(R.id.text1);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tv.setText(getItem(position));
            if (selectPos != -1 && selectPos == position) {
                holder.tv.setTextSize(30);
                holder.tv.setTextColor(getResources().getColor(R.color.green));
            }else{
                holder.tv.setTextSize(20);
                holder.tv.setTextColor(getResources().getColor(R.color.black));
            }
            return convertView;
        }
    }

    static class ViewHolder {
        TextView tv;
    }
}
