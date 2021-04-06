package com.example.binge.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    Glide.with(view.context).load(url).into(view)
}

@BindingAdapter("textFromDouble")
fun setTextFromDouble(textView: TextView, value: Double) {
    textView.text = value.toString()
}

@BindingAdapter("textFromList")
fun setTextFromList(textView: TextView, list: List<String>) {
    textView.text = list.joinToString(", ")
}

@BindingAdapter("textFromLong")
fun setTextFromLong(textView: TextView, value: Long) {
    textView.text = value.toString()
}