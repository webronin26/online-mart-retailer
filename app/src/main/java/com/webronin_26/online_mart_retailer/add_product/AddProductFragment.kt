package com.webronin_26.online_mart_retailer.add_product

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
import com.webronin_26.online_mart_retailer.databinding.AddProductFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddProductFragment : Fragment() {

    private lateinit var viewModel: AddProductViewModel
    private lateinit var viewDataBinding: AddProductFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.add_product_fragment, container, false)

        viewModel = ViewModelProvider(this)[AddProductViewModel::class.java]
        lifecycle.addObserver(viewModel)
        viewDataBinding = AddProductFragmentBinding.bind(view).apply { viewmodel = viewModel }
        viewDataBinding.lifecycleOwner = this

        return view
    }

    override fun onResume() {
        super.onResume()

        initView()
    }

    private fun initView() {

        viewDataBinding.addProductFragmentCancelButton.setOnClickListener {
            findNavController().navigate(R.id.action_add_product_fragment_to_main_fragment)
        }

        viewDataBinding.addProductFragmentSaveButton.setOnClickListener {

            if (viewModel.addProductEditTextName.get().isNullOrEmpty()) {
                Toast.makeText(context, "產品名稱不得為空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (viewModel.addProductEditTextPrice.get().toString().isEmpty()) {
                Toast.makeText(context, "產品價錢不得為空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                viewModel.addProductEditTextPrice.get().toString().toFloat()
            } catch (e: Exception) {
                Toast.makeText(context, "產品價錢請輸入 數字 類型", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (viewModel.addProductEditTextNumber.get().toString().isEmpty()) {
                Toast.makeText(context, "產品價錢不得為空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                viewModel.addProductEditTextNumber.get().toString().toInt()
            } catch (e: Exception) {
                Toast.makeText(context, "產品庫存請輸入 數字 類型", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (viewModel.addProductEditTextMaxBuy.get().toString().isEmpty()) {
                Toast.makeText(context, "產品價錢不得為空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                viewModel.addProductEditTextMaxBuy.get().toString().toInt()
            } catch (e: Exception) {
                Toast.makeText(context, "產品最大購買量請輸入 數字 類型", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val token = TokenManager.getToken(requireContext())

            if (!token.isNullOrEmpty()) {
                viewModel.addProduct(token)
            } else {
                Toast.makeText(requireContext(), "尚未能更新", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.viewModelInternetStatus.observe(this, EventObserver {
            when(it) {
                VIEW_MODEL_INTERNET_SUCCESS ->
                    Toast.makeText(requireContext(), "增加商品成功", Toast.LENGTH_LONG).show()
                VIEW_MODEL_INTERNET_ERROR_PRODUCT_NAME ->
                    Toast.makeText(requireContext(), "商品名稱跟現有商品名有重複", Toast.LENGTH_LONG).show()
                VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION ->
                    Toast.makeText(requireContext(), "網路連線異常，請確認網路狀態", Toast.LENGTH_LONG).show()
                VIEW_MODEL_INTERNET_ERROR ->
                    Toast.makeText(requireContext(), "網路錯誤，請稍等", Toast.LENGTH_LONG).show()
            }
        })
    }
}