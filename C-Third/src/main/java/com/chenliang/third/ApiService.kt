package com.chenliang.third

import com.chenliang.baselibrary.net.BaseResponse
import retrofit2.Call
import java.util.*

typealias Data<T> = Call<BaseResponse<T>>
typealias Datas<T> = Call<BaseResponse<ArrayList<T>>>


interface ApiService {


}