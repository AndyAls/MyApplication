package cn.evun.opengl;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ContentFrameLayout;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private BufferedWriter writer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MySufaceView mGlView=new MySufaceView(this);
        setContentView(mGlView);
        TextView tv=new TextView(this);
        init();
    }

    private void init() {

        try {
            FileOutputStream sj = openFileOutput("sj", MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(sj));
            writer.write("nanalala");
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        SharedPreferences config = getSharedPreferences("config", MODE_PRIVATE);
        SharedPreferences.Editor edit = config.edit();
    }

}
