package com.chenliang.account.vm

import com.chenliang.account.API
import com.chenliang.baselibrary.base.MyBaseViewModel

class AccountViewModel : MyBaseViewModel() {
    fun login(account: String, pass: String) = go { API.login(account, pass) }
    fun register(account: String, pass: String) = go { API.register(account, pass) }
}