package com.chenliang.baselibrary.view

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.chenliang.baselibrary.R
import com.chenliang.baselibrary.utils.changed
import kotlinx.android.synthetic.main.layout_editarea.view.*

/**
 * author:chenliang
 * date:2021/12/15
 */
class MyEditArea : LinearLayout {
    lateinit var edit: EditText

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        var rootview = View.inflate(context!!, R.layout.layout_editarea, null)
        edit = rootview.edit
        edit.clearFocus()
        val typedArray = context?.obtainStyledAttributes(attrs, R.styleable.EditArea)
        var hint = typedArray.getString(R.styleable.EditArea_my_hint)
        var text = typedArray.getString(R.styleable.EditArea_my_text)
        var lenght = typedArray.getInt(R.styleable.EditArea_my_max_length, 0)
        var textsize = typedArray?.getDimension(R.styleable.EditArea_my_textSize, -1f)

        if (hint != null)
            edit.hint = hint
        if (text != null)
            edit.setText(text)
        if (lenght != null) {
            if (lenght > 0) {
                rootview.edit.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(lenght))
                rootview.length.text = lenght.toString()
            }
        }
        rootview.edit.changed {
            rootview.editNum.text = edit.text.length.toString()
        }

        if (textsize != null && textsize > 0) {
            edit.setTextSize(TypedValue.COMPLEX_UNIT_PX,textsize)
        }
        var params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        addView(rootview, params)
    }

    open fun getText(): String {
        return edit.text.toString()
    }

    open fun setText(text: String) {
        edit.setText(text)
    }


}

// SET 方法
@BindingAdapter("my_text")
fun setValue(item: MyEditArea, content: String?) {

    if (item != null && content!=null&&content!="") {
        var oldValue = item.edit.text.toString()
        if (oldValue != content) {
            item.edit.setText(content)
            item.edit.setSelection(content!!.length)
        }

    }
}

// GET 方法
@InverseBindingAdapter(attribute = "my_text", event = "contentAttrChanged")
fun bindEditArea(item: MyEditArea): String {
    return item.edit.text.toString()
}

@BindingAdapter(value = ["contentAttrChanged"])
fun setChangeListenerEditArea(item: MyEditArea, listener: InverseBindingListener) {
    item.edit.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            listener.onChange();
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    })
}
