package com.dan.myarchitectproject.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.dan.myarchitectproject.common.utils.LogUtils;

import butterknife.ButterKnife;

/**
 * Author: Dan
 * Date: 2017/4/11 上午9:27
 * Description:
 * PackageName: com.dan.myarchitectproject.common
 * Copyright: 杭州安存网络科技有限公司
 **/

public abstract class BaseFragmentActivity extends FragmentActivity{
    //布局文件id
    protected abstract int getLayoutId();

    //布局中Fragment id
    protected abstract int getFragmentContentId();

    protected void initOnClick(){}

    //获取第一个fragment
    protected abstract BaseFragment getFirstFragment();

    protected void addFragment(BaseFragment fragment){
        if (fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(getFragmentContentId(),fragment,fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

//    protected void removeFragment(){
//        if (getSupportFragmentManager().getBackStackEntryCount() > 1){
//            getSupportFragmentManager().popBackStack();
//        }else {
//            finish();
//        }
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initOnClick();
        if (null == getSupportFragmentManager().getFragments()){
            BaseFragment firstFragment = getFirstFragment();
            if (null != firstFragment)
                addFragment(firstFragment);
        }
    }

}
