package com.example.x5tbsdemo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.silang.superfileview.R;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;


public class Main extends Activity {

    private static final int CHOOSE_REQUEST_CODE = 1000;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        frameLayout = (FrameLayout) findViewById(R.id.fl);
    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button:
                chooseFile();
                break;
        }
    }

    //选择文件
    private void chooseFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        Intent chooser = Intent.createChooser(intent, "请选择要代开的文件");
        startActivityForResult(chooser, CHOOSE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case CHOOSE_REQUEST_CODE:
                        Uri uri = data.getData();
                        String path = getPathFromUri(this, uri);
                        File file =new File(path);
                        String s = file.toString();
                        openFile(path);
                        break;
                }
            }
        }
    }

    /**
     * 打开文件
     */
    private void openFile(String path) {
        TbsReaderView readerView = new TbsReaderView(this, new TbsReaderView.ReaderCallback() {
            @Override
            public void onCallBackAction(Integer integer, Object o, Object o1) {

                Log.e("--", "onCallBackAction: " );
            }
        });
        //通过bundle把文件传给x5,打开的事情交由x5处理
        Bundle bundle = new Bundle();
        //传递文件路径
        bundle.putString("filePath", path);
        //加载插件保存的路径
        bundle.putString("tempPath", Environment.getExternalStorageDirectory() + File.separator + "TbsReaderTemp");
        //加载文件前的初始化工作,加载支持不同格式的插件
        boolean b = readerView.preOpen(getFileType(path), false);
        if (b) {
            readerView.openFile(bundle);
        }
        frameLayout.addView(readerView,new FrameLayout.LayoutParams(-1,-1));
    }

    /***
     * 获取文件类型
     *
     * @param path 文件路径
     * @return 文件的格式
     */
    private String getFileType(String path) {
        String str = "";

        if (TextUtils.isEmpty(path)) {
            return str;
        }
        int i = path.lastIndexOf('.');
        if (i <= -1) {
            return str;
        }
        str = path.substring(i + 1);
        return str;
    }

    /**
     * @param context
     * @param uri     文件的uri
     * @return 文件的路径
     */
    public String getPathFromUri(final Context context, Uri uri) {
        // 选择的图片路径
        String selectPath = null;

        final String scheme = uri.getScheme();
        if (uri != null && scheme != null) {
            if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
                // content://开头的uri
                Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    // 取出文件路径
                    selectPath = cursor.getString(columnIndex);

                    // Android 4.1 更改了SD的目录，sdcard映射到/storage/sdcard0
                    if (!selectPath.startsWith("/storage") && !selectPath.startsWith("/mnt")) {
                        // 检查是否有"/mnt"前缀
                        selectPath = "/mnt" + selectPath;
                    }
                    //关闭游标
                    cursor.close();
                }
            } else if (scheme.equals(ContentResolver.SCHEME_FILE)) {// file:///开头的uri
                // 替换file://
                selectPath = uri.toString().replace("file://", "");
                int index = selectPath.indexOf("/sdcard");
                selectPath = index == -1 ? selectPath : selectPath.substring(index);
//                if (!selectPath.startsWith("/mnt")) {
//                    // 加上"/mnt"头
//                    selectPath = "/mnt" + selectPath;
//                }
            }
        }
        return selectPath;
    }
}
