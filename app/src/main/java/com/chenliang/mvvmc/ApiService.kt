package com.chenliang.mvvmc

import com.chenliang.baselibrary.net.BaseResponse
import com.chenliang.mvvmc.bean.BeanUser
import com.chenliang.baselibrary.annotation.MyRetrofitGo
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.*

typealias Data<T> = Call<BaseResponse<T>>
typealias Datas<T> = Call<BaseResponse<ArrayList<T>>>


interface ApiService {

    @MyRetrofitGo(loading = true, cache = false, hasCacheLoading = false)
    @POST("home/login")
    fun login(
        @Query("account") account: String,
        @Query("password") password: String
    ): Data<BeanUser>

    @MyRetrofitGo(loading = true, cache = true, hasCacheLoading = false)
    @POST("home/reminds")
    fun getDatas(
        @Query("userId") userId: String
    ): Datas<JSONObject>
}