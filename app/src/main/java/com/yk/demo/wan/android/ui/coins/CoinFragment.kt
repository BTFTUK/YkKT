package com.yk.demo.wan.android.ui.coins

import android.os.Bundle
import android.view.View
import com.yk.demo.wan.android.R
import com.yk.demo.wan.android.base.BaseFragment
import com.yk.demo.wan.android.base.BaseFragmentPagerAdapter
import com.yk.demo.wan.android.base.DoubleClickListener
import com.yk.demo.wan.android.databinding.FragmentCoinsBinding

/**
 * @author yk
 * @description
 */
class CoinFragment : BaseFragment<FragmentCoinsBinding>() {

    private val mAdapter: BaseFragmentPagerAdapter by lazy {
        BaseFragmentPagerAdapter(
            childFragmentManager, arrayListOf(
                CoinCommonSubFragment.recordInstance(),
                CoinCommonSubFragment.rankInstance()
            ), arrayOf(
                resources.getString(R.string.coin_record),
                resources.getString(R.string.coin_rank)
            )
        )
    }

    override fun getLayoutId(): Int = R.layout.fragment_coins

    override fun initFragment(view: View, savedInstanceState: Bundle?) {
        mBinding?.let { binding ->
            binding.adapter = mAdapter
            binding.coinIndicator.setupWithViewPager(binding.coinVp)
            binding.gesture = DoubleClickListener {
                doubleTap = {
                    (childFragmentManager.fragments[binding.coinVp.currentItem] as? CoinCommonSubFragment)?.scrollToTop()
                }
            }
        }
    }
}