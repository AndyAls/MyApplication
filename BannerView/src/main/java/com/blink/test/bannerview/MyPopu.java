package com.blink.test.bannerview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import java.util.zip.Inflater;

/**
 * Created by Shuai.Li13 on 2017/3/30.
 */

public class MyPopu extends PopupWindow {

    public MyPopu(Activity context, View.OnClickListener listener) {

        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflate = inflater.inflate(R.layout.pup_item, null);
        this.setContentView(inflate);
        this.setFocusable(true);
        this.setBackgroundDrawable(new ColorDrawable());
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                dismiss();
            }
        });
        this.setContentView(inflate);
    }

}
