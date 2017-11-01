package com.uitlscollection;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.uitlscollection.tools.DeviceUtils;
import com.uitlscollection.tools.SpUtils;
import com.uitlscollection.tools.SystemUtils;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Button mTextView;
    private File mOutCachePath;
    private File mOutFilePath;
    private Button mButton;
    private ImageView mImageView;
    String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (Button) findViewById(R.id.btn_down);
        mButton = (Button) findViewById(R.id.btn_clear);

        //  new  SharedPreferences.Editor();
        SpUtils.setString(this, "ss", "爱仕达那时刻记得哈斯卡电话卡萨丁好卡好");
        mOutCachePath = getExternalCacheDir();
        mOutFilePath = getExternalFilesDir(Environment.DIRECTORY_ALARMS);
        mImageView = (ImageView) findViewById(R.id.iv);

        Glide.with(this)
                .load("http://dev-app.xiaoxi6.com/v6/static/app/img/index/oil_activity_idx.png")
                .crossFade()
                .into(mImageView);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  FileCacheUtils.clearAllCache(MainActivity.this);
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            }
        });
        findViewById(R.id.btn_down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fingerPrint = SystemUtils.getFingerPrint();
                String androidID = DeviceUtils.getAndroidID(MainActivity.this);


                Toast.makeText(MainActivity.this, "androidID---->" + androidID, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
