package com.yk.demo.wan.android.ui.dialog

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.yk.demo.wan.android.R
import com.yk.demo.wan.android.base.BaseDialogFragment
import com.yk.demo.wan.android.databinding.DialogRegisterBinding
import com.yk.demo.wan.android.ui.main.MainModelFactory
import com.yk.demo.wan.android.ui.main.MainRepository
import com.yk.demo.wan.android.ui.main.MainViewModel
import org.jetbrains.anko.toast

/**
 * @author yk
 * @description
 */
class RegisterDialogFragment : BaseDialogFragment<DialogRegisterBinding>() {

    private val mViewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity(), MainModelFactory(MainRepository()))
            .get(MainViewModel::class.java)
    }

    override fun getLayoutId(): Int = R.layout.dialog_register

    override fun initFragment(view: View, savedInstanceState: Bundle?) {
        mBinding.holder = this@RegisterDialogFragment
    }

    fun register(view: View) {
        val username = mBinding.userName.text.toString()
        val password = mBinding.password.text.toString()
        val repass = mBinding.repass.text.toString()

        if (username.isBlank() || password.isBlank() || repass.isBlank()) {
            requireContext().toast("请输入完整")
        } else {
            mViewModel.register(username, password, repass, {
                requireContext().toast("登录成功")
                dialog?.dismiss()
            }, { message ->
                requireContext().toast(message)
                dialog?.dismiss()
            })
        }
    }

    fun close(view: View) {
        dialog?.dismiss()
    }
}