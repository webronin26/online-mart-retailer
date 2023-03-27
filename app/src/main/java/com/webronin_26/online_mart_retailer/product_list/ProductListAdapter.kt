package com.webronin_26.online_mart_retailer.product_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.webronin_26.online_mart_retailer.Event
import com.webronin_26.online_mart_retailer.data.remote.Response
import com.webronin_26.online_mart_retailer.databinding.ProductListItemBinding
import kotlin.collections.ArrayList

class ProductListAdapter (private val viewModel: ProductListViewModel):
    ListAdapter<Response.Products, ProductListAdapter.ProductListViewHolder>(ProductListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        return ProductListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) { holder.bind(viewModel , item)}
    }

    override fun submitList(list: MutableList<Response.Products>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }

    class ProductListViewHolder private constructor(private val binding: ProductListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: ProductListViewModel , item: Response.Products) {

            binding.viewmodel = viewModel
            binding.products = item

            binding.productListItemLinearLayout.setOnClickListener { viewModel.productId.value = Event(item.id) }

            binding.executePendingBindings()
        }

        companion object {
            fun from (parent: ViewGroup) : ProductListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ProductListItemBinding.inflate(layoutInflater , parent , false)
                return ProductListViewHolder(binding)
            }
        }
    }
}

class ProductListDiffCallback : DiffUtil.ItemCallback<Response.Products>() {

    override fun areContentsTheSame(oldItem: Response.Products, newItem: Response.Products): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(oldItem: Response.Products, newItem: Response.Products): Boolean {
        return oldItem == newItem
    }
}