package com.ryane.lazyfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by Dapeng on 2017/10/17.
 */

public abstract  class LazyFragment extends Fragment {
    // 标识view 是否初始化完成
    protected boolean isViewInit = false;
    // 当前Fragment 是否可见
    protected boolean isVisible = false;
    // 是否加载过数据
    protected boolean isLoadData = false;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisible = isVisibleToUser;
        preLoadData(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.isViewInit = true;
        // 防止一开始加载的时候未 调用 preLoadData 方法， 因为setUserVisibleHint 比 onActivityCreated 触发 前
        if (getUserVisibleHint()) {
            preLoadData(false);
        }
    }

    /**
     * 子类加载数据
     */
    protected abstract void loadData();

    /**
     * 当UI初始化成功，UI可见并且没有加载过数据的时候 加载数据
     *
     * @param forceLoad 强制加载数据
     */
    public void preLoadData(boolean forceLoad) {
        if (isViewInit && isVisible && (!isLoadData || forceLoad)) {
            loadData();
            isLoadData = true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //当界面校会的时候将标识置空
        isViewInit = false;
        isVisible = false;
        isLoadData = false;
    }
}
