package com.yk.demo.wan.android.entity

import com.yk.demo.wan.android.data.db.HomeArticleDetail

/**
 * @author yk
 * @description
 */

data class TopArticleEntity(
    val `data`: List<HomeArticleDetail>,
    val errorCode: Int,
    val errorMsg: String
)