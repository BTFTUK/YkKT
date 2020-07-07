package com.yk.demo.wan.sudoku.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.Utils
import com.google.gson.reflect.TypeToken
import com.yk.demo.wan.android.R
import com.yk.demo.wan.android.base.BaseFragment
import com.yk.demo.wan.android.data.PreferencesHelperNew
import com.yk.demo.wan.android.databinding.FragmentSudokuBinding
import com.yk.demo.wan.android.utils.SharePreferencesUtils
import com.yk.demo.wan.sudoku.entity.UnitBean

class SudokuFragment : BaseFragment<FragmentSudokuBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_sudoku

    override fun initFragment(view: View, savedInstanceState: Bundle?) {
        mBinding?.let { binding ->
            binding.holder = this@SudokuFragment
        }
        mBinding?.let {
            it.showNote.setOnCheckedChangeListener { _, isChecked ->
                it.gv.showNote = isChecked
            }
        }
        initSuDuKuDada()
    }

    fun onClickNum(view: View) {
        if (view is TextView) {
            val num = view.text.toString().toInt()
            if (mBinding?.switchInput?.isChecked!!) {
                mBinding?.gv?.setConfirmText(num)
            } else {
                mBinding?.gv?.setNoteText(num)
            }
        }
    }

    fun go(view: View) {
        if (mBinding?.switchInput?.isChecked!!) {
            mBinding?.gv?.setConfirmText(0)
        }
    }

    fun note(view: View) {
        mBinding?.gv?.noteAll()
    }

    fun run(view: View) {
        mBinding?.gv?.calculation()
    }

    fun clear(view: View) {
        mBinding?.gv?.clearAll()
    }

    fun save(view: View) {
        val data = GsonUtils.toJson(mBinding?.gv?.array)
        SharePreferencesUtils.saveString(Utils.getApp(), PreferencesHelperNew.SUDOKU_DATA, data)
    }

    private fun initSuDuKuDada() {
        val data: String = SharePreferencesUtils.getString(Utils.getApp(), PreferencesHelperNew.SUDOKU_DATA)
        val type = object : TypeToken<ArrayList<UnitBean?>?>() {}.type
        val array = GsonUtils.fromJson<ArrayList<UnitBean?>>(data, type)
        mBinding?.gv?.updateData(array)
    }

    companion object {
        fun viewCollections(controller: NavController, @IdRes navId: Int, position: Int = 0) {
            controller.navigate(navId, bundleOf(Pair("position", position)))
        }
    }
}