package com.yk.demo.wan.android.entity

/**
 * @author yk
 * @description
 */
data class ProjectCategoryEntity(
    val `data`: List<ProjectCategoryData>,
    val errorCode: Int,
    val errorMsg: String
)

data class ProjectCategoryData(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)