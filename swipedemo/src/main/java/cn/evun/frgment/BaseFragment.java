package cn.evun.frgment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Shuai.Li13 on 2017/7/5.
 */

public abstract class BaseFragment extends Fragment {


    public Context mContext;
    protected View mRootView;
    private boolean isVisible;//fragment是否可见
    private boolean isPreparet;//fragment是否已经创建 onCreateView执行完后设置true
    private boolean isFrist=true;//是否第一次调用 ..保证生命周期只加载一次数据

    public BaseFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView==null){
            mRootView=loadView();
        }
        return mRootView;
    }

    //fragment是否可见
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()){
            isVisible=true;
            lazyLoadData();
        }else {
            isVisible=false;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPreparet =true;
        lazyLoadData();
    }

    private void lazyLoadData() {

        if (!isFrist&&!isPreparet&&!isVisible){
            return;
        }
        loadData();
        isFrist = false;

    }

    //加载数据
    public abstract void loadData();

    //加载视图
    public abstract View loadView();


    public int dp2Px(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getContext().getResources().getDisplayMetrics());
    }


    /**
     * 获取listview条目布局
     */
    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
}
