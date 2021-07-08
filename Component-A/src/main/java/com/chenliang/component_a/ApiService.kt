package com.chenliang.component_a

import com.chenliang.baselibrary.annotation.MyRetrofitGo
import com.chenliang.baselibrary.net.BaseResponse
import com.chenliang.component_a.bean.BeanUserA
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.*

typealias Data<T> = Call<BaseResponse<T>>
typealias Datas<T> = Call<BaseResponse<ArrayList<T>>>


interface ApiService {

    @MyRetrofitGo(loading = true, cache = false, hasCacheLoading = false)
    @POST("home/logina")
    fun login(
        @Query("account") account: String,
        @Query("password") password: String
    ): Data<BeanUserA>

}