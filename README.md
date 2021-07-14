# MVVM-Component
## Android Kotlin MVVM-【极简轻量级】【组件化框架】----持续更新--未完结

# 结构
![结构](https://user-images.githubusercontent.com/4067327/125152474-577f7880-e17f-11eb-8e94-8813379e2d53.jpg)
# 说明
    1、每个module都自己独自的AndroidMinifast.xml文件，各自模块的权限，activity，service等声明，均在各自module声明；
    2、每个module都有自己的Application，各自模块需要启动就初始化的代码均放在自己的application里，可以直接脱离主app直接运行；
    3、每个module都有自己的ApiService，且需在各自的Application中注册，以保证module在application模式下可以直接运行；
# 有多精简？以登录为例：
## 一、登录接口定义：
```
interface ApiService {
    @MyRetrofitGo(myTag = "登录", myLoading = true,myFailToast = true)
    @POST("home/login")
    fun login(
        @Query("account") account: String,
        @Query("password") password: String
    ): Data<BeanUser>
}
```
## 二、登录ViewMode：
```
class AccountViewModel : MyBaseViewModel() {
    fun login(account: String, pass: String) = go { API.login(account, pass) }
}
```
## 三、登录Activity：

```
@My(myToolbarTitle = "登录")
class LoginActivity : MyBaseActivity<AccountActLoginBinding, AccountViewModel>() {
    var user = BeanUser()
    override fun initCreate() {
        mBinding.user = user
        mBinding.act = this
    }

    fun registerAction() {
        goto(RegisterActivity::class.java)
    }

    fun loginAction() {
        with(user) {
            if (hasNull(name, "请输入账号", password, "请输入密码")) return
            mViewModel.login(name, password).obs(this@LoginActivity) {  it.y { loginSuccess(it.data!!) }  }
        }
    }

    private fun loginSuccess(user: BeanUser) {
        user.save()
        goto("/app/main", "username", "tom", "age", 15)
        finish()
    }

}
```
## R.layout.login在哪里指定？
## ViewModel又在哪里初始化？
## AccountViewModel就1行代码？
## 是的，极简框架，就这么简单！

# 四、组件的使用，更简单，例如Dialog：
```
     MyDialog().message("确定删除用户？")
            .y { toast("确定被点击") }
            .n { toast("取消被点击") }
            .show(this)
            
    或自定义文案
    MyDialog().message("确定提交订单？")
            .y("提交") { toast("确定被点击") }
            .n("关闭") { toast("取消被点击") }
            .show(this)
```
# 五、更精简的RecyclerView列表分页功能，2行代码，包含了下拉刷新，加载更多，下一页预加载，切支持多TypeItem：
```
   refreshRecycler.bindData<BeanItem> { (it.binding as ItemRecyclerviewBinding).item = it }
   refreshRecycler.loadData { httpGetData() }
```


