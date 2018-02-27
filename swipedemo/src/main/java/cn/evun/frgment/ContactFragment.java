package cn.evun.frgment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.evun.javebean.Cheeses;
import cn.evun.adapter.ContactAdapter;
import cn.evun.javebean.ContactInfo;
import cn.evun.view.QuickIndexBar;
import cn.evun.swipedemo.R;
import cn.evun.util.PinyinUtil;

/**
 * Created by Shuai.Li13 on 2017/7/5.
 */
public class ContactFragment extends BaseFragment{

    private TextView tv_show_letter;
    private QuickIndexBar qib;
    private SwipeMenuListView lv_contact_list;
    private List<ContactInfo> infos;


    @Override
    public void loadData() {//数据加载

        infos = new ArrayList<ContactInfo>();
        for (int i = 0; i < Cheeses.NAMES.length; i++) {
            ContactInfo contactInfo = new ContactInfo(Cheeses.NAMES[i]);
            infos.add(contactInfo);
        }

        //将infos集合进行排序
        Collections.sort(infos);

        lv_contact_list.setAdapter(new ContactAdapter(infos,mContext));
        qib.setOnLetterChangedListener(new QuickIndexBar.OnLetterChangedListener() {
            //当字母改变的时候,显示中间的黑色TextView
            @Override
            public void onLetterChanged(String letter) {
                tv_show_letter.setVisibility(View.VISIBLE);
                tv_show_letter.setText(letter);

                //遍历所有的字母,判断与当前选择的字母一致,即可获取对应的位置
                for (int i = 0; i < infos.size(); i++) {
                    if(infos.get(i).getFirstLetter().equals(letter)){
                        //让ListView跳到某个位置
                        lv_contact_list.setSelection(i);
                        break;
                    }
                }

            }

            //当抬起手后,让黑色TextView消失
            @Override
            public void onLetterDismiss() {
                tv_show_letter.setVisibility(View.GONE);
            }
        });

        String result = PinyinUtil.chineseWordToPinyin("传 a&@%智播客");
        Log.i("test","result:"+result);

        CreateSwipeMenu();

    }

    /**
     * 创建侧滑菜单
     */
    private void CreateSwipeMenu() {

        SwipeMenuCreator creator=new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                switch (menu.getViewType()){

                    case 0://返回的标题 不设置侧滑菜单

                        break;

                    case 1://返回的是名字

                        SwipeMenuItem moneyMenu=new SwipeMenuItem(mContext);
                        moneyMenu.setBackground(mContext.getDrawable(R.color.grey));
                        moneyMenu.setWidth(dp2Px(50));
                        moneyMenu.setIcon(R.drawable.money);
                        menu.addMenuItem(moneyMenu);

                        SwipeMenuItem editMenu=new SwipeMenuItem(mContext);
                        editMenu.setBackground(mContext.getDrawable(R.color.grey));
                        editMenu.setWidth(dp2Px(50));
                        editMenu.setIcon(R.drawable.edit);
                        menu.addMenuItem(editMenu);

                        SwipeMenuItem phoneMenu=new SwipeMenuItem(mContext);
                        phoneMenu.setBackground(mContext.getDrawable(R.color.grey));
                        phoneMenu.setWidth(dp2Px(50));
                        phoneMenu.setIcon(R.drawable.phone);
                        menu.addMenuItem(phoneMenu);

                        SwipeMenuItem deleteMenu=new SwipeMenuItem(mContext);
                        deleteMenu.setBackground(mContext.getDrawable(R.color.grey));
                        deleteMenu.setWidth(dp2Px(50));
                        deleteMenu.setIcon(R.drawable.delete);
                        menu.addMenuItem(deleteMenu);
                        break;
                }

            }
        };
        lv_contact_list.setMenuCreator(creator);

        setListener();
    }

    /**
     * 设置侧滑菜单的监听
     */
    private void setListener() {

        lv_contact_list.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                int itemViewType = lv_contact_list.getAdapter().getItemViewType(position);
                View v = getViewByPosition(position, lv_contact_list);

                switch (index){
                    case 0:
                        Toast.makeText(mContext,"金钱",Toast.LENGTH_SHORT).show();
                        if (itemViewType==1){
                            v.findViewById(R.id.ll_view).setBackgroundColor(mContext.getColor(R.color.white));
                        }
                        break;
                    case 1:
                        Toast.makeText(mContext,"编辑",Toast.LENGTH_SHORT).show();

                        if (itemViewType==1){
                            v.findViewById(R.id.ll_view).setBackgroundColor(mContext.getColor(R.color.white));
                        }

                        break;
                    case 2:
                        if (itemViewType==1){
                            v.findViewById(R.id.ll_view).setBackgroundColor(mContext.getColor(R.color.white));
                        }
                        Toast.makeText(mContext,"电话",Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        if (itemViewType==1){
                            v.findViewById(R.id.ll_view).setBackgroundColor(mContext.getColor(R.color.white));
                        }
                        Toast.makeText(mContext,"删除",Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
        lv_contact_list.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {//右侧菜单打开

                int itemViewType = lv_contact_list.getAdapter().getItemViewType(position);
                View v = getViewByPosition(position, lv_contact_list);
                if (itemViewType==1){
                    v.findViewById(R.id.ll_view).setBackgroundColor(mContext.getColor(R.color.green));
                }


                Toast.makeText(mContext,"open"+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMenuClose(int position) {//右侧菜单关闭


                int itemViewType = lv_contact_list.getAdapter().getItemViewType(position);
                View v = getViewByPosition(position, lv_contact_list);
                if (itemViewType==1){
                    v.findViewById(R.id.ll_view).setBackgroundColor(mContext.getColor(R.color.white));
                }
                Toast.makeText(mContext,"close"+position,Toast.LENGTH_SHORT).show();
            }
        });

        lv_contact_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext,position+"",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View loadView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.contact_fragment_view, null, false);
        tv_show_letter = (TextView) view.findViewById(R.id.tv_show_letter);
        qib = (QuickIndexBar) view.findViewById(R.id.qib);
        lv_contact_list = (SwipeMenuListView) view.findViewById(R.id.lv_contact_list);
        return view;
    }
}
