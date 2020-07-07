package com.yk.demo.wan.dmnd.ui.login

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ToastUtils
import com.yk.demo.wan.android.R
import com.yk.demo.wan.android.base.BaseFragment
import com.yk.demo.wan.android.base.State
import com.yk.demo.wan.android.data.PreferencesHelperNew
import com.yk.demo.wan.android.databinding.FragmentLoginBinding
import com.yk.demo.wan.android.utils.DmndUtils
import com.yk.demo.wan.sudoku.ui.SudokuFragment

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    var cookies: String = ""
    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProvider(requireActivity(), LoginModelFactory(LoginRepository()))
            .get(LoginViewModel::class.java)
    }

    /**
     * 登录操作
     */
    fun login(view: View) {
        loginViewModel.netState.observe(this, Observer {
            when (it.state) {
                State.RUNNING -> showLoading()
                State.SUCCESS -> hideLoading()
                State.FAILED ->{
                    loadVerify(null)
                    hideLoading()
                }
            }
        })

        loginViewModel.login(
            mBinding?.editName?.text.toString(),
            mBinding?.editPwd?.text.toString(),
            mBinding?.editVerify?.text.toString(), cookies,
            mBinding?.cbSavePwd?.isChecked!!
        )
    }

    /**
     * 加载验证码
     */
    fun loadVerify(view: View?) {
        loginViewModel.loadVerify(success = { captcha ->
            mBinding?.ivVerify?.setImageBitmap(DmndUtils.convertStringToIcon(captcha.captcha))
//            cookies = captcha.sessionId
        }, fail = {
            ToastUtils.showShort(it)
        })
    }

    fun sudoku(view: View){
        SudokuFragment.viewCollections(mNavController
            ,R.id.action_loginFragment_to_sudokuFragment,0)
    }

    override fun getLayoutId(): Int = R.layout.fragment_login

    override fun initFragment(view: View, savedInstanceState: Bundle?) {
        mBinding?.let { binding ->
            binding.holder = this@LoginFragment
        }
        loadVerify(null)
    }

    override fun actionsOnViewInflate() {
        super.actionsOnViewInflate()
        mBinding?.let { binding ->
//            binding.btnLogin.setOnClickListener { v -> login(v) }
            binding.userName = PreferencesHelperNew.getUserName()
        }
    }

    override fun onResume() {
        super.onResume()
    }
}