package com.chenliang.compiler

import com.chenliang.annotation.MyRoute
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
@SupportedAnnotationTypes(value = ["com.chenliang.annotation.MyRoute"])
class MyRouteCompiler : AbstractProcessor() {
    private lateinit var mFiler: Filer
    private var mModuleName: String? = null

    init {
//        System.out.println("MyRouteCompiler Processor Init--------------------------------------------")
    }

    override fun init(p0: ProcessingEnvironment?) {
        super.init(p0)
        mFiler = p0!!.filer
        mModuleName = p0.options["AROUTER_MODULE_NAME"]
//        System.out.println("MyRouteCompiler Processor  mModuleName-------------------------------------------- $mModuleName")
    }

    override fun process(ms: MutableSet<out TypeElement>?, en: RoundEnvironment?): Boolean {

        if (en == null) {
//            return false
        }

        var routes = en!!.getElementsAnnotatedWith(MyRoute::class.java)

        var myRoute = TypeSpec.objectBuilder("MyRoutePath")

        routes.forEach {
            var route = it.getAnnotation(MyRoute::class.java)

            var className = it.asType().toString()


            if (route != null) {
                var path = route.mPath
                var keys = path.split("/")

                keys = keys.filter { !it.isNullOrEmpty() }.map {
                    it.substring(0, 1).toUpperCase() + it.substring(1)
                }
                var key = keys.joinToString(separator = "")
                key = key.substring(0, 1).toLowerCase() + key.substring(1)
                myRoute.addProperty(
                    PropertySpec.builder(key, String::class)
                        .initializer("%S", "$path|$className")
                        .build()
                )

//                MyRouteUtils.path[path] = className
//                println("MyRoute Compiler --------$path : $className ---------------------------------------------------")
            }

        }
        val file =
            FileSpec.builder(
                "com.chenliang.processor." + mModuleName!!.replace("-", ""),
                "MyRoutePath"
            ).addType(myRoute.build()).build()



        try {
            file.writeTo(mFiler)
        } catch (e: Exception) {

        }


//        System.out.println("MyRouteCompiler Processor  Finish-------------------------------------------- ")
        return true
    }

}

