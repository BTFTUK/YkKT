package com.yk.demo.wan.android.ui.wxchapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yk.demo.wan.android.R
import com.yk.demo.wan.android.base.*
import com.yk.demo.wan.android.databinding.FragmentWxChapterBinding
import com.yk.demo.wan.android.ui.main.MainFragment
import com.yk.demo.wan.android.ui.widget.ErrorReload
import com.yk.demo.wan.android.ui.wxchapterlist.WxChapterListFragment

/**
 * @author yk
 * @description 首页公众号模块界面
 */
class WxChapterFragment : BaseFragment<FragmentWxChapterBinding>() {

    private val mViewModel by lazy {
        ViewModelProvider(requireActivity(), WxChapterModelFactory(WxChapterRepository()))
            .get(WxChapterViewModel::class.java)
    }
    private val mAdapter by lazy { WxChapterAdapter(null) }

    override fun actionsOnViewInflate() {
        fetchWxChapter(false)
    }

    override fun getLayoutId(): Int = R.layout.fragment_wx_chapter

    @SuppressLint("ClickableViewAccessibility")
    override fun initFragment(view: View, savedInstanceState: Bundle?) {
        mBinding?.let { binding ->
            binding.refreshColor = R.color.colorAccent
            binding.refreshListener = SwipeRefreshLayout.OnRefreshListener {
                fetchWxChapter()
            }

            binding.adapter = mAdapter
            binding.listener = OnItemClickListener { position, _ ->
                (parentFragment as? MainFragment)?.closeMenu()
                mAdapter.getItemData(position)?.let {
                    WxChapterListFragment.navigate(mNavController, R.id.action_mainFragment_to_wxChapterListFragment, it.id, it.name)
                }
            }
            binding.rcvChapter.setOnTouchListener { _, _ ->
                (parentFragment as? MainFragment)?.closeMenu()
                false
            }

            binding.errorReload = ErrorReload {
                fetchWxChapter()
            }

            binding.gesture = DoubleClickListener {
                doubleTap = {
                    binding.rcvChapter.scrollToTop()
                }
            }
        }
    }

    private fun fetchWxChapter(isRefresh: Boolean = true) {
        mViewModel.getWxChapter()
        mViewModel.netState.observe(this, Observer {
            when (it.state) {
                State.RUNNING -> injectStates(refreshing = true, loading = !isRefresh)

                State.SUCCESS -> injectStates()

                State.FAILED -> {
                    mBinding?.wxChapterType?.text = resources.getText(R.string.text_place_holder)
                    injectStates(error = true)
                }
            }
        })

        mViewModel.mData.observe(this, Observer {
            mBinding?.emptyStatus = it.isNullOrEmpty()
            mAdapter.update(it)
            mBinding?.wxChapterType?.text = resources.getText(R.string.wx_chapter)
        })
    }

    private fun injectStates(refreshing: Boolean = false, loading: Boolean = false, error: Boolean = false) {
        mBinding?.let { binding ->
            binding.refreshing = refreshing
            binding.loadingStatus = loading
            binding.errorStatus = error
        }
    }
}