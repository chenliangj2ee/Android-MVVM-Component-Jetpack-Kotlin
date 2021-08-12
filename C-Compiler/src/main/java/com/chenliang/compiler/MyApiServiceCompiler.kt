package com.chenliang.compiler

import com.chenliang.annotation.MyApiService
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement


/**
 *
 * @Project: MVVM-Component
 * @Package: com.chenliang.compiler
 * @author: chenliang
 * @date: 2021/07/28
 */


/**
 * 路由全局自动生成器
 * @property mFiler Filer
 * @property mModuleName String?
 */
@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes(value = ["com.chenliang.annotation.MyApiService"])
class MyApiServiceCompiler : AbstractProcessor() {
    private lateinit var mFiler: Filer
    private var mModuleName: String? = null

    init {
    }

    override fun init(p0: ProcessingEnvironment?) {
        super.init(p0)
        mFiler = p0!!.filer
        mModuleName = p0.options["AROUTER_MODULE_NAME"]
    }

    override fun process(ms: MutableSet<out TypeElement>?, en: RoundEnvironment?): Boolean {
        if (en == null) {
//            return false
        }
        var mApiServices = en!!.getElementsAnnotatedWith(MyApiService::class.java)




        var myApiFactory = TypeSpec.objectBuilder("MyApiFactory")
        var codeBlockApi = CodeBlock.Builder().add(
            "var  Any.API: ApiService\n" +
                    "            private set(value) {}\n" +
                    "            get() = MyApiFactory.api!!"
        )
        mApiServices?.forEach {


            var anno = it.getAnnotation(MyApiService::class.java)
            var name = anno.mName
            var path = anno.mPath
            var apiName = it.simpleName.toString()
            println(
                "MyApiServiceCompiler--------------  it.simpleName:${
                    it.asType().toString()
                }   "
            )
//            com.chenliang.account.ApiService
            var apiPackageName = it.asType().toString().replace(".$apiName", "")

            //属性
            var pro = PropertySpec.builder(
                "api",
                ClassName(apiPackageName, apiName).copy(nullable = true)
            )
                .mutable()
                .initializer("null")
                .build()
            myApiFactory.addProperty(pro)
            //init方法
            var codeBlock = CodeBlock.builder()
                .addStatement(
                    "api = %T(%S, %T::class.java)",
                    ClassName("com.chenliang.baselibrary.net", "initAPI"),
                    path,
                    ClassName(apiPackageName, apiName)
                )
                .build()

            myApiFactory.addInitializerBlock(codeBlock)


        }
        val file = FileSpec.builder(
            "com.chenliang.processor" + mModuleName!!.replace("-", ""),
            "MyApiFactory"
        )
            .addType(myApiFactory.build())
            .build()


        try {
            file.writeTo(mFiler)
        } catch (e: Exception) {

        }


        return true
    }

}

