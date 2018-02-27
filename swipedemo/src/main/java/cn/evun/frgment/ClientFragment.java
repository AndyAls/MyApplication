package cn.evun.frgment;

import android.support.v4.app.Fragment;
import android.view.View;

import cn.evun.swipedemo.R;

/**
 * Created by Shuai.Li13 on 2017/7/5.
 */
public class ClientFragment extends BaseFragment{
    @Override
    public void loadData() {

    }

    @Override
    public View loadView() {
        View view = View.inflate(mContext, R.layout.client_fragment_view, null);
        return view;
    }
}
