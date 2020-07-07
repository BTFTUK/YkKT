package com.yk.demo.wan.android.ui.collectedarticles

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.yk.demo.wan.android.base.NetworkState
import com.yk.demo.wan.android.base.safeLaunch
import com.yk.demo.wan.android.entity.UserCollectDetail


/**
 * Author: Taonce
 * Date: 2019/7/19
 * Project: CoroutinesWanAndroid
 * Desc:
 */
class CollectedArticlesViewModel(private val repo: CollectedArticlesRepository) : ViewModel() {
    var netState: LiveData<NetworkState>? = null
    var mArticles: LiveData<PagedList<UserCollectDetail>>? = null

    fun fetchCollectedArticleList(empty: () -> Unit) {
        mArticles = LivePagedListBuilder(
            CollectedArticlesDataSourceFactory(repo).apply {
                netState = Transformations.switchMap(sourceLiveData) { it.initState }
            }, PagedList.Config.Builder()
                .setPageSize(20)
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(20)
                .build()
        ).setBoundaryCallback(object : PagedList.BoundaryCallback<UserCollectDetail>() {
            override fun onZeroItemsLoaded() = empty()
        }).build()
    }

    fun deleteCollectedArticle(
        articleId: Int, originId: Int,
        onSuccess: () -> Unit, onFailed: (errorMsg: String) -> Unit
    ) {
        viewModelScope.safeLaunch {
            block = {
                val result = repo.deleteCollectedArticle(articleId, originId)
                if (result.errorCode == 0) {
                    // TODO("目前根据官方文档，通过 dataSource.invalidate 刷新 Paging 数据")
                    mArticles?.value?.dataSource?.invalidate()
                    onSuccess()
                } else {
                    onFailed(result.errorMsg)
                }
            }
            onError = { onFailed("网络出错啦~请检查网络") }
        }
    }
}

