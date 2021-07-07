package com.chenliang.mvvmc.vm

import com.chenliang.baselibrary.base.MyBaseViewModel
import com.chenliang.baselibrary.net.go
import com.chenliang.mvvmc.API

class AccountViewModel : MyBaseViewModel() {
    fun test(account: String, pass: String) = go { API.login(account, pass) }
}