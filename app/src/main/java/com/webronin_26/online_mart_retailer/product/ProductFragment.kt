package com.webronin_26.online_mart_retailer.product

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
import com.webronin_26.online_mart_retailer.databinding.ProductFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFragment : Fragment() {

    private lateinit var viewModel: ProductViewModel
    private lateinit var viewDataBinding: ProductFragmentBinding

    private var productId: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.product_fragment, container, false)

        viewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        lifecycle.addObserver(viewModel)
        viewDataBinding = ProductFragmentBinding.bind(view).apply { viewmodel = viewModel }
        viewDataBinding.lifecycleOwner = this

        productId = arguments?.getInt("product_id") ?: 0

        return view
    }

    override fun onResume() {
        super.onResume()

        initView()
        refreshRequest()
    }

    private fun initView() {

        viewDataBinding.productFragmentCancelButton.setOnClickListener {
            findNavController().navigate(R.id.action_product_fragment_to_product_list_fragment)
        }

        viewDataBinding.productFragmentSaveButton.setOnClickListener {

            if (productId == 0) {
                Toast.makeText(requireContext(), "目前沒有詳細資料", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (viewModel.productEditTextName.get().isNullOrEmpty()) {
                Toast.makeText(context, "產品名稱不得為空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (viewModel.productEditTextPrice.get().toString().isEmpty()) {
                Toast.makeText(context, "產品名稱不得為空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                viewModel.productEditTextPrice.get().toString().toFloat()
            } catch (e: Exception) {
                Toast.makeText(context, "產品價錢請輸入 數字 類型", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (viewModel.productEditTextNumber.get().toString().isEmpty()) {
                Toast.makeText(context, "產品名稱不得為空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                viewModel.productEditTextNumber.get().toString().toInt()
            } catch (e: Exception) {
                Toast.makeText(context, "產品庫存請輸入 數字 類型", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (viewModel.productEditTextMaxBuy.get().toString().isEmpty()) {
                Toast.makeText(context, "產品名稱不得為空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                viewModel.productEditTextMaxBuy.get().toString().toInt()
            } catch (e: Exception) {
                Toast.makeText(context, "產品最大購買量請輸入 數字 類型", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val token = TokenManager.getToken(requireContext())

            if (!token.isNullOrEmpty()) {
                viewModel.updateProduct(token, productId)
            } else {
                Toast.makeText(requireContext(), "尚未能更新", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.viewModelRefreshInternetStatus.observe(this, EventObserver {
            when(it) {
                VIEW_MODEL_INTERNET_SUCCESS ->
                    Toast.makeText(requireContext(), "更新成功", Toast.LENGTH_LONG).show()
                VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION ->
                    Toast.makeText(requireContext(), "網路連線異常，請確認網路狀態", Toast.LENGTH_LONG).show()
                VIEW_MODEL_INTERNET_ERROR ->
                    Toast.makeText(requireContext(), "網路錯誤，請稍等", Toast.LENGTH_LONG).show()
            }
        })

        viewModel.viewModelUpdateInternetStatus.observe(this, EventObserver {
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

        if (!token.isNullOrEmpty() && productId != 0) {
            viewModel.refreshProduct(token, productId)
        } else {
            Toast.makeText(requireContext(), "目前沒有詳細資料", Toast.LENGTH_LONG).show()
        }
    }
}