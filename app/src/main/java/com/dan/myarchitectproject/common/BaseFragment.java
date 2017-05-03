package com.dan.myarchitectproject.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Author: Dan
 * Date: 2017/4/11 上午9:44
 * Description:
 * PackageName: com.dan.myarchitectproject.common
 * Copyright: 杭州安存网络科技有限公司
 **/

public abstract class BaseFragment extends Fragment {

    protected BaseFragmentActivity mBaseFragmentActivity;

    protected static final String BUNDLE_ARGUMENTS = "bundle_arguments";

    //布局文件id
    protected abstract int getLayoutId();

    //初始化数据
    protected void initData() {
    }

    //点击事件
    protected void initOnClick() {
    }

//    protected BaseFragment newInstance(){
//        return null;
//    }
//    protected BaseFragment newInstance(Bundle bundle){}

    //宿主Activity
    protected BaseFragmentActivity getHoldingActivity() {
        return mBaseFragmentActivity;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mBaseFragmentActivity = (BaseFragmentActivity) activity;
    }

//    protected void removeFragment(){
//        mBaseFragmentActivity.removeFragment();
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(mBaseFragmentActivity, view);
        initData();
        initOnClick();
        return view;
    }

    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(mBaseFragmentActivity, cls);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(mBaseFragmentActivity, cls);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

}
