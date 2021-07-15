package com.chenliang.account.bean

import com.chenliang.baselibrary.annotation.MyDefault
import com.chenliang.baselibrary.base.MyBaseBean

class BeanUser : MyBaseBean() {
    var name = ""

    @MyDefault(myValue = "默认密码")
    var password = ""


}