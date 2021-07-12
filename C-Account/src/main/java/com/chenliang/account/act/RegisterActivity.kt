package com.chenliang.account.act

import com.chenliang.account.bean.BeanUser
import com.chenliang.account.databinding.AccountActRegisterBinding
import com.chenliang.account.vm.AccountViewModel
import com.chenliang.baselibrary.annotation.My
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.base.obs
import com.chenliang.baselibrary.utils.goto
import com.chenliang.baselibrary.utils.hasNull
import com.chenliang.baselibrary.utils.sendSelf

@My(myToolbarTitle = "注册" )
class RegisterActivity : MyBaseActivity<AccountActRegisterBinding, AccountViewModel>() {
    var user = BeanUser()

    override fun initCreate() {
        mBinding.user = user
        mBinding.act = this
    }

    fun registerAction() {

        with(user) {
            if (hasNull(name, "请输入账号", password, "请输入密码")) return

            mViewModel.register(name, password).obs(this@RegisterActivity) {
                it.y { registerSuccess(it.data!!) }
                it.n { registerFail() }
            }
        }

    }

    private fun registerFail() {
        user.sendSelf(100)
        finish()
    }

    private fun registerSuccess(user: BeanUser) {
        user.save()
        goto("/app/main", "username", "tom", "age", 15)
        finish()
    }

}