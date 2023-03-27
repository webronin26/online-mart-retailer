package com.webronin_26.online_mart_retailer.order_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.webronin_26.online_mart_retailer.*
import com.webronin_26.online_mart_retailer.data.source.TokenManager
import com.webronin_26.online_mart_retailer.databinding.OrderListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderListFragment : Fragment() {

    private lateinit var viewModel: OrderListViewModel
    private lateinit var viewDataBinding: OrderListFragmentBinding

    private var orderListAdapter: OrderListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.order_list_fragment, container, false)

        viewModel = ViewModelProvider(this)[OrderListViewModel::class.java]
        lifecycle.addObserver(viewModel)
        viewDataBinding = OrderListFragmentBinding.bind(view).apply { viewmodel = viewModel }
        viewDataBinding.lifecycleOwner = this

        return view
    }

    override fun onResume() {
        super.onResume()

        initView()
        initAdapter()
        refreshRequest()
    }

    private fun initView() {

        viewModel.orders.observe(this, EventObserver {
            (viewDataBinding.orderListRecyclerView.adapter as OrderListAdapter).submitList(it.toMutableList())
        })

        viewModel.viewModelInternetStatus.observe(this, EventObserver {
            when(it) {
                VIEW_MODEL_INTERNET_SUCCESS ->
                    Toast.makeText(requireContext(), "更新成功", Toast.LENGTH_LONG).show()
                VIEW_MODEL_INTERNET_SUCCESS_BUT_EMPTY ->
                    Toast.makeText(requireContext(), "目前沒有任何訂單", Toast.LENGTH_LONG).show()
                VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION ->
                    Toast.makeText(requireContext(), "網路連線異常，請確認網路狀態", Toast.LENGTH_LONG).show()
                VIEW_MODEL_INTERNET_ERROR ->
                    Toast.makeText(requireContext(), "網路錯誤，請稍等", Toast.LENGTH_LONG).show()
            }
        })

        viewModel.orderId.observe(this, EventObserver {
            val bundle = Bundle()
            bundle.putInt("order_id", it)
            findNavController().navigate(R.id.action_order_list_fragment_to_order_fragment, bundle)
        })
    }

    private fun initAdapter() {

        val viewModel = viewDataBinding.viewmodel

        if (viewModel != null) {
            orderListAdapter = OrderListAdapter(viewModel)
            viewDataBinding.orderListRecyclerView.adapter = orderListAdapter
            (viewDataBinding.orderListRecyclerView.adapter as OrderListAdapter).submitList(null)
        }
    }

    private fun refreshRequest() {

        val token = TokenManager.getToken(requireContext())

        if (!token.isNullOrEmpty()) {
            viewModel.refreshList(token)
        } else {
            Toast.makeText(requireContext(), "目前沒有詳細資料", Toast.LENGTH_LONG).show()
        }
    }
}