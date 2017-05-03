package com.dan.myarchitectproject.test;

import android.annotation.TargetApi;
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

public class FragmentOne extends BaseFragment {
    private static FragmentOne mFragmentOne;
    @BindView(R.id.tv_one)
    TextView tvOne;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one;
    }

    @Override
    protected void initData() {
        super.initData();
        tvOne.setText("This is fragment one");
    }

    @Override
    protected void initOnClick() {
        super.initOnClick();
    }

    public static FragmentOne newInstance() {
        if (null == mFragmentOne)
            mFragmentOne = new FragmentOne();
        return mFragmentOne;
    }
}
