package com.chenliang.baselibrary.base

import androidx.databinding.BaseObservable
import androidx.databinding.ViewDataBinding
import com.chenliang.baselibrary.bean.BeanUser
import com.chenliang.baselibrary.utils.MySpUtis
import com.google.gson.Gson
import com.chenliang.baselibrary.annotation.defaultResetToNull
import com.chenliang.baselibrary.annotation.defaultValueReflex
import java.io.Serializable


/*
如果需要保存数据到sp，且该类型的数据，只会保存一个对象的数据到sp，可以用以下方法：比如BeanUser继承MyBaseBean，登录后保存BeanUser

     var user = BeanUser()
     user.name = "tom"
     user.age = 12
     user.save()//保存

     var user2 = BeanUser().get()//获取
     user2!!.logJson()

      BeanUser().clear()//清除
 */
open class MyBaseBean() : BaseObservable(), Serializable {
    @Transient
    open var notifyDataSetChanged:Long = 0//解决notifyDataSetChanged不刷新问题

    @Transient
    open var itemType = 0

    @Transient
    open var binding: ViewDataBinding? = null

    @Transient
    var itemPosition = 0

    @Transient
    var itemSelected = false
    open fun save() {

        if (this is BeanUser) {
            var user = get<BeanUser>()
            //比如修改用户信息后，返回的user json中，没有token和isLogin信息；所以到单独处理
            if (user != null) {

            }
        }

        MySpUtis.putString(this::class.java.name, Gson().toJson(this))
    }

    open fun <T> get(): T? {
        var data = MySpUtis.getObject(this::class.java.name, this::class.java)
        if (data == null) {
            return null
        } else {
            return data as T
        }
    }

    open fun clear() = MySpUtis.clear(this::class.java.name)

    fun <T> fromJson(json: String): T {
        return Gson().fromJson(json, this::class.java) as T
    }


    fun resetDefault() {
        defaultValueReflex(this)
    }

    fun resetToNull() {
        defaultResetToNull(this)
    }

    fun change() {
        notifyDataSetChanged = System.currentTimeMillis()
    }
}


