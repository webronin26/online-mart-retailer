package com.webronin_26.online_mart_retailer.order_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.webronin_26.online_mart_retailer.Event
import com.webronin_26.online_mart_retailer.data.remote.Response
import com.webronin_26.online_mart_retailer.databinding.OrderListItemBinding

class OrderListAdapter (private val viewModel: OrderListViewModel):
    ListAdapter<Response.Orders, OrderListAdapter.OrderListViewHolder>(OrderListDiffCallback()) {

    override fun submitList(list: MutableList<Response.Orders>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListViewHolder {
        return OrderListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) { holder.bind(viewModel , item)}
    }

    class OrderListViewHolder private constructor(private  val binding: OrderListItemBinding ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: OrderListViewModel , item: Response.Orders) {

            binding.viewmodel = viewModel
            binding.orders = item

            binding.orderListItemLinearLayout.setOnClickListener { viewModel.orderId.value = Event(item.id) }

            binding.executePendingBindings()
        }

        companion object {
            fun from (parent: ViewGroup) : OrderListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = OrderListItemBinding.inflate(layoutInflater , parent , false)
                return OrderListViewHolder(binding)
            }
        }
    }
}

class OrderListDiffCallback : DiffUtil.ItemCallback<Response.Orders>() {

    override fun areContentsTheSame(oldItem: Response.Orders, newItem: Response.Orders): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(oldItem: Response.Orders, newItem: Response.Orders): Boolean {
        return oldItem == newItem
    }
}