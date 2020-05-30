package com.aper_lab.grocery.fragment.cooking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aper_lab.grocery.databinding.FragmentCookingRecipePageBinding
import com.aper_lab.scraperlib.data.RecipeStep

class CookingPageAdapter() :  RecyclerView.Adapter<CookingPageAdapter.ViewHolder>() {

    var data = listOf<RecipeStep>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int{
        return data.size;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CookingPageAdapter.ViewHolder {
        return CookingPageAdapter.ViewHolder.from(
            parent
        );
    }

    override fun onBindViewHolder(holder: CookingPageAdapter.ViewHolder, position: Int) {
        holder.bind(data[position], data.size)
    }



    class ViewHolder(val binding: FragmentCookingRecipePageBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(direction: RecipeStep, itemCount:Int){
            binding.direction = direction;
            binding.executePendingBindings();
        }

        companion object{

            fun from(parent: ViewGroup): CookingPageAdapter.ViewHolder {

                val layoutInflater = LayoutInflater.from(parent.context);

                var binding : FragmentCookingRecipePageBinding = FragmentCookingRecipePageBinding.inflate(layoutInflater, parent,false);

                //val binding = ListItemDirectionBinding.inflate(layoutInflater);
                //binding.root.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //val layout = binding.root as ConstraintLayout;
                //layout.requestLayout();
                return CookingPageAdapter.ViewHolder(
                    binding
                );
            }
        }
    }
}