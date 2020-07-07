package com.yk.demo.wan.android.entity

/**
 * @author yk
 * @description
 */

data class TodoChoiceGroup(
    val choices: List<Choice>,
    val group_name: String,
    val param_key: String
) : ITodoChoice

data class Choice(
    val choice_name: String,
    val type: Int
) : ITodoChoice

interface ITodoChoice