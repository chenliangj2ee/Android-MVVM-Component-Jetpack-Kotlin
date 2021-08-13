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
* **弃用@Aroute，使用自定义@MyRoute路由，编译器自动化路由配置管理，更简单的路由跳转参数传递与参数接收**
* **更简单的SharedPreferences使用，再也不需要自己写setXXX()，getXXX()了，皆有编译器自动生成**
* **最新的技术实现方案，Retrofit2、OkHttp3、ViewModel、DataBinding、LiveData等jetpack组件，以及Kotlin协程技术方案**


# 组件化说明
* **每个module都有独自的AndroidMinifast.xml文件，各自模块的权限，activity，service等声明，均在各自module声明；**
* **每个module都有独自的Application，启动就初始化的代码均放在该Module的application里，可以直接脱离主app直接运行；**
* **每个module都有独自的ApiService，且需在各自的Application中注册，以保证module在application模式下可以直接运行；**
# 有多精简？先举了栗子，以登录为栗：
## 一、登录接口定义：
```
@MyApiService(
    mName = "API2",
    mPath = "http://www.chenliang.com/app/",//成产环境
    mDevPath = "http://www.chenliang.com/dev/app/",//开发环境
    mTestPath = "http://www.chenliang.com/test/app/"//测试环境
)
interface ApiService {
    @MyRetrofitGo(mTag = "登录", mLoading = true,mCache = false ,mFailToast = true,mSuccessCode = 1001)
    @POST("home/login")
    fun login(
        @Query("account") account: String,
        @Query("password") password: String
    ): Data<BeanUser>
}
```
## 二、登录ViewMode：

* **通过@MyApiService注解指定的mName名称，直接调用接口：**
```
class AccountViewModel : MyBaseViewModel() {
    fun login(account: String, pass: String) = go { API.login(account, pass) }
}
```
## 三、登录Activity：

```
@MyClass(mToolbarTitle = "登录")
@MyRoute(mPath = "/account/Login")
class LoginActivity : MyBaseActivity<AccountActLoginBinding, AccountViewModel>() {
    var user = BeanUser()
    override fun initCreate() {
        mBinding.user = user
        //监听编辑框输入状态，手机号设置成130 7876 7657 格式
        account.changed { account.setText(it.insert(" ", 3, 7)) }
    }

    //去注册
    fun registerAction() { goto(RegisterActivity::class.java, "user", user) }

    fun loginAction() {
        //登录验证
        if (user.name.check(MyCheck.empty, "请输入账号", MyCheck.mobilePhone, "手机号格式不正确") ||
            user.password.check(MyCheck.empty, "请输入密码", MyCheck.LENGTH(6, 12), "密码长度在6-12之间")
        ) return
        //登录接口
        mViewModel.login(user.name, user.password).obs(this@LoginActivity) {  it.y { loginSuccess(it) }  }
    }

    private fun loginSuccess(user: BeanUser) {
        user.save()//把登录后user数据保存到sp
        goto(appMain)//跳转到Main界面
        finish()
    }

    //注册后，登录界面回显账号信息
    @Subscribe(code = 100)
    fun eventRegister(user: BeanUser) {
        this.user = user
        user.password = ""
        mBinding.user = user
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


 //目标Activity or Fragment参数接收
 @MyField(mKey = "beanUser")//指定key为:beanUser
 lateinit var user: BeanUser

 @MyField(mKey = "param1")//指定key为:param1
 lateinit var param1: String

 @MyField//默认key为变量名称param2
 lateinit var param2: String

```
*  **组件之间的消息传递与事件回调**
```
//仅发送数据
var user=BeanUser("tom",12)
user.postSelf(1002)

//仅接受数据
@Subscribe(code = 1002)
fun eventRegister(user: BeanUser) {
      //接收到user
}

//发送数据、并且回调接收数据
var user=BeanUser("tom",12)
user.postSelf(1002){
    mytoast(it.getStringExtra("message")!!)
}

//接受数据，并且回调数据给发送者
@Subscribe(code = 100)
fun eventCallBack(event: RxBusEvent<BeanUser>) {
   mytoast("消息收到${event.data.toJson}")
   event.callback("message", "回调成功")
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
* **mDialogGravity：指定位置**
* **mDialogAnimation：指定是否启用动画[Gravity.BOTTOM:底部向上动画，其他；伸缩、透明度动画]**
* **mDialogAnimationTime：指定动画时长**
* **mDialogTransparent：指定是否透明**
```
     @MyClass(mDialogGravity = Gravity.BOTTOM, mDialogTransparent = true ，mDialogAnimation = true,mDialogAnimationTime = 300)
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
# 七、各个组件根据@MyRoute注解，统一自动生成配置文件MyRoutePath类，如Account组件模块：
```
@MyRoute(mPath = "/account/login")
class LoginActivity : MyBaseActivity<AccountActLoginBinding, AccountViewModel>() {//Activity
        .
        .
        .
        .
}
@MyRoute(mPath = "/account/register")
class RegisterActivity : MyBaseActivity<AccountActLoginBinding, AccountViewModel>() {//Activity
        .
        .
        .
        .
}
@MyRoute(mPath = "/account/my")
class MyFragment : MyBaseFragment<AccountFgMyBinding, DefaultViewModel>() {//Fragment
        .
        .
        .
        .
}
```
各个组件会自动生成路由配置管理类MyRoutePath

```
public object MyRoutePath {
  public val accountLogin: String = "/account/Login|com.chenliang.account.act.LoginActivity"

  public val accountRegister: String = "/account/Register|com.chenliang.account.act.RegisterActivity"
  
  public val accountMy: String = "/account/my|com.chenliang.account.fragment.MyFragment"
}

```
路由跳转，比如由app Module组件跳到Account Module组件的登录Activity，直接调用如下代码：

```
 goto(accountLogin)

```

# 八、更简单的SharedPreferences使用，在Base组件中只需要配置MyConfig类,则会自动生成MySp类，如下：
### MyConfit配置
```
class MyConfig {
    @MySpConfig
    var isLogin = false;

    @MySpConfig
    var isFirst = false
    
    @MySpConfig
    var ohter = ""
 
}

```
### 自动生成MySp类，可直接调用setXXX，getXXX方法保存和设置SharedPreferences数据

```
public object MySp {
  public fun setLogin(login: Boolean): Unit {
    MySpUtis.putBoolean("isLogin", login)
  }

  public fun isLogin(): Boolean = MySpUtis.getBoolean("isLogin")

  public fun setFirst(first: Boolean): Unit {
    MySpUtis.putBoolean("isFirst", first)
  }

  public fun isFirst(): Boolean = MySpUtis.getBoolean("isFirst")
  
  public fun setOhter(ohter: String): Unit {
    MySpUtis.putString("ohter", ohter)
  }

  public fun isFirst(): String = MySpUtis.getString("ohter")
}
```



