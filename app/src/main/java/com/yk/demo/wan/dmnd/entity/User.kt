/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yk.demo.wan.dmnd.entity

import android.text.TextUtils

/**
 * ================================================
 * User 实体类
 *
 *
 * Created by JessYan on 04/09/2016 17:14
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
class User {
    /**
     * id : 215
     * username : wxm
     * nickname : 王星朦
     * password : 740be933dcc3f843540f8f9a2c2f2ab144867ecbbffcbd865c442352dc695313
     * newPassword : null
     * status : 1
     * typeCode : null
     * salt : eIe1gAAt026YvcOjYP7E
     * email : 3821521@qq.com
     * mobile : 11111113333
     * usecycle : 01
     * roleIdList : [62]
     * createUserId : null
     * createTime : 2020-03-11 11:29:47
     * deptId : 1
     * deptName : 全国
     * orgNo : 999999
     * areaNo : 999999
     * companyName : 中国
     * depttypecode : 3
     * iszz : 1
     * ip : null
     * rsaPwd : null
     * rsausername : null
     * rspassword : null
     * appRoleId : null
     */
    var id: Long = 0
    var username: String? = null
    var nickname: String? = null
    var password: String? = null
    var newPassword: String? = null
    var status = 0
    var typeCode: String? = null
    var salt: String? = null
    var email: String? = null
    var mobile: String? = null
    var usecycle: String? = null
    var createUserId: String? = null
    var createTime: String? = null
    var deptId = 0
    var deptName: String? = null
    var orgNo: String? = null
    var areaNo: String? = null
    var companyName: String? = null
    var depttypecode: String? = null
    var iszz: String? = null
    var ip: String? = null
    var rsaPwd: String? = null
    var rsausername: String? = null
    var rspassword: String? = null
    var appRoleId //63录入人员  64需求人员
            : String? = null
    var roleIdList: List<Int>? = null

    val idStr: String
        get() = id.toString()

    val isLRY: Boolean
        get() = !TextUtils.isEmpty(appRoleId) && appRoleId == "63"

    val isXQY: Boolean
        get() = !TextUtils.isEmpty(appRoleId) && appRoleId == "64"
}