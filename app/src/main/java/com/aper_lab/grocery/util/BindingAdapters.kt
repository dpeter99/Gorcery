package com.aper_lab.grocery.util

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.aper_lab.grocery.R
import com.bumptech.glide.Glide
import org.threeten.bp.Duration

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()

        Glide.with(imgView.context)
            .load(imgUri)
            .into(imgView)
    }
}

@BindingAdapter("time")
fun bindImage(textView: TextView, time: Long?) {
    time?.let {
        val text: String = Duration.ofSeconds(time).toMinutes().toString() + textView.context.resources.getString(R.string.minute_short)
        textView.text = text;
    }
}