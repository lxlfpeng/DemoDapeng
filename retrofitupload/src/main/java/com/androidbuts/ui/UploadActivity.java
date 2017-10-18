package com.androidbuts.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidbuts.api.ApiService;
import com.androidbuts.api.RetroClient;
import com.androidbuts.permission.PermissionsActivity;
import com.androidbuts.permission.PermissionsChecker;
import com.androidbuts.utils.FileRequestBody;
import com.androidbuts.utils.InternetConnection;
import com.androidbuts.utils.RetrofitCallback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadActivity extends AppCompatActivity {
    public final String TAG = this.getClass().getSimpleName();

    /**
     * Permission List
     */
    private static final String[] PERMISSIONS_READ_STORAGE = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

    /**
     * Context Variables
     */
    Context mContext;

    /**
     * Views
     */
    View parentView;
    ImageView imageView;
    TextView textView;

    /**
     * Image path to send
     */
    String imagePath;

    /**
     *
     */
    PermissionsChecker checker;

    /**
     *
     */
    Toolbar toolbar;
    private TextView mTvPro;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        parentView = findViewById(R.id.parentView);
        checker = new PermissionsChecker(this);
        mTvPro = (TextView) findViewById(R.id.txt_pro);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView = (TextView) findViewById(R.id.textView);
        imageView = (ImageView) findViewById(R.id.imageView);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(imagePath)) {

                    /**
                     * Uploading AsyncTask
                     */
                    if (InternetConnection.checkConnection(mContext)) {
                        /******************Retrofit***************/
                        uploadImage();
                    } else {
                        Snackbar.make(parentView, R.string.string_internet_connection_warning, Snackbar.LENGTH_INDEFINITE).show();
                    }
                } else {
                    Snackbar.make(parentView, R.string.string_message_to_attach_file, Snackbar.LENGTH_INDEFINITE).show();
                }
            }
        });
    }

    /**
     * Upload Image Client Code
     */
    private void uploadImage() {

        /**
         * Progressbar to Display if you need
         */
        //Create Upload Server Client
        ApiService service = RetroClient.getApiService();

        RetrofitCallback callback = new RetrofitCallback() {
            @Override
            public void onLoading(final long total, final long progress) {
                //此处进行进度更新
                Log.d(TAG, "total---" + total);
                Log.d(TAG, "progress---" + progress);
                UploadActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvPro.setText("进度-->" + ((progress * 100) / total) + "%");
                    }
                });

            }
        };
        //File creating from selected URL
        File file = new File(imagePath);

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        FileRequestBody fileRequestBody = new FileRequestBody(requestFile, callback);


        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file.getName(), fileRequestBody);


        Call<ResponseBody> resultCall = service.uploadImage(body);


        // finally, execute the request
        resultCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();
                    Log.d(TAG, string);
                    Toast.makeText(mContext, "上传成功", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /**
                 * Update Views
                 */
                imagePath = "";
                textView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    /**
     * Showing Image Picker
     */
    public void showImagePopup(View view) {
        if (checker.lacksPermissions(PERMISSIONS_READ_STORAGE)) {
            startPermissionsActivity(PERMISSIONS_READ_STORAGE);
        } else {
            // File System.
            final Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_PICK);

            // Chooser of file system options.
            final Intent chooserIntent = Intent.createChooser(galleryIntent, getString(R.string.string_choose_image));
            startActivityForResult(chooserIntent, 1010);
        }
    }

    /***
     * OnResult of Image Picked
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1010) {
            if (data == null) {
                Snackbar.make(parentView, R.string.string_unable_to_pick_image, Snackbar.LENGTH_INDEFINITE).show();
                return;
            }
            Uri selectedImageUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = cursor.getString(columnIndex);

                Picasso.with(mContext).load(new File(imagePath))
                        .into(imageView);

                Snackbar.make(parentView, R.string.string_reselect, Snackbar.LENGTH_LONG).show();
                cursor.close();

                textView.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
            } else {
                textView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                Snackbar.make(parentView, R.string.string_unable_to_load_image, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startPermissionsActivity(String[] permission) {
        PermissionsActivity.startActivityForResult(this, 0, permission);
    }
}
