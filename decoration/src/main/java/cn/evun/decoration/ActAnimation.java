package cn.evun.decoration;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Shuai.Li13 on 2017/2/23.
 */

public class ActAnimation extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation);
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transte(v);
            }
        });
    }

    private void transte(View v) {

        if (Build.VERSION.SDK_INT<21){

            Toast.makeText(this,"版本不支持",Toast.LENGTH_SHORT).show();
        }else {

            Intent intent = new Intent(ActAnimation.this,ActTranstion.class);
            ActivityOptionsCompat option = ActivityOptionsCompat.makeScaleUpAnimation(v,v.getWidth()/2,v.getHeight()/2,10,10);
            startActivity(intent,option.toBundle());

        }
    }
}
