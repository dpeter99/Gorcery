package com.aper_lab.grocery.fragment.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aper_lab.grocery.R
import com.aper_lab.grocery.databinding.ListItemIngredientBinding
import com.aper_lab.scraperlib.data.Ingredient

//TODO(Diff Utils)

class RecipeIngredientAdapter(val listener:IngredientClickListener): RecyclerView.Adapter<RecipeIngredientAdapter.ViewHolder>() {

    var data = listOf<Ingredient>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return data.size;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder.from(
            parent
        );
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], listener)
    }

    class ViewHolder(val binding: ListItemIngredientBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(ingredient: Ingredient, listener: IngredientClickListener){
            binding.ingredient = ingredient;
            binding.listener = listener;
/*
            binding.root.setOnClickListener {
                listener.ingredientClicked(ingredient);
            }
 */
            binding.executePendingBindings();
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context);

                var binding : ListItemIngredientBinding = DataBindingUtil.inflate(layoutInflater,R.layout.list_item_ingredient, parent,false);

                return ViewHolder(
                    binding
                );
            }
        }
    }


    interface IngredientClickListener{
        fun onIngredientClicked(ingredient: Ingredient);
    }
}