package com.chenliang.baselibrary.base

import android.app.Dialog
import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.stream.MalformedJsonException
import com.chenliang.baselibrary.BaseInit
import com.chenliang.baselibrary.R
import com.chenliang.baselibrary.annotation.MyRetrofitGoValue
import com.chenliang.baselibrary.debug.BaseBeanLog
import com.chenliang.baselibrary.utils.*
import com.chenliang.baselibrary.utils.MyHttpDB
import gorden.rxbus2.RxBus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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

    var dataMap = HashMap<String, MutableLiveData<MyBaseResponse<Any>>>()
    var dialogs = HashMap<String, Dialog>()

    fun <T> go(
        block: () -> Call<MyBaseResponse<T>>
    ): MutableLiveData<MyBaseResponse<T>> {

        var cell = block()
        var path = cell.request().url.toString()
        var key = cell.request().url.toJson()

//       log("request:"+  cell.request().url.toJson())

        var mutableLiveDataKey = this.toString() + path.split("?")[0]
//        log("VM key:$mutableLiveDataKey")

        var data = dataMap[mutableLiveDataKey]

        if (data == null) {
            data = MutableLiveData<MyBaseResponse<Any>>()
            dataMap[mutableLiveDataKey] = data
        }

        viewModelScope.launch(Dispatchers.IO) {
            var myRetrofitGoValue: MyRetrofitGoValue? = null
            var responseBean = try {
                //是否启用缓存
                myRetrofitGoValue = getMyRetrofitGoValue(path)
                if (myRetrofitGoValue.cache) {
                    initCache(myRetrofitGoValue, key!!, data, viewModelScope)
                } else {
                    if (myRetrofitGoValue.loading) {
                        showLoading(key)
                    }
                }

//                delay(2000)//模拟延迟,上线的时候，注释掉
                var res = cell.execute()
                if (res != null && res.isSuccessful) {
                    res.body()
                } else {
                    val errorResponse =
                        Gson().fromJson(res.errorBody()?.string(), MyBaseErrorResponse::class.java)
                    var bean = MyBaseResponse<T>()
                    bean.code = res.code()
//                    bean.code = errorResponse.code.toInt()
                    bean.msg = errorResponse.msg ?: ""
                    bean.error = errorResponse.msg
                    log("接口异常：${path} \n ${errorResponse.toJson()}")
                    when {
                        bean.code == 401 -> bean.msg = "访问未受权"
                        bean.code == 403 -> bean.msg = "访问被拒绝"
                        bean.code == 404 -> bean.msg = "找不到路径"
                        bean.code.toString().startsWith("4") -> bean.msg = "客户端异常"
                        bean.code.toString().startsWith("5") -> bean.msg = "服务器异常，稍后再试"
                    }
                    bean
                }

            } catch (e: Exception) {
                apiException<T>(e)
            }


            //使用默认值
//            defaultValueReflex(responseBean!!)


            if (myRetrofitGoValue!!.mDataIsNullToError && responseBean!!.data == null) {
                responseBean!!.code = -1
            }

            //请求日志发送到统计
            BaseBeanLog().send(myRetrofitGoValue!!.tag, path, responseBean!!)


            viewModelScope.launch(Dispatchers.Main) {
                data.value = responseBean as MyBaseResponse<Any>

                //失败后toast提示
                if (myRetrofitGoValue.failToast && responseBean.code != 0) {
                    toast(responseBean.msg ?: "程序异常")
                }
                //发送成功Event
                if (myRetrofitGoValue.successCode != 0 && responseBean.code == 0) {

                    postDelayed( 100){  RxBus.get().send(myRetrofitGoValue.successCode)}

                }
                //发送失败Event
                if (myRetrofitGoValue.failCode != 0 && responseBean.code != 0) {
                    RxBus.get().send(myRetrofitGoValue.failCode)
                }

            }
            //把数据更新到缓存
            if (responseBean?.code == 0 && myRetrofitGoValue.cache) {
                if (responseBean.data != null) {
                    MyHttpDB.putCache(key, responseBean)
                } else {
                    MyHttpDB.clearCache(key)
                }
            }

            closeLoading(key)
        }
        return data!! as MutableLiveData<MyBaseResponse<T>>
    }

    private fun <T> initCache(
        myRetrofitGoValue: MyRetrofitGoValue,
        key: String,
        data: MutableLiveData<MyBaseResponse<T>>,
        viewModelScope: CoroutineScope
    ) {

        var hasCache = false

        if (!key.isNullOrEmpty() && myRetrofitGoValue.type != null) {
            var cacheJson = MyHttpDB.getCacheJson(key)

            if (cacheJson != null) {
                var response: MyBaseResponse<T>? =
                    Gson().fromJson(cacheJson, myRetrofitGoValue.type)
                if (response != null) {
                    response.code = 0
                    response.cache = true
                    response.cacheJson = cacheJson
                    viewModelScope.launch(Dispatchers.Main) { data.value = response }
                    hasCache = true
                }
            }

        }

        if (hasCache) {
            if (myRetrofitGoValue.hasCacheLoading) {
                showLoading(key)
            }
        } else if (myRetrofitGoValue.loading) {
            showLoading(key)
        }


    }

    /**
     * 网络异常处理
     */
    private fun <T> apiException(e: Exception): MyBaseResponse<T> {
        e.printStackTrace()
        loge(e.message+"")
        var bean = MyBaseResponse<T>()
        when (e) {
            is SocketTimeoutException -> bean.msg = "网络超时"
            is HttpException -> {
                when {
                    e.code() == 401 -> bean.msg = "访问未受权"
                    e.code() == 403 -> bean.msg = "访问被拒绝"
                    e.code() == 404 -> bean.msg = "找不到路径"
                    e.code().toString().startsWith("4") -> bean.msg = "客户端异常"
                    e.code().toString().startsWith("5") -> bean.msg = "服务器异常，稍后再试"
                }
            }
            is ConnectException -> bean.msg = "网络链接异常，请检查网络"
            is IllegalStateException -> bean.msg = "类型转换异常"
            is SSLException -> bean.msg = "证书异常"
            is UnknownHostException -> bean.msg = "找不到服务器，请检查网络"
            is JSONException -> bean.msg = "数据解析异常，非法JSON"
            is MalformedJsonException -> bean.msg = "数据解析异常，非法JSON"
            is UnknownServiceException -> bean.msg = "未知服务器路径"
            is Exception -> bean.msg = "服务器异常，稍后再试"
        }
        return bean
    }

    /**
     * 获取MyRetrofitGo注解loading和cache
     */
    private fun getMyRetrofitGoValue(path: String): MyRetrofitGoValue {
        MyRetrofitGoUtils.value.forEach {

//            /api-app/v1/app/user-favorite/pageUserFavorite?MyRetrofitGo=咨询列表&
            if (path.contains("MyRetrofitGo")) {
                var pathKey = path.split("?")[0] + "?" + path.split("?")[1].split("&")[0]
                if (pathKey.endsWith(it.key)) {
//                    log("接口[${it.value.tag}:$pathKey]返回类型${it.value.type}")
                    return it.value
                }
            }

            if (path.split("?")[0].endsWith(it.key)) {
//                log("接口[${it.value.tag}:${path.split("?")[0]}]返回类型${it.value.type}")
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
            failCode = 0,
            null,
            false
        )
    }

    /**对象
     * MutableLiveData<BaseEntity<T>简写方案
     */
    fun <T> initData() = lazy { MutableLiveData<MyBaseResponse<T>>() }

    /**数组
     * MutableLiveData<BaseEntity<ArrayList<T>>>简写方案
     */
    fun <T> initDatas() = lazy { MutableLiveData<MyBaseResponse<ArrayList<T>>>() }

    private fun showLoading(id: String) {
        viewModelScope.launch(Dispatchers.Main) {
            var dialog = BaseInit.act?.let { Dialog(it) }
            dialog?.setContentView(R.layout.base_loading)
            dialog?.window!!.setBackgroundDrawableResource(android.R.color.transparent);
            dialog?.show()
            dialog?.let {  dialogs[id] = dialog }

        }


    }

    private fun closeLoading(id: String) {
        viewModelScope.launch(Dispatchers.Main) {
            dialogs[id]?.dismiss()
            dialogs.remove(id)
        }
    }
}

/**
 * 观察者监听数据变化
 */
fun <T> MutableLiveData<T>.obs(owner: LifecycleOwner, func: (t: T) -> Unit) = this.apply {
    if (!this.hasObservers())
        this.observe(owner, Observer<T> { func(it) })
}

/**
 * 观察者监听数据变化
 */
fun <T> MutableLiveData<T>.obsf(func: (t: T) -> Unit) = this.apply {
    if (!this.hasObservers())
        this.observeForever(func)
}

