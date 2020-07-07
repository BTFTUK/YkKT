package com.yk.demo.wan.android.entity

/**
 * @author yk
 * @description
 */
data class SharedUser(
    val `data`: SharedData,
    val errorCode: Int,
    val errorMsg: String
)

data class SharedData(
    val coinInfo: CoinInfo,
    val shareArticles: UserArticleData
)

data class CoinInfo(
    val coinCount: Int,
    val level: Int,
    val rank: Int,
    val userId: Int,
    val username: String
)