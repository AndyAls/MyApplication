package cn.evun.decoration;

import android.Manifest;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

/**
 * Created by Shuai.Li13 on 2017/2/22.
 */

public class ActDemo extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        setContentView(R.layout.demo);

        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActDemo.this,"dianjile",Toast.LENGTH_SHORT).show();


                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

                    if (AndPermission.hasPermission(ActDemo.this, Manifest.permission.READ_SMS)){

                        Toast.makeText(ActDemo.this,"我有权限",Toast.LENGTH_SHORT).show();
                    }else {

                        AndPermission.with(ActDemo.this).requestCode(100)
                                .permission(Manifest.permission.READ_SMS)
                                .send();
                    }
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        AndPermission.onRequestPermissionsResult(requestCode,permissions,grantResults,listener);
    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantPermissions) {

            if (requestCode==100){

                Toast.makeText(ActDemo.this,"100",Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {

            if (AndPermission.hasAlwaysDeniedPermission(ActDemo.this,deniedPermissions)){

                AndPermission.defaultSettingDialog(ActDemo.this,requestCode).show();
            }
        }
    };
}
