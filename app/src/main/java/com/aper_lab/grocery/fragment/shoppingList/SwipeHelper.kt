package com.aper_lab.grocery.fragment.shoppingList

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.aper_lab.grocery.R

class SwipeHelper<T>(val viewModel: SwipeableList<T>, val mRecyclerView: RecyclerView, val context: Context) : ItemTouchHelper.SimpleCallback(
    0, ItemTouchHelper.LEFT
) {

    var background: Drawable = ColorDrawable(Color.RED)
    var xMark: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_delete_24dp)
    var xMarkMargin = 16

    init {
        //xMark?.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
    }

    override fun getSwipeDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {

        return super.getSwipeDirs(recyclerView, viewHolder)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
        val vh : T? = (viewHolder as ItemProvider<T>).getBoundItem();
        vh?.let {
            viewModel.remove(vh);
        }

    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView: View = viewHolder.itemView

        // not sure why, but this method get's called for viewholder that are already swiped away
        if (viewHolder.getAdapterPosition() == -1) {
            // not interested in those
            return;
        }
        if(dX ==0f && dY ==0f){
            return;
        }

        // draw red background
        background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
        background.draw(c)

        // draw x mark
        // draw x mark
        val itemHeight = itemView.bottom - itemView.top
        val intrinsicWidth = xMark!!.intrinsicWidth
        val intrinsicHeight = xMark!!.intrinsicWidth

        val xMarkLeft = itemView.right - xMarkMargin - intrinsicWidth
        val xMarkRight = itemView.right - xMarkMargin
        val xMarkTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val xMarkBottom = xMarkTop + intrinsicHeight
        xMark!!.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom)

        xMark!!.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }




    interface ItemProvider<T>{
        fun getBoundItem():T?;
    }

    interface SwipeableList<T>{
        fun remove(item: T);
    }

}