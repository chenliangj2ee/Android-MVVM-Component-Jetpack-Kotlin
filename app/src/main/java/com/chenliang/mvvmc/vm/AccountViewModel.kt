package com.chenliang.mvvmc.vm

import com.chenliang.baselibrary.base.MyBaseViewModel
import com.chenliang.mvvmc.API

class AccountViewModel : MyBaseViewModel() {
    fun login(account: String, pass: String) = go { API.login(account, pass) }
}