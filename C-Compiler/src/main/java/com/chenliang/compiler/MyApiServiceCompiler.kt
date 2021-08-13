package com.chenliang.compiler

import com.chenliang.annotation.ApiModel
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



        mApiServices?.forEach {


            var anno = it.getAnnotation(MyApiService::class.java)
            var name = anno.mName
            var path = anno.mPath
            var testPath=anno.mTestPath
            var devPath=anno.mDevPath

            var apiName = it.simpleName.toString()

            var myApiFactory = TypeSpec.objectBuilder("MyApiFactory$name")
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
                    "if (%T.dev) api = %T(%S, %T::class.java)",
                    ClassName("com.chenliang.annotation", "ApiModel"),
                    ClassName("com.chenliang.baselibrary.net", "initAPI"),
                    devPath,
                    ClassName(apiPackageName, apiName)
                )
                .addStatement(
                    "if (%T.test) api = %T(%S, %T::class.java)",
                    ClassName("com.chenliang.annotation", "ApiModel"),
                    ClassName("com.chenliang.baselibrary.net", "initAPI"),
                    testPath,
                    ClassName(apiPackageName, apiName)
                )
                .addStatement(
                    "if (%T.release) api = %T(%S, %T::class.java)",
                    ClassName("com.chenliang.annotation", "ApiModel"),
                    ClassName("com.chenliang.baselibrary.net", "initAPI"),
                    path,
                    ClassName(apiPackageName, apiName)
                )
                .build()

            myApiFactory.addInitializerBlock(codeBlock)

            val square = PropertySpec.builder(name, ClassName(apiPackageName, apiName))
                .receiver(Any::class)
                .setter(
                    FunSpec.setterBuilder()
                        .addParameter("value", String::class)
                        .build()
                )
                .getter(
                    FunSpec.getterBuilder()
                        .addStatement("return MyApiFactory$name.api!!")
                        .build()
                ).mutable(true)
                .build()

            val file = FileSpec.builder(
                "com.chenliang.processor." + mModuleName!!.replace("-", ""),
                "MyApiFactory$name"
            ).addProperty(square)
                .addType(myApiFactory.build())
                .build()



            try {
                file.writeTo(mFiler)
            } catch (e: Exception) {

            }

        }




        return true
    }

}

