package com.chenliang.account.vm

import com.chenliang.baselibrary.base.MyBaseViewModel
import com.chenliang.processor.CAccount.API

class AccountViewModel : MyBaseViewModel() {
    fun login(account: String, pass: String) = go { API.login(account, pass) }
    fun register(account: String, pass: String) = go { API.register(account, pass) }
}