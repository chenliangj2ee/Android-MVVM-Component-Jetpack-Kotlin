# MVVM-Component
## Android Kotlin MVVM-【极简轻量级】【组件化框架】----持续更新--未完结

# 结构
![结构](https://user-images.githubusercontent.com/4067327/125152474-577f7880-e17f-11eb-8e94-8813379e2d53.jpg)

# 有多精简？看Demo代码：
## 获取账号、密码，验证，登录成功后保存账号信息，失败会自带tosat提示，成功后带参跳转到Main页面，finish当前页；
## R.layout.login在哪里指定？ViewModel又在哪里初始化？AccountViewModel就2行代码？是的，极简框架，就这么简单

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

