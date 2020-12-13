package com.aper_lab.grocery.fragment.shoppingList

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.aper_lab.grocery.R
import com.aper_lab.grocery.databinding.ListItemProductBinding
import com.aper_lab.grocery.model.ShoppingItem
import com.aper_lab.grocery.model.ShoppingList


class ShoppingListAdapter(val clickListener: ShoppingListListener, val checked: Boolean) : RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {

    var data: ShoppingList? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data?.items?.filter { it -> it.checked == checked }?.get(position);
        holder.bind(item!!, position, clickListener)
    }

    override fun getItemCount(): Int {
        return data?.items?.count { item -> item.checked == checked } ?: 0
    }

    class ViewHolder(val binding: ListItemProductBinding) : RecyclerView.ViewHolder(binding.root), SwipeHelper.ItemProvider<ShoppingItem> {

        var data: ShoppingItem?  = null;

        fun bind(item: ShoppingItem, itemCount: Int, clickListener: ShoppingListListener) {
            data = item;
            binding.product = item;
            //binding.ingredientAmount.text = itemCount.toString();
            binding.clickListener = clickListener;

            if(item.checked) {
                binding.strikethrough.visibility = View.VISIBLE;
                binding.ingredientName.setTypeface(null, Typeface.ITALIC);

            }
            else{
                binding.strikethrough.visibility = View.INVISIBLE;
                binding.ingredientName.setTypeface(null, Typeface.NORMAL);
            }


            binding.executePendingBindings();
        }

        override fun getBoundItem(): ShoppingItem? {
            return data;
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context);

                var binding = ListItemProductBinding.inflate(layoutInflater);

                binding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                );

                return ViewHolder(binding);
            }
        }



    }

    interface ShoppingListListener {
        fun onShoppingItemClick(recipe: ShoppingItem);
    }
}
