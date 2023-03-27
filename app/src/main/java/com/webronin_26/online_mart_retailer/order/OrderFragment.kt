package com.webronin_26.online_mart_retailer.order

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
import com.webronin_26.online_mart_retailer.databinding.OrderFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : Fragment() {

    private lateinit var viewModel: OrderViewModel
    private lateinit var viewDataBinding: OrderFragmentBinding

    private var orderId: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.order_fragment, container, false)

        viewModel = ViewModelProvider(this)[OrderViewModel::class.java]
        lifecycle.addObserver(viewModel)
        viewDataBinding = OrderFragmentBinding.bind(view).apply { viewmodel = viewModel }
        viewDataBinding.lifecycleOwner = this

        orderId = arguments?.getInt("order_id") ?: 0

        return view
    }

    override fun onResume() {
        super.onResume()

        initView()
        refreshRequest()
    }

    private fun initView() {

        viewDataBinding.orderFragmentCancelButton.setOnClickListener {
            findNavController().navigate(R.id.action_order_fragment_to_order_list_fragment)
        }

        viewModel.viewModelInternetStatus.observe(this, EventObserver {
            when(it) {
                VIEW_MODEL_INTERNET_SUCCESS ->
                    Toast.makeText(requireContext(), "更新成功", Toast.LENGTH_LONG).show()
                VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION ->
                    Toast.makeText(requireContext(), "網路連線異常，請確認網路狀態", Toast.LENGTH_LONG).show()
                VIEW_MODEL_INTERNET_ERROR ->
                    Toast.makeText(requireContext(), "網路錯誤，請稍等", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun refreshRequest() {

        val token = TokenManager.getToken(requireContext())

        if (!token.isNullOrEmpty() && orderId != 0) {
            viewModel.refreshOrder(token, orderId)
        } else {
            Toast.makeText(requireContext(), "目前沒有詳細資料", Toast.LENGTH_LONG).show()
        }
    }
}