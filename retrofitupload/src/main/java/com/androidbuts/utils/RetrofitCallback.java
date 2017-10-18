package com.androidbuts.utils;

/**
 * Created by Dapeng on 2017/10/17.
 */

public abstract class RetrofitCallback {
    //用于进度的回调
    public abstract void onLoading(long total, long progress);
}

