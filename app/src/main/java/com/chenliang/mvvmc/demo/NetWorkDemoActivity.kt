package com.chenliang.mvvmc.demo

import android.Manifest
import com.chenliang.baselibrary.annotation.My
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.base.obs
import com.chenliang.baselibrary.utils.*
import com.chenliang.mvvmc.R
import com.chenliang.mvvmc.bean.BeanUser
import com.chenliang.mvvmc.databinding.ActivityMainBinding
import com.chenliang.mvvmc.vm.AccountViewModel
import kotlinx.android.synthetic.main.activity_login.*

@My(myToolbarTitle = "登录", myToolbarShow = true)
class NetWorkDemoActivity : MyBaseActivity<ActivityMainBinding>() {
    override fun layoutId() = R.layout.activity_login
    override fun initCreate() {

        var user = BeanUser().get()
        if (user != null) {//已登录
        }

        login.click { loginAction() }


    }

    private fun loginAction() {
        var loginVM = initVM(AccountViewModel::class.java)
        var name = account.text.toString()
        var pass = password.text.toString()

        if (hasNull(name, "请输入账号", pass, "请输入密码")) {

            return
        }

        loginVM.login(name, pass).obs(this) {
            it.c { }
            it.y { loginSucess(it.data!!) }
            it.n { loginFail(it.message) }

        }
    }

    /**
     * 登录成功
     */
    private fun loginSucess(user: BeanUser) {
        user.save()//保存user数据到sp缓存
        toAct(ToolBarDemoActivity::class.java)
    }

    /**
     * 登录失败
     */
    fun loginFail(message: String) {
        toast(message)
    }
}