package com.yk.demo.wan.android.ui.collection

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.yk.demo.wan.android.R
import com.yk.demo.wan.android.base.BaseFragment
import com.yk.demo.wan.android.base.BaseFragmentPagerAdapter
import com.yk.demo.wan.android.base.DoubleClickListener
import com.yk.demo.wan.android.databinding.FragmentCollectionBinding
import com.yk.demo.wan.android.ui.collectedarticles.CollectedArticlesFragment
import com.yk.demo.wan.android.ui.collectedwebsites.CollectedWebsitesFragment

/**
 * @author yk
 * @description 个人收藏页面
 */
class CollectionFragment : BaseFragment<FragmentCollectionBinding>() {

    private val mAdapter: BaseFragmentPagerAdapter by lazy {
        BaseFragmentPagerAdapter(
            childFragmentManager,
            arrayListOf(
                CollectedArticlesFragment(),
                CollectedWebsitesFragment()
            ),
            arrayOf("文章", "网址")
        )
    }

    override fun getLayoutId(): Int = R.layout.fragment_collection

    override fun initFragment(view: View, savedInstanceState: Bundle?) {
        mBinding?.let { binding ->
            binding.adapter = mAdapter
            binding.current = arguments?.getInt("position", 0) ?: 0
            binding.collectionIndicator.setupWithViewPager(binding.collectionVp)

            binding.gesture = DoubleClickListener {
                doubleTap = {
                    when (binding.collectionVp.currentItem) {
                        0 -> (childFragmentManager.fragments[0] as? CollectedArticlesFragment)?.scrollToTop()

                        1 -> (childFragmentManager.fragments[1] as? CollectedWebsitesFragment)?.scrollToTop()
                    }
                }
            }
        }
    }

    companion object {
        fun viewCollections(controller: NavController, @IdRes navId: Int, position: Int = 0) {
            controller.navigate(navId, bundleOf(Pair("position", position)))
        }
    }
}