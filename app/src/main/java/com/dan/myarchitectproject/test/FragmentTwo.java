package com.dan.myarchitectproject.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.dan.myarchitectproject.R;
import com.dan.myarchitectproject.common.BaseFragment;

import butterknife.BindView;

/**
 * Author: Dan
 * Date: 2017/4/11 上午11:09
 * Description:
 * PackageName: com.dan.myarchitectproject.common.test
 * Copyright: 杭州安存网络科技有限公司
 **/

public class FragmentTwo extends BaseFragment {
    private static FragmentTwo mFragmentTwo;
    @BindView(R.id.tv_one)
    TextView tvOne;

    private Bundle mBundle;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one;
    }

    public static FragmentTwo newInstance() {
        if (mFragmentTwo == null)
            mFragmentTwo = new FragmentTwo();
        return mFragmentTwo;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()){
            mBundle = getArguments().getBundle(BUNDLE_ARGUMENTS);
        }

    }

    @Override
    protected void initData() {
        super.initData();
        tvOne.setText("This is fragment two");

    }

    @Override
    protected void initOnClick() {
        super.initOnClick();
    }
}
