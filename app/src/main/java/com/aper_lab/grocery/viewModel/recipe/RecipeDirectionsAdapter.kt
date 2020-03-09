package com.aper_lab.grocery.viewModel.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aper_lab.grocery.databinding.DirectionListItemBinding
import com.aper_lab.scraperlib.data.Ingredient
import com.aper_lab.scraperlib.data.RecipeStep

//TODO(Diff Utils)

class RecipeDirectionsAdapter: RecyclerView.Adapter<RecipeDirectionsAdapter.ViewHolder>() {

    var data = listOf<RecipeStep>()
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
        holder.bind(data[position])
    }

    class ViewHolder(val binding: DirectionListItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(direction: RecipeStep){
            binding.step = direction;
            binding.executePendingBindings();
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context);
                val binding = DirectionListItemBinding.inflate(layoutInflater);
                return ViewHolder(binding);
            }
        }
    }

}