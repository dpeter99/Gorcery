package com.aper_lab.grocery.viewModel.recipeList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aper_lab.grocery.databinding.ListItemRecipeBinding
import com.aper_lab.scraperlib.RecipeAPIService
import com.aper_lab.scraperlib.data.Recipe

class RecipeListAdapter (val clickListener: RecipeListener): RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {

    var data = mutableMapOf<String,Recipe>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return data.size;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data.values.elementAt(position), data.size, clickListener)
    }

    class ViewHolder(val binding: ListItemRecipeBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(
            recipe: Recipe,
            itemCount: Int,
            clickListener: RecipeListener
        ){
            binding.recipe = recipe;
            binding.clickListener = clickListener;

            val id = RecipeAPIService.getSourceIDfromURL(recipe.link);
            binding.icon.setImageResource(binding.root.context.resources.getIdentifier("icon_"+id,"drawable","com.aper_lab.grocery"))

            binding.executePendingBindings();
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context);
                var binding = ListItemRecipeBinding.inflate(layoutInflater);
                binding.root.layoutParams =ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                return ViewHolder(binding);
            }
        }

    }

    class RecipeListener(val clickListener: (name: String) -> Unit) {
        fun onClick(recipe: Recipe) = clickListener(recipe.id)
    }

}