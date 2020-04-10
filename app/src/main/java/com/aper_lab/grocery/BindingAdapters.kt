package com.aper_lab.grocery

import androidx.appcompat.widget.AppCompatImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.aper_lab.scraperlib.RecipeAPIService
import com.aper_lab.scraperlib.data.Recipe

@BindingAdapter("stepNumber")
fun TextView.setSleepDurationFormatted(item: Int?) {
    item?.let {
        text = item.toString();
    }
}

@BindingAdapter("recipeSourceIcon")
fun AppCompatImageView.setrecipeIcon(item: Recipe?) {
    item?.let {
        if(item.link != "") {
            val id = RecipeAPIService.getSourceIDfromURL(item.link);
            this.setImageResource(
                this.context.resources.getIdentifier(
                    "icon_" + id,
                    "drawable",
                    "com.aper_lab.grocery"
                )
            )
        }
    }
}

@BindingAdapter("recipeName")
fun TextView.setSleepImage(item: Recipe?) {
    item?.let {
        text = item.name;
    }
}