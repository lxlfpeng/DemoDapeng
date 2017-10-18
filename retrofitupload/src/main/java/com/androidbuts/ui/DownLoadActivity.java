package com.androidbuts.ui;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidbuts.api.ApiService;
import com.androidbuts.utils.ProgressListener;
import com.androidbuts.utils.ProgressResponseBody;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DownLoadActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private SeekBar mSeekBar;
    private TextView mTvPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);
        mSeekBar = (SeekBar) findViewById(R.id.sb);
        mTvPro = (TextView) findViewById(R.id.txt_pro);
        findViewById(R.id.btn_down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downLoad();
            }
        });
    }

    ProgressListener progressListener = new ProgressListener() {
        //该方法在子线程中运行
        @Override
        public void onProgress(final long progress, final long total, boolean done) {
            Log.d(TAG, String.format("%d%% done\n", (100 * progress) / total));
            DownLoadActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mSeekBar.setProgress((int) ((100 * progress) / total));
                    mTvPro.setText("进度" + ((100 * progress) / total) + "%");
                }
            });
        }
    };

    private void downLoad() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //添加拦截器，自定义ResponseBody，添加下载进度
        builder.networkInterceptors().add(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder().body(
                        new ProgressResponseBody(originalResponse.body(), progressListener))
                        .build();

            }
        });
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://msoftdl.360.cn");
        ApiService retrofit = retrofitBuilder
                .client(builder.build())
                .callbackExecutor(executorService)
                .build().create(ApiService.class);
        final Call<ResponseBody> call = retrofit.retrofitDownload();

/*        new AsyncTask<Void, Long, Void>() {
            @Override
            protected Void doInBackground(Void... params) {*/

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    InputStream is = response.body().byteStream();
                    File file = new File(Environment.getExternalStorageDirectory(), "360app.apk");
                    FileOutputStream fos = new FileOutputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        fos.flush();
                    }
                    fos.close();
                    bis.close();
                    is.close();
                } catch (IOException e) {
                   // Toast.makeText(DownLoadActivity.this, "e-->" + e.toString(), Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }
                DownLoadActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DownLoadActivity.this, "下载成功", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        Toast.makeText(DownLoadActivity.this, "失败" + t.toString(), Toast.LENGTH_SHORT).show();
                DownLoadActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DownLoadActivity.this, "下载成功", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });


           /*     return null;
            }
        }.execute();*/


    }


}
