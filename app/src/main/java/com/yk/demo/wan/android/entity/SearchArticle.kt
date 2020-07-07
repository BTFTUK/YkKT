package com.yk.demo.wan.android.entity

/**
 * @author yk
 * @description
 */
data class SearchArticleEntity(
    val `data`: SearchArticleData,
    val errorCode: Int,
    val errorMsg: String
)

data class SearchArticleData(
    val curPage: Int,
    val datas: List<ArticleDetail>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class ArticleDetail(
    val apkLink: String,
    val author: String,
    val chapterId: Int,
    val chapterName: String,
    var collect: Boolean,
    val courseId: Int,
    val desc: String,
    val envelopePic: String,
    val fresh: Boolean,
    val id: Int,
    val link: String,
    val niceDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: Long,
    val superChapterId: Int,
    val superChapterName: String,
    val tags: List<ArticleTag>,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
)

data class ArticleTag(
    val name: String,
    val url: String
)