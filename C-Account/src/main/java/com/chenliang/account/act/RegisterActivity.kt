package com.chenliang.account.act

import com.chenliang.account.R
import com.chenliang.account.bean.BeanUser
import com.chenliang.account.databinding.AccountActRegisterBinding
import com.chenliang.account.vm.AccountViewModel
import com.chenliang.baselibrary.annotation.My
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.base.obs
import com.chenliang.baselibrary.utils.hasNull
import com.chenliang.baselibrary.utils.initVM

@My(myToolbarTitle = "注册", myToolbarShow = true)
class RegisterActivity : MyBaseActivity<AccountActRegisterBinding>() {
    override fun layoutId() = R.layout.account_act_register
    var user = BeanUser();
    lateinit var accountVM: AccountViewModel

    override fun initCreate() {
        accountVM = initVM(AccountViewModel::class.java)
        mBinding.user = user
        mBinding.act=this
    }

    fun registerAction() {

        with(user) {
            if (hasNull(name, "请输入账号", password, "请输入密码")) return

            accountVM.register(name, password).obs(this@RegisterActivity) {
                it.y { registerSuccess(it.data!!) }
            }
        }

    }

    /**
     * 登录成功
     */
    private fun registerSuccess(user: BeanUser) {
        user.save()
        finish()
    }


}