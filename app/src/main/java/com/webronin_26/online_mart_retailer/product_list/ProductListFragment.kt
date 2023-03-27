package com.webronin_26.online_mart_retailer.product_list

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
import com.webronin_26.online_mart_retailer.databinding.ProductListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private lateinit var viewModel: ProductListViewModel
    private lateinit var viewDataBinding: ProductListFragmentBinding

    private var productListAdapter: ProductListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.product_list_fragment, container, false)

        viewModel = ViewModelProvider(this)[ProductListViewModel::class.java]
        lifecycle.addObserver(viewModel)
        viewDataBinding = ProductListFragmentBinding.bind(view).apply { viewmodel = viewModel }
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

        viewModel.products.observe(this, EventObserver {
            (viewDataBinding.productListRecyclerView.adapter as ProductListAdapter).submitList(it.toMutableList())
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

        viewModel.productId.observe(this, EventObserver {
            val bundle = Bundle()
            bundle.putInt("product_id", it)
            findNavController().navigate(R.id.action_product_list_fragment_to_product_fragment, bundle)
        })
    }

    private fun initAdapter() {

        val viewModel = viewDataBinding.viewmodel

        if (viewModel != null) {
            productListAdapter = ProductListAdapter(viewModel)
            viewDataBinding.productListRecyclerView.adapter = productListAdapter
            (viewDataBinding.productListRecyclerView.adapter as ProductListAdapter).submitList(null)
        }
    }

    private fun refreshRequest() {
        if (!TokenManager.getToken(requireContext()).isNullOrEmpty()) {
            viewModel.refreshList(TokenManager.getToken(requireContext())!!)
        } else {
            Toast.makeText(requireContext(), "目前沒有資料", Toast.LENGTH_LONG).show()
        }
    }
}