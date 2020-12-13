package com.aper_lab.grocery.fragment.tags

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aper_lab.grocery.databinding.ListItemTagBinding
import com.aper_lab.grocery.model.RecipeTag

class UserTagsAdapter(val listener: TagEventListener) : RecyclerView.Adapter<UserTagsAdapter.ViewHolder>() {

    var data: List<RecipeTag> = listOf()
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

    class ViewHolder(val binding: ListItemTagBinding,
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(tag: RecipeTag, listener: TagEventListener){
            binding.name.text = tag.name;
            binding.count.text = tag.recipes.size.toString();

            binding.root.setOnClickListener {
                listener.ontagClicked(tag);
            }
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context);

                var binding = ListItemTagBinding.inflate(layoutInflater);

                return ViewHolder(binding);
            }
        }
    }

    interface TagEventListener{
        fun ontagClicked(tag: RecipeTag);
    }

}