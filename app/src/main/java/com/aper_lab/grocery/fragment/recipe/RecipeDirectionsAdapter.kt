package com.aper_lab.grocery.fragment.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aper_lab.grocery.R
import com.aper_lab.grocery.databinding.ListItemDirectionBinding
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
        return ViewHolder.from(
            parent
        );
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], data.size)
    }

    fun setContent(steps:List<RecipeStep>){
        data = steps;
    }

    class ViewHolder(val binding: ListItemDirectionBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(direction: RecipeStep, itemCount:Int){
            binding.step = direction;
            if(direction.num == 1)
                binding.topLine.visibility = View.GONE
            if(direction.num == itemCount)
                binding.bottomLine.visibility = View.GONE

            binding.executePendingBindings();
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context);

                var binding : ListItemDirectionBinding = DataBindingUtil.inflate(layoutInflater,
                    R.layout.list_item_direction, parent,false);

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

}