# MVVM-Component
## Android Kotlin MVVM-【极简轻量级】【组件化框架】----持续更新--未完结

# 结构
![结构](https://user-images.githubusercontent.com/4067327/125152474-577f7880-e17f-11eb-8e94-8813379e2d53.jpg)

# 有多精简？以登录为例：
## 登录接口定义：
'''
interface ApiService {
    @MyRetrofitGo(myTag = "登录", myLoading = true,myFailToast = true)
    @POST("home/login")
    fun login(
        @Query("account") account: String,
        @Query("password") password: String
    ): Data<BeanUser>
}
'''
## 登录ViewMode：
```
class AccountViewModel : MyBaseViewModel() {
    fun login(account: String, pass: String) = go { API.login(account, pass) }
}
```
## 登录Activity：

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
            mViewModel.login(name, password).obs(this@LoginActivity) {
                it.y { loginSuccess(it.data!!) }
            }
        }
    }

    private fun loginSuccess(user: BeanUser) {
        user.save()
        goto("/app/main", "username", "tom", "age", 15)
        finish()
    }

}
```
## R.layout.login在哪里指定？ViewModel又在哪里初始化？AccountViewModel就2行代码？是的，极简框架，就这么简单！


