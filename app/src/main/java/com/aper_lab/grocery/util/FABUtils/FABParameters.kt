package com.aper_lab.grocery.util.FABUtils

import androidx.annotation.DrawableRes
import com.google.android.material.bottomappbar.BottomAppBar

class FABParameters (p:Int, @DrawableRes ic : Int, t:String?) {

    @BottomAppBar.FabAlignmentMode
    public var position: Int = p;

    @DrawableRes
    public var icon : Int = ic;

    var text : String? = t;
}