package com.yk.demo.wan.android.ui.dialog

import android.os.Bundle
import android.view.View
import com.yk.demo.wan.android.R
import com.yk.demo.wan.android.base.BaseDialogFragment
import com.yk.demo.wan.android.databinding.DialogLoadingFBinding

class LoadingDialogFragment:BaseDialogFragment<DialogLoadingFBinding>() {
    override fun getLayoutId(): Int = R.layout.dialog_loading_f

    override fun initFragment(view: View, savedInstanceState: Bundle?) {
        mBinding.holder = this@LoadingDialogFragment
    }
}