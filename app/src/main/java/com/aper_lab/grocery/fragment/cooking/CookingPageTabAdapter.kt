package com.aper_lab.grocery.fragment.cooking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aper_lab.grocery.databinding.CookingTabBinding
import com.aper_lab.grocery.databinding.FragmentCookingRecipePageBinding
import com.aper_lab.grocery.fragment.recipeList.RecipeListAdapter
import com.aper_lab.scraperlib.data.Recipe
import com.aper_lab.scraperlib.data.RecipeStep

class CookingPageTabAdapter(val clickListener: TabListener) :  RecyclerView.Adapter<CookingPageTabAdapter.ViewHolder>() {

    var data = listOf<RecipeStep>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int{
        return data.size;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        );
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], data.size, clickListener)
    }



    class ViewHolder(val binding: CookingTabBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(direction: RecipeStep, itemCount:Int, clickListener: TabListener){
            binding.num = direction.num;
            binding.clickHandler = clickListener;
            binding.executePendingBindings();
        }

        companion object{

            fun from(parent: ViewGroup): ViewHolder {

                val layoutInflater = LayoutInflater.from(parent.context);

                var binding : CookingTabBinding = CookingTabBinding.inflate(layoutInflater, parent,false);

                //val binding = ListItemDirectionBinding.inflate(layoutInflater);
                //binding.root.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //val layout = binding.root as ConstraintLayout;
                //layout.requestLayout();
                return ViewHolder(
                    binding
                );
            }
        }
    }

    open class TabListener(val clickListener: (name: Int) -> Unit) {
        fun onClick(recipe: Int) = clickListener(recipe)
    }
}