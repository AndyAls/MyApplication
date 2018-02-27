package cn.evun.slidrdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.r0adkll.slidr.Slidr;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onClick(View view) {


        switch (view.getId()){
            case R.id.button:
                Intent intent=new Intent(this,BackActivity.class);
                startActivity(intent);
                break;
        }
    }
}
