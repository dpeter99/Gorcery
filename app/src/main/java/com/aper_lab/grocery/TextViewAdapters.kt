package com.aper_lab.grocery

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("stepNumber")
fun TextView.setSleepDurationFormatted(item: Int?) {
    item?.let {
        text = item.toString();
    }
}