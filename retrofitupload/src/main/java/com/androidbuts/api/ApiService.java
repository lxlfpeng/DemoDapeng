package com.androidbuts.api;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;

/**
 * @author Pratik Butani on 23/4/16.
 */
public interface ApiService {

    /*上传文件*/
    @Multipart
    @POST("v6/index.php?m=app&act=user&detail=user_face_upload&_ver=1.0.0&_dtype=Android&_t=1507888488&_utoken=4844af241003f148a544900a7c4c90e0&user_id=24")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part file);

    /*下载文件*/
    @Streaming
    @GET("/mobilesafe/shouji360/360safesis/360MobileSafe_6.2.3.1060.apk")
    Call<ResponseBody> retrofitDownload();
}
