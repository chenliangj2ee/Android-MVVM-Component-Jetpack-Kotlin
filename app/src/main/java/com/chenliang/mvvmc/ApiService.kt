package com.chenliang.mvvmc

import com.chenliang.annotation.MyApiService
import com.chenliang.baselibrary.base.MyBaseResponse
import retrofit2.Call
import java.util.*

typealias Data<T> = Call<MyBaseResponse<T>>
typealias Datas<T> = Call<MyBaseResponse<ArrayList<T>>>

@MyApiService(mName = "app",mPath = "http://www.baidu.com")
interface ApiService {


}