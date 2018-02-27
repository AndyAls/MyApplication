package cn.evun.swiperecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Shuai.Li13 on 2017/7/10.
 */
public class MyAdapter extends BaseAdapter {

    private final ArrayList<Peason> peasons;
    private final Context context;

    public MyAdapter(Context context, ArrayList<Peason> peasons) {
        this.peasons = peasons;
        this.context = context;
    }

    @Override
    public int getCount() {
        return peasons.size();
    }

    @Override
    public Object getItem(int position) {
        return peasons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();

            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Peason peason = peasons.get(position);

        holder.tv_number.setText(peason.number);
        holder.tv_name.setText(peason.name);

        return convertView;
    }

    static class ViewHolder {
        TextView tv_name, tv_number;
    }
}
