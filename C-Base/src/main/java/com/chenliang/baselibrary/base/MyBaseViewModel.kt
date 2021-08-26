package com.chenliang.baselibrary.base

import androidx.lifecycle.*
import com.chenliang.baselibrary.annotation.MyRetrofitGoValue
import com.chenliang.baselibrary.annotation.defaultValueReflex
import com.chenliang.baselibrary.net.BaseResponse
import com.chenliang.baselibrary.net.log.BaseBeanLog
import com.chenliang.baselibrary.net.utils.MyApiReflex
import com.chenliang.baselibrary.net.utils.MyHttpDB
import com.chenliang.baselibrary.utils.toast
import com.google.gson.stream.MalformedJsonException
import gorden.rxbus2.RxBus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONException
import retrofit2.Call
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.net.UnknownServiceException
import javax.net.ssl.SSLException
import kotlin.collections.set

open class MyBaseViewModel : ViewModel() {

    var dataMap = HashMap<String, MutableLiveData<BaseResponse<Any>>>()


    fun <T> go(
        block: () -> Call<BaseResponse<T>>
    ): MutableLiveData<BaseResponse<T>> {

        var cell = block()
        var path = cell.request().url.toString()


        var mutableLiveDataKey=this.toString()+path.split("?")[0]

        var data = dataMap[mutableLiveDataKey]

        if (data == null) {
            data = MutableLiveData<BaseResponse<Any>>()
            dataMap[mutableLiveDataKey] = data
        }

        viewModelScope.launch(Dispatchers.IO) {
            var myRetrofitGoValue: MyRetrofitGoValue? = null
            var responseBean = try {

                //是否启用缓存
                myRetrofitGoValue = getMyRetrofitGoValue(path)
                if (myRetrofitGoValue.cache) {
                    initCache(myRetrofitGoValue, path!!, data, viewModelScope)
                } else {
                    if (myRetrofitGoValue.loading) {
                        RxBus.get().send(31415926, path)//显示dialog
                    }
                }

                delay(2000)//模拟延迟,上线的时候，注释掉
                var res = cell.execute()
                if (res != null && res.isSuccessful) {
                    res.body()
                } else {
                    var bean = BaseResponse<T>()
                    if (res.code() == 404) bean.message = "找不到地址"
                    bean
                }

            } catch (e: Exception) {
                apiException<T>(e)
            }

            //使用默认值
            defaultValueReflex(responseBean!!)
            BaseBeanLog().send(myRetrofitGoValue!!.tag, path, responseBean!!)
            viewModelScope.launch(Dispatchers.Main) {
                data.value = responseBean as BaseResponse<Any>

                if (myRetrofitGoValue.failToast && responseBean.code != 0) {
                    toast(responseBean.message)

                }
                if (myRetrofitGoValue.successCode != 0 && responseBean.code == 0) {
                    RxBus.get().send(myRetrofitGoValue.successCode)
                }

                if (myRetrofitGoValue.failCode != 0 && responseBean.code != 0) {
                    RxBus.get().send(myRetrofitGoValue.failCode)
                }

            }
            //把数据更新到缓存
            if (responseBean?.code == 0) {
                MyHttpDB.putCache(path, responseBean);
            }

            delay(100)
            RxBus.get().send(31415927, path)
        }
        return data!! as MutableLiveData<BaseResponse<T>>
    }

    fun <T> initCache(
        myRetrofitGoValue: MyRetrofitGoValue,
        path: String,
        data: MutableLiveData<BaseResponse<T>>,
        viewModelScope: CoroutineScope
    ) {

        var hasCache = false

        if (!path.isNullOrEmpty()) {
            var cacheResponse = MyHttpDB.getCache(path, BaseResponse<T>()::class.java)

            if (cacheResponse != null) {
                cacheResponse.cache = true
                viewModelScope.launch(Dispatchers.Main) { data.value = cacheResponse }
                hasCache = true
            }

        }

        if (hasCache) {
            if (myRetrofitGoValue.hasCacheLoading) {
                RxBus.get().send(31415926, path)//显示dialog
            }
        } else if (myRetrofitGoValue.loading) {
            RxBus.get().send(31415926, path)//显示dialog
        }


    }

    /**
     * 网络异常处理
     */
    private fun <T> apiException(e: Exception): BaseResponse<T> {
        e.printStackTrace()
        var bean = BaseResponse<T>()
        when (e) {
            is SocketTimeoutException -> bean.message = "网络超时"
            is HttpException -> {
                when {
                    e.code() == 403 -> bean.message = "访问被拒绝"
                    e.code() == 404 -> bean.message = "找不到路径"
                    e.code().toString().startsWith("4") -> bean.message = "客户端异常"
                    e.code().toString().startsWith("5") -> bean.message = "服务器异常"
                }
            }
            is ConnectException -> bean.message = "网络链接异常"
            is SSLException -> bean.message = "证书异常"
            is UnknownHostException -> bean.message = "找不到服务器，请检查网络"
            is JSONException -> bean.message = "数据解析异常，非法JSON"
            is MalformedJsonException -> bean.message = "数据解析异常，非法JSON"
            is UnknownServiceException -> bean.message = "未知服务器路径"
            is Exception -> bean.message = "程序异常" + e.javaClass.name
        }
        return bean
    }

    /**
     * 获取MyRetrofitGo注解loading和cache
     */
    private fun getMyRetrofitGoValue(path: String): MyRetrofitGoValue {
        MyApiReflex.value.forEach {
            if (path.split("?")[0].endsWith(it.key)) {
                return it.value
            }
        }
        return MyRetrofitGoValue(
            loading = false,
            cache = false,
            hasCacheLoading = false,
            tag = "",
            failToast = false,
            successCode = 0,
            failCode = 0
        )
    }

    /**对象
     * MutableLiveData<BaseEntity<T>简写方案
     */
    fun <T> initData() = lazy { MutableLiveData<BaseResponse<T>>() }

    /**数组
     * MutableLiveData<BaseEntity<ArrayList<T>>>简写方案
     */
    fun <T> initDatas() = lazy { MutableLiveData<BaseResponse<ArrayList<T>>>() }


}

/**
 * 观察者监听数据变化
 */
fun <T> MutableLiveData<T>.obs(owner: LifecycleOwner, func: (t: T) -> Unit) = this.apply {
    if (!this.hasObservers())
        this.observe(owner, Observer<T> { func(it) })
}