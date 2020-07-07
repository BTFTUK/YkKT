package com.yk.demo.wan.android.ui.hotproject

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.yk.demo.wan.android.base.NetworkState
import com.yk.demo.wan.android.base.safeLaunch
import com.yk.demo.wan.android.entity.ProjectCategoryData
import com.yk.demo.wan.android.entity.ProjectDetailData

/**
 * @author yk
 * @description
 */
class HotProjectViewModel(private val repository: HotProjectRepository) : ViewModel() {

    val typeNetState = MutableLiveData<NetworkState>()
    var netState: LiveData<NetworkState>? = null
    val categories: MutableLiveData<List<ProjectCategoryData>> = MutableLiveData()
    var projects: LiveData<PagedList<ProjectDetailData>>? = null
    val selectedCategoryPosition = MutableLiveData<Int>()

    init {
        selectedCategoryPosition.value = 0
    }

    fun fetchCategories() {
        viewModelScope.safeLaunch {
            block = {
                typeNetState.postValue(NetworkState.LOADING)
                categories.value = repository.loadProjectCategories()
                typeNetState.postValue(NetworkState.LOADED)
            }
            onError = {
                typeNetState.postValue(NetworkState.error(it.message))
            }
        }
    }

    fun fetchDiffCategoryProjects(pid: Int, empty: () -> Unit) {
        projects = LivePagedListBuilder(
            HotProjectDataSourceFactory(repository, pid).apply {
                netState = Transformations.switchMap(sourceLiveData) { it.initState }
            }, PagedList.Config.Builder()
                .setPageSize(20)
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(20)
                .build()
        ).setBoundaryCallback(object : PagedList.BoundaryCallback<ProjectDetailData>() {
            override fun onZeroItemsLoaded() = empty()
        }).build()
    }
}