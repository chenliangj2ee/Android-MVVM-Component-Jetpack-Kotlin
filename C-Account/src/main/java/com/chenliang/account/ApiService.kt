package com.chenliang.account

import com.chenliang.account.bean.BeanUser
import com.chenliang.baselibrary.annotation.MyRetrofitGo
import com.chenliang.baselibrary.net.BaseResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.*

typealias Data<T> = Call<BaseResponse<T>>
typealias Datas<T> = Call<BaseResponse<ArrayList<T>>>


interface ApiService {
    @MyRetrofitGo(myTag = "登录", myLoading = true)
    @POST("home/login")
    fun login(
        @Query("account") account: String,
        @Query("password") password: String
    ): Data<BeanUser>

    @MyRetrofitGo(myTag = "注册", myLoading = true,myFailToast = true)
    @POST("home/register")
    fun register(
        @Query("account") account: String,
        @Query("password") password: String
    ): Data<BeanUser>


}