# Android-MVVM-Component-Jetpack-Kotlin
## 超级简洁、彻底组件化的轻量级Android Kotlin Jetpack MVVM框架----持续更新----未完结
## 框架追求：简洁！简洁！简洁！没办法，老子就是这么懒，多写一行代码，都感觉累！

# 结构
![结构](https://user-images.githubusercontent.com/4067327/125152474-577f7880-e17f-11eb-8e94-8813379e2d53.jpg)
# 特点：
* **彻底组件化，且更简洁，Module具有独立的Application、AndroidMinifast、资源文件等；Application和Library的切换更加快捷；**
* **超级简洁、且多功能的网络层封装，自带2级缓存，App端内嵌了日志查看，测试人员，接口错误，该知道找谁了吧；**
* **更简洁的组件使用，更少的代码实现最全的功能，灵活扩展，随时扩展新的组件；**
* **组件与组件之间，主App与组件之间不存在任何依赖关系，快速集成，也可快速分离**
* **最新的技术实现方案，Retrofit2、OkHttp3、ViewModel、DataBinding、LiveData等jetpack组件，以及Kotlin协程技术方案**


# 组件化说明
* **每个module都有独自的AndroidMinifast.xml文件，各自模块的权限，activity，service等声明，均在各自module声明；**
* **每个module都有独自的Application，启动就初始化的代码均放在该Module的application里，可以直接脱离主app直接运行；**
* **每个module都有独自的ApiService，且需在各自的Application中注册，以保证module在application模式下可以直接运行；**
# 有多精简？先举了栗子，以登录为栗：
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
@MyClass(myToolbarTitle = "登陆")
class LoginActivity : MyBaseActivity<AccountActLoginBinding, AccountViewModel>() {

    var user = BeanUser()
    
    override fun initCreate() { mBinding.user = user  }

    fun registerAction() {  goto(RegisterActivity::class.java)  }

    fun loginAction() {
            if (hasNull(user.name, "请输入账号",user.password, "请输入密码"))
                return
            mViewModel.login(name, password).obs(this@LoginActivity) {  it.y { loginSuccess(it.data!!) }  }
    }

    private fun loginSuccess(user: BeanUser) {
        user.save()
        goto("/app/main", "user",user)
        finish()
    }

}
```
* **R.layout.login在哪里指定？**
* **ViewModel在哪里初始化？**
* **AccountViewModel就1行代码？**
* **是的，极简框架，就这么简单！**
# 四、组件之间的跳转与消息传递
*  **组件之间的跳转与参数传递**
```
 //跨组件--->跳转到目标页【跨组件，建议使用path跳转，当然，也可以使用class跳转】
 goto("/app/main", "username", "tom", "age", 15)
 goto("/app/fragment", "user",user)
 //组件内--->跳转到目标页【组件内，建议使用class跳转，当然，也可以使用path跳转】
 goto(MainActivity::class.java, "username","tom",  "id","UID121231","age", 15 ,"sex",2)
 goto(UserFragment::class.java, , "user",user,"param1", "value1","param2",vlue2, "param3", true ,"param4",2F,......)//想传递几个值，后面跟上即可


 //目标页参数接收
 @MyField(myKey = "user")//指定key:user
 lateinit var user: BeanUser

 @MyField(myKey = "param1")//指定key:param1
 lateinit var param1: String

 @MyField//默认key为变量名称
 lateinit var param2: String

```
*  **组件之间的消息传递**
```
//发送数据
var user=BeanUser("tom",12)
user.postSelf(1002)

//接受数据
@Subscribe(code = 1002)
fun eventRegister(user: BeanUser) {
      //接收到user
}
```

# 五、View组件的使用，更简单，例如Dialog：
### 默认dialog：
```
        MyDialog().message("确定删除用户？")
            .y { toast("确定被点击") }
            .n { toast("取消被点击") }
            .show(this)

        //或者
        dialog("确定删除用户？")
            .y { toast("确定被点击") }
            .n { toast("取消被点击") }
            .show(this)
```
### 默认dialog【自定义文案】：
```
        MyDialog().message("确定提交订单？")
            .y("提交") { toast("提交被点击") }
            .n("关闭") { toast("关闭被点击") }
            .show(this)
        //或者
        dialog("确定删除用户？")
            .y("提交") { toast("提交被点击") }
            .n("关闭") { toast("关闭被点击") }
            .show(this)
```
### 自定义dialog：
* **myDialogGravity：指定位置**
* **myDialogAnimation：指定是否启用动画[Gravity.BOTTOM:底部向上动画，其他；伸缩、透明度动画]**
* **myDialogAnimationTime：指定动画时长**
* **myDialogTransparent：指定是否透明**
```
     @MyClass(myDialogGravity = Gravity.BOTTOM, myDialogTransparent = true ，myDialogAnimation = true,*myDialogAnimationTime = 300)
     class DialogDemo : MyBaseDialog<DialogLayoutBinding>() {
        override fun initCreate() {
            mRootView.confirm.click { dismiss() }
        }
     }
```
![Video_20210715_121413_214](https://user-images.githubusercontent.com/4067327/125656327-2da3c27e-37b8-4f9f-a5b3-9374696569c5.gif)



# 六、更精简的RecyclerView列表分页功能，2行代码，包含了下拉刷新，加载更多，下一页预加载，且支持多TypeItem：
```
   refreshRecycler.bindData<BeanItem> { (it.binding as ItemRecyclerviewBinding).item = it }
   refreshRecycler.loadData { httpGetData() }
```
![Video_20210714_084908_861](https://user-images.githubusercontent.com/4067327/125624671-a129958c-5f45-4519-832a-35250ea0a932.gif)


