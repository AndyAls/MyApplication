package cn.evun.decoration;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.security.Permission;

/**
 * Created by Shuai.Li13 on 2017/2/21.
 */

public class ActText extends AppCompatActivity {

    private String [] permissions = {Manifest.permission.READ_SMS};
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item);


        final AudioManager manger = (AudioManager) getSystemService(AUDIO_SERVICE);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

                    int i = ContextCompat.checkSelfPermission(ActText.this, permissions[0]);
                    if (i!= PackageManager.PERMISSION_GRANTED){

                        showDialogUserRequestPermission();
                    }else {
//                        Settings.System.putInt(getContentResolver(),Settings.System.SOUND_EFFECTS_ENABLED,1);
//                        manger.loadSoundEffects();
                    }
                }


            }
        });
    }

    private void showDialogUserRequestPermission() {

        new AlertDialog.Builder(this)
                .setTitle("权限申请")
                .setMessage("必须申请权限应用正常使用")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startRequestPermission();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setIcon(R.mipmap.ic_launcher).setCancelable(false).show();
    }

    private void startRequestPermission() {

        ActivityCompat.requestPermissions(this,permissions,12);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==12){

            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

                if (grantResults[0]!=PackageManager.PERMISSION_GRANTED){

                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b){

                        showDialogToAppSetting();//提示用户去设置开启
                    }

                }else {

                    Toast.makeText(ActText.this,"获取权限成功",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void showDialogToAppSetting() {
            dialog = new AlertDialog.Builder(this).setTitle("权限不可用")
                    .setMessage("请在-应用设置-权限-中，允许支付宝使用存储权限来保存用户数据")
                    .setPositiveButton("现在设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            goToUserSetting();
                        }
                    })
                    .setNegativeButton("不设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finish();
                        }
                    }).setCancelable(false).show();

    }

    private void goToUserSetting() {

        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent,123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==123){

            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

                int i = ContextCompat.checkSelfPermission(this, permissions[0]);
                if (i!=PackageManager.PERMISSION_GRANTED){

                    showDialogToAppSetting();
                }else {

                    if (dialog!=null&&dialog.isShowing()){

                        dialog.dismiss();
                    }

                    Toast.makeText(this,"获取权限成功",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.menu:
                Toast.makeText(ActText.this,"meau",Toast.LENGTH_SHORT).show();
                break;
            case R.id.search:
                Toast.makeText(ActText.this,"search",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
