package cn.evun.slidrdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Shuai.Li13 on 2017/8/9.
 */

public class MyFragment extends Fragment{

    private static final String TAB_KEY = "TAB_KEY";
    private Bundle getBundle;
    private Context mContext;

    public static MyFragment newInstance(String type){

        MyFragment fragment=new MyFragment();
        Bundle bundle=new Bundle();
        bundle.putCharSequence(TAB_KEY,type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments!=null){
            this.getBundle=arguments;
        }
        mContext=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment, container, false);
        TextView tv= (TextView) view.findViewById(R.id.tv);
        tv.setText(getBundle.getCharSequence(TAB_KEY));
        return view;
    }
}
