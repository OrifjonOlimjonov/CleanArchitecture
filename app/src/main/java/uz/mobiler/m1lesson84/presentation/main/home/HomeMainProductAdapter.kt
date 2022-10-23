package uz.mobiler.m1lesson84.presentation.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import uz.mobiler.m1lesson84.databinding.ItemBinding
import uz.mobiler.m1lesson84.domain.product.models.ProductData

class HomeMainProductAdapter(var itemClickListener:(ProductData,Int)->Unit):androidx.recyclerview.widget.ListAdapter<ProductData,HomeMainProductAdapter.VH>(MyDiffUtils()) {

    inner class VH(var binding: ItemBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(productData: ProductData,position: Int){
            binding.tvProductName.text = productData.name
            binding.tvProductPrice.text = productData.price.toString()

            itemView.setOnClickListener{
                itemClickListener(productData,position)
            }
        }
    }
    class MyDiffUtils:DiffUtil.ItemCallback<ProductData>(){
        override fun areItemsTheSame(oldItem: ProductData, newItem: ProductData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductData, newItem: ProductData): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            return VH(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(getItem(position), position)
    }
}