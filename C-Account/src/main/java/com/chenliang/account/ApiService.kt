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
    @MyRetrofitGo(mTag = "登录", mLoading = true,mFailToast = true)
    @POST("home/login")
    fun login(
        @Query("account") account: String,
        @Query("password") password: String
    ): Data<BeanUser>

    @MyRetrofitGo(mTag = "注册", mLoading = true,mFailToast = true)
    @POST("home/register")
    fun register(
        @Query("account") account: String,
        @Query("password") password: String
    ): Data<BeanUser>


    @MyRetrofitGo(mTag = "添加测试",mCache = false,mLoading = true,mFailToast = true,mSuccessCode = 1001)
    @POST("home/add")
    fun addTest(
        @Query("account") account: String,
        @Query("password") password: String
    ): Data<BeanUser>


}