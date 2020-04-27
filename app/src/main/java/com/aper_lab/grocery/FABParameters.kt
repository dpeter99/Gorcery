package com.aper_lab.grocery

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import com.google.android.material.bottomappbar.BottomAppBar
import java.text.FieldPosition

class FABParameters (p:Int, @DrawableRes ic : Int) {

    @BottomAppBar.FabAlignmentMode
    public var position: Int = p;

    @DrawableRes
    public var icon : Int = ic;
}