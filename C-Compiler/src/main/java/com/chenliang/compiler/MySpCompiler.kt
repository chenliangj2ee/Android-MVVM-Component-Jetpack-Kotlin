package com.chenliang.compiler

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
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
@SupportedAnnotationTypes(value = ["com.chenliang.baselibrary.annotation.MySpConfig"])
class MySpCompiler : AbstractProcessor() {
    private lateinit var mFiler: Filer
    private var mModuleName: String? = null

    init {
//        System.out.println("MySpCompiler Processor Init--------------------------------------------")
    }

    override fun init(p0: ProcessingEnvironment?) {
        super.init(p0)
        mFiler = p0!!.filer
        mModuleName = p0.options["AROUTER_MODULE_NAME"]
//        System.out.println("MySpCompiler Processor  mModuleName-------------------------------------------- $mModuleName")
    }

    override fun process(ms: MutableSet<out TypeElement>?, en: RoundEnvironment?): Boolean {

        if (en == null) {
//            return false
        }

        var mySp = TypeSpec.objectBuilder("MySp")
        ms!!.forEach {

            var annos = en!!.getElementsAnnotatedWith(it)
            annos.forEach {

//                println("asType:${it.asType()}----------------------------------------------------------------------------")
                var fieldName = it.simpleName.toString()
                when {
                    it.asType().toString() == "boolean" -> {
                        fieldName = fieldName.replace("is", "")
                        var funName =
                            "set" + (fieldName.substring(0, 1).toUpperCase()) + fieldName.substring(
                                1
                            )

                        var setFun = FunSpec
                            .builder(funName)
                            .addParameter(fieldName.toLowerCase(), Boolean::class)
                            .addStatement(
                                "%T.putBoolean(%S, ${fieldName.toLowerCase()})",
                                ClassName("com.chenliang.baselibrary.utils", "MySpUtis"),
                                it.simpleName.toString()
                            )

                        mySp.addFunction(setFun.build())

                        var getFunctionName =
                            "is" + (fieldName.substring(0, 1).toUpperCase()) + fieldName.substring(
                                1
                            )

                        var getFun = FunSpec
                            .builder(getFunctionName)
                            .returns(Boolean::class)
                            .addStatement(
                                "return %T.getBoolean(%S)",
                                ClassName("com.chenliang.baselibrary.utils", "MySpUtis"),
                                it.simpleName.toString()
                            )

                        mySp.addFunction(getFun.build())


                    }
                    it.asType().toString() == "java.lang.String" -> {
                        fieldName = fieldName.replace("is", "")
                        var funName =
                            "set" + (fieldName.substring(0, 1).toUpperCase()) + fieldName.substring(
                                1
                            )

                        var setFun = FunSpec
                            .builder(funName)
                            .addParameter(fieldName.toLowerCase(), String::class)
                            .addStatement(
                                "%T.putString(%S, ${fieldName.toLowerCase()})",
                                ClassName("com.chenliang.baselibrary.utils", "MySpUtis"),
                                it.simpleName.toString()
                            )

                        mySp.addFunction(setFun.build())

                        var getFunctionName =
                            "get" + (fieldName.substring(0, 1).toUpperCase()) + fieldName.substring(
                                1
                            )

                        var getFun = FunSpec
                            .builder(getFunctionName)
                            .returns(String::class)
                            .addStatement(
                                "return %T.getString(%S)",
                                ClassName("com.chenliang.baselibrary.utils", "MySpUtis"),
                                it.simpleName.toString()
                            )

                        mySp.addFunction(getFun.build())

                    }
                    it.asType().toString() == "int" -> {
                        fieldName = fieldName.replace("is", "")
                        var funName =
                            "set" + (fieldName.substring(0, 1).toUpperCase()) + fieldName.substring(
                                1
                            )

                        var setFun = FunSpec
                            .builder(funName)
                            .addParameter(fieldName.toLowerCase(), Int::class)
                            .addStatement(
                                "%T.putInt(%S, ${fieldName.toLowerCase()})",
                                ClassName("com.chenliang.baselibrary.utils", "MySpUtis"),
                                it.simpleName.toString()
                            )

                        mySp.addFunction(setFun.build())

                        var getFunctionName =
                            "get" + (fieldName.substring(0, 1).toUpperCase()) + fieldName.substring(
                                1
                            )

                        var getFun = FunSpec
                            .builder(getFunctionName)
                            .returns(Int::class)
                            .addStatement(
                                "return %T.getInt(%S)",
                                ClassName("com.chenliang.baselibrary.utils", "MySpUtis"),
                                it.simpleName.toString()
                            )

                        mySp.addFunction(getFun.build())
                    }
                    it.asType().toString() == "long" -> {
                        fieldName = fieldName.replace("is", "")
                        var funName =
                            "set" + (fieldName.substring(0, 1).toUpperCase()) + fieldName.substring(
                                1
                            )

                        var setFun = FunSpec
                            .builder(funName)
                            .addParameter(fieldName.toLowerCase(), Long::class)
                            .addStatement(
                                "%T.putLong(%S, ${fieldName.toLowerCase()})",
                                ClassName("com.chenliang.baselibrary.utils", "MySpUtis"),
                                it.simpleName.toString()
                            )

                        mySp.addFunction(setFun.build())

                        var getFunctionName =
                            "get" + (fieldName.substring(0, 1).toUpperCase()) + fieldName.substring(
                                1
                            )

                        var getFun = FunSpec
                            .builder(getFunctionName)
                            .returns(Long::class)
                            .addStatement(
                                "return %T.getLong(%S)",
                                ClassName("com.chenliang.baselibrary.utils", "MySpUtis"),
                                it.simpleName.toString()
                            )

                        mySp.addFunction(getFun.build())
                    }
                    it.asType().toString() == "float" -> {
                        fieldName = fieldName.replace("is", "")
                        var funName =
                            "set" + (fieldName.substring(0, 1).toUpperCase()) + fieldName.substring(
                                1
                            )

                        var setFun = FunSpec
                            .builder(funName)
                            .addParameter(fieldName.toLowerCase(), Float::class)
                            .addStatement(
                                "%T.putFloat(%S, ${fieldName.toLowerCase()})",
                                ClassName("com.chenliang.baselibrary.utils", "MySpUtis"),
                                it.simpleName.toString()
                            )

                        mySp.addFunction(setFun.build())

                        var getFunctionName =
                            "get" + (fieldName.substring(0, 1).toUpperCase()) + fieldName.substring(
                                1
                            )

                        var getFun = FunSpec
                            .builder(getFunctionName)
                            .returns(Float::class)
                            .addStatement(
                                "return %T.getFloat(%S)",
                                ClassName("com.chenliang.baselibrary.utils", "MySpUtis"),
                                it.simpleName.toString()
                            )

                        mySp.addFunction(getFun.build())
                    }
                    it.asType().toString() == "double" -> {
                        fieldName = fieldName.replace("is", "")
                        var funName =
                            "set" + (fieldName.substring(0, 1).toUpperCase()) + fieldName.substring(
                                1
                            )

                        var setFun = FunSpec
                            .builder(funName)
                            .addParameter(fieldName.toLowerCase(), Double::class)
                            .addStatement(
                                "%T.putDouble(%S, ${fieldName.toLowerCase()})",
                                ClassName("com.chenliang.baselibrary.utils", "MySpUtis"),
                                it.simpleName.toString()
                            )

                        mySp.addFunction(setFun.build())

                        var getFunctionName =
                            "get" + (fieldName.substring(0, 1).toUpperCase()) + fieldName.substring(
                                1
                            )

                        var getFun = FunSpec
                            .builder(getFunctionName)
                            .returns(Double::class)
                            .addStatement(
                                "return %T.getDouble(%S)",
                                ClassName("com.chenliang.baselibrary.utils", "MySpUtis"),
                                it.simpleName.toString()
                            )

                        mySp.addFunction(getFun.build())
                    }
                    it.asType().toString() == "object" -> {

                    }
                }
            }


        }
        val file =
            FileSpec.builder("com.chenliang.processor." + mModuleName!!.replace("-", ""), "MySp")
                .addType(mySp.build()).build()

        try {
            file.writeTo(mFiler)
        } catch (e: Exception) {

        }


        return true
    }

}

