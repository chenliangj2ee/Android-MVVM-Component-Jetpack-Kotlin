package com.chenliang.baselibrary.act

import android.content.Context
import androidx.fragment.app.Fragment
import com.chenliang.baselibrary.databinding.BaseActivityImagesBinding
import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.fragment.ImageFragment
import com.chenliang.baselibrary.utils.goto
import com.chenliang.baselibrary.annotation.MyClass
import com.chenliang.baselibrary.annotation.MyField

/**
 * author:chenliang
 * date:2021/12/22
 */
@MyClass(mFullScreen = true)
class ImagesActivity : MyBaseActivity<BaseActivityImagesBinding, DefaultViewModel>() {
    @MyField
    lateinit var images: ArrayList<String>

    @MyField
    var position: Int = 0

    override fun initCreate() {
        fullscreenTransparentBar(true)
        var fs = ArrayList<Fragment>()
        images.forEach { fs.add(ImageFragment(it)) }
        mBinding.viewpager.addFragments(fs)
        mBinding.viewpager.currentItem = position

    }
}

fun Context.gotoPhoto(images: List<String>, position: Int) {
    goto(ImagesActivity::class.java, "images", images, "position", position)
}