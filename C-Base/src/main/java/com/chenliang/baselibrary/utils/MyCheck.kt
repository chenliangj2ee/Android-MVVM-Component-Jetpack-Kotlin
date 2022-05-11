package com.chenliang.baselibrary.utils

/**
 *
 * @Project: MVVM-Component
 * @Package: com.chenliang.baselibrary.utils
 * @author: chenliang
 * @date: 2021/07/15
 */


object MyCheck {
    const val empty = 1;
    const val mobilePhone = 2;
    const val number = 3;
    const val AZ09All = 4;
    const val AZaz = 5;
    const val AZ = 6;
    const val az = 7;
    const val phone = 8;
    const val id = 9;

    fun LENGTH(min: Int, max: Int): ChenkLength {
        return ChenkLength(min, max)
    }

}

class ChenkLength(min: Int, max: Int) {
    var min = min
    var max = max
}