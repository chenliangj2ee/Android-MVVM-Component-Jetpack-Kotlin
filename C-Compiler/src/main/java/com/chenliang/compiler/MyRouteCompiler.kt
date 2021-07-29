package com.chenliang.compiler

import com.alibaba.android.arouter.facade.annotation.Route
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement


/**
 *
 * @Project: MVVM-Component
 * @Package: com.chenliang.compiler.test
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
@SupportedAnnotationTypes(value = ["com.alibaba.android.arouter.facade.annotation.Route"])
class MyRouteCompiler : AbstractProcessor() {
    private lateinit var mFiler: Filer
    private var mModuleName: String? = null

    init {
        System.out.println("processor Init--------------------------------------------")
    }

    override fun init(p0: ProcessingEnvironment?) {
        super.init(p0)
        mFiler = p0!!.filer
        mModuleName = p0.options["AROUTER_MODULE_NAME"]
        System.out.println("processor mModuleName-------------------------------------------- $mModuleName")
    }

    override fun process(ms: MutableSet<out TypeElement>?, en: RoundEnvironment?): Boolean {

        if (en == null) {
//            return false
            System.out.println("processor return-------------------------------------------- ")
        }


        var routes = en!!.getElementsAnnotatedWith(Route::class.java)
        var myRoute = TypeSpec.objectBuilder("MyRoute")
        routes.forEach {
            var route = it.getAnnotation(Route::class.java)

            if (route != null) {
                var path = route.path
                var keys = path.split("/")

                keys = keys.filter { !it.isNullOrEmpty() }.map {
                    it.substring(0, 1).toUpperCase() + it.substring(1)
                }
                var key = keys.joinToString(separator = "")
                key = key.substring(0, 1).toLowerCase() + key.substring(1)
                myRoute.addProperty(
                    PropertySpec.builder(key, String::class)
                        .initializer("%S", path)
                        .build()
                )
            }


        }


        val file =
            FileSpec.builder("com.chenliang.processor" + mModuleName!!.replace("-", ""), "MyRoute")
                .addType(myRoute.build()).build()

        try {
            file.writeTo(mFiler)
        } catch (e: Exception) {
            e.printStackTrace()
        }


        System.out.println("processor Finish-------------------------------------------- ")
        return true
    }

}

