package com.yk.demo.wan.android.ui.dialog

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.yk.demo.wan.android.R
import com.yk.demo.wan.android.base.BaseDialogFragment
import com.yk.demo.wan.android.databinding.DialogShareArticleBinding
import com.yk.demo.wan.android.ui.usersharelist.UserShareListModelFactory
import com.yk.demo.wan.android.ui.usersharelist.UserShareListRepository
import com.yk.demo.wan.android.ui.usersharelist.UserShareListViewModel
import org.jetbrains.anko.toast

/**
 * @author yk
 * @description
 */
class ShareArticleDialogFragment : BaseDialogFragment<DialogShareArticleBinding>() {

    private val mViewModel: UserShareListViewModel by lazy {
        ViewModelProvider(requireActivity(), UserShareListModelFactory(UserShareListRepository()))
            .get(UserShareListViewModel::class.java)
    }

    override fun getLayoutId(): Int = R.layout.dialog_share_article

    override fun initFragment(view: View, savedInstanceState: Bundle?) {
        mBinding.fragment = this@ShareArticleDialogFragment
    }

    fun cancel(view: View) {
        dismiss()
    }

    fun ensure(view: View) {
        mViewModel.putAShare(mBinding.sharedName.text.toString(), mBinding.sharedLink.text.toString(), {
            requireContext().toast("添加成功")
            dismiss()
        }, { msg ->
            requireContext().toast(msg)
        })
    }
}