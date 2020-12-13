package com.aper_lab.grocery.fragment.recipe.recipeTagMenu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aper_lab.grocery.databinding.ListItemTagCheckboxBinding

class RecipeTagAdapter(val listener: RecipeTagEventListener) : RecyclerView.Adapter<RecipeTagAdapter.ViewHolder>() {

    var data: List<Pair<String, Boolean>> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], listener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(val binding: ListItemTagCheckboxBinding,
                    ): RecyclerView.ViewHolder(binding.root){

        fun bind(tag: Pair<String, Boolean>, listener: RecipeTagEventListener){

            binding.checkBox.text = tag.first;
            binding.checkBox.isChecked = tag.second;

            binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                listener.onTagCheckChanged(tag.first,isChecked);
            }
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context);

                var binding = ListItemTagCheckboxBinding.inflate(layoutInflater);

                return ViewHolder(binding);
            }
        }
    }

    interface RecipeTagEventListener{
        fun onTagCheckChanged(tag: String, state:Boolean);
    }

}