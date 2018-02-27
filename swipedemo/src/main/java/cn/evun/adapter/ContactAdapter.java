package cn.evun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.evun.javebean.ContactInfo;
import cn.evun.swipedemo.R;

public class ContactAdapter extends BaseAdapter {
    private static final int TYPECOUNT = 2;
    private static final int TITLETYPE = 0;
    private static final int ITEMTYPE = 1;
    private final Context context;
    private List<ContactInfo> infos;
    private int itemViewType;

    public ContactAdapter(List<ContactInfo> infos, Context mContext) {
        this.infos = infos;
        context = mContext;
    }

    @Override
    public int getCount() {
        return infos == null ? 0 : infos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ContactInfo info = infos.get(position);
        itemViewType = getItemViewType(position);
        if (itemViewType == TITLETYPE) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.title_list_item, parent, false);
                holder = new ViewHolder();
                holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvTitle.setText(info.getFirstLetter());
        } else if (itemViewType == ITEMTYPE) {
            ItemViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_list_item, parent, false);
                viewHolder = new ItemViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ItemViewHolder) convertView.getTag();
            }

            viewHolder.tvName.setText(info.getName());
        }


        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return TYPECOUNT;
    }

    @Override
    public int getItemViewType(int position) {

        ContactInfo info = infos.get(position);
        if (position == 0) {
            return TITLETYPE;
        } else {
            //判断自身条目的首字母和上一个首字母是否一致
            ContactInfo lastInfo = infos.get(position - 1);
            String lastFirstLetter = lastInfo.getFirstLetter();
            //如果一致,隐藏自身的首字母
            if (info.getFirstLetter().equals(lastFirstLetter)) {
                return ITEMTYPE;
            } else {
                //如果不一致,则让首字母可见
                return TITLETYPE;
            }
        }
    }

    private class ViewHolder {
        private TextView tvTitle;
    }

    private class ItemViewHolder {
        private final TextView tvName;

        ItemViewHolder(View convertView) {
            tvName = (TextView) convertView.findViewById(R.id.tv_name);
        }
    }
}
