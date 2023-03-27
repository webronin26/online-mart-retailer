package com.webronin_26.online_mart_retailer

import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:set_edit_text_float")
fun setEditTextFloat(editText: AppCompatEditText?, float: Float?) {
    if (editText != null && float != 0.0f) editText.setText(float.toString())
}

@BindingAdapter("app:set_edit_text_int")
fun setEditTextInt(editText: AppCompatEditText?, int: Int?) {
    if (editText != null && int != 0) editText.setText(int.toString())
}

@BindingAdapter("app:set_text_view_float")
fun setTextViewFloat(textView: AppCompatTextView?, float: Float?) {
    if (textView != null && float != 0.0f) textView.text = float.toString()
}

@BindingAdapter("app:set_text_view_int")
fun setTextViewInt(textView: AppCompatTextView?, int: Int?) {
    if (textView != null && int != 0) textView.text = int.toString()
}