package com.chenliang.mvvmc

import android.telecom.Call
import android.util.JsonReader
import com.chenliang.baselibrary.base.Data
import com.chenliang.baselibrary.base.MyBaseApiService
import com.chenliang.baselibrary.net.BaseResponse
import org.json.JSONObject
import java.util.*

typealias Data<T> = Call<BaseResponse<T>>
typealias Datas<T> = Call<BaseResponse<ArrayList<T>>>

interface ApiService {

    @MyRetrofitGo(loading = true, cache = true, hasCacheLoading = false)
    @POST("home/remind")
    fun getData(
        @Query("username") username: String,
        @Query("age") age: String,
    ): Data<JSONObject>

    @MyRetrofitGo(loading = true, cache = true, hasCacheLoading = false)
    @POST("home/remind")
    fun getData(
        @Query("username") username: String,
        @Query("age") age: String,
    ): Datas<JSONObject>
}