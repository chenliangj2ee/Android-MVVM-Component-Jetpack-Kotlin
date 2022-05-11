package com.chenliang.third

import com.chenliang.baselibrary.base.MyBaseResponse
import retrofit2.Call
import java.util.*

typealias Data<T> = Call<MyBaseResponse<T>>
typealias Datas<T> = Call<MyBaseResponse<ArrayList<T>>>


interface ApiService {


}