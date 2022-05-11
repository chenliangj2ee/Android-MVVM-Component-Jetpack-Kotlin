package com.chenliang.baselibrary.utils

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import java.security.MessageDigest

/**
 * author:chenliang
 * date:2022/3/21
 */
class GlideBlurTransformation internal constructor(private val context: Context) : CenterCrop() {
    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val bitmap = super.transform(pool, toTransform, outWidth, outHeight)
        return blurBitmap(context, bitmap, 4F, (outWidth * 0.2).toInt(), (outHeight * 0.2).toInt())
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {}

    /**
     * 模糊Bitmap处理
     *
     * @param context
     * @param source
     * @param blurRadius
     * @param outWidth
     * @param outHeight
     * @return
     */
    fun blurBitmap(
        context: Context,
        source: Bitmap,
        blurRadius: Float,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            var inputBitmap = Bitmap.createScaledBitmap(source, outWidth, outHeight, false);
            var outputBitmap = Bitmap.createBitmap(inputBitmap);
            var rs = RenderScript.create(context);
            var blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            var tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
            var tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
            blurScript.setRadius(blurRadius);
            blurScript.setInput(tmpIn);
            blurScript.forEach(tmpOut);
            tmpOut.copyTo(outputBitmap);
            return outputBitmap;
        } else {
            return source;
        }
    }
}