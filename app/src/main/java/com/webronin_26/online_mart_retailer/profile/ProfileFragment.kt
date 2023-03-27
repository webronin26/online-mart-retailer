package com.webronin_26.online_mart_retailer.profile

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
import com.webronin_26.online_mart_retailer.data.source.UserInformationManager
import com.webronin_26.online_mart_retailer.databinding.ProfileFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var viewDataBinding: ProfileFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.profile_fragment, container, false)

        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        lifecycle.addObserver(viewModel)
        viewDataBinding = ProfileFragmentBinding.bind(view).apply { viewmodel = viewModel }
        viewDataBinding.lifecycleOwner = this

        return view
    }

    override fun onResume() {
        super.onResume()

        initView()
        refreshRequest()
    }

    private fun initView() {

        viewDataBinding.profileFragmentCancelButton.setOnClickListener {
            findNavController().navigate(R.id.action_profile_fragment_to_main_fragment)
        }

        viewDataBinding.profileFragmentSaveButton.setOnClickListener {

            if (viewModel.profileName.get().isNullOrEmpty()) {
                Toast.makeText(context, "公司名稱不得為空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (viewModel.profileResponsiblePerson.get().isNullOrEmpty()) {
                Toast.makeText(context, "負責人名稱不得為空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (viewModel.profileInvoice.get().isNullOrEmpty()) {
                Toast.makeText(context, "匯款帳號不得為空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (viewModel.profileRemittanceAccount.get().isNullOrEmpty()) {
                Toast.makeText(context, "匯款帳號不得為空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (viewModel.profileOfficePhone.get().isNullOrEmpty()) {
                Toast.makeText(context, "公司電話不得為空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (viewModel.profilePersonalPhone.get().isNullOrEmpty()) {
                Toast.makeText(context, "聯絡人電話不得為空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (viewModel.profileOfficeAddress.get().isNullOrEmpty()) {
                Toast.makeText(context, "公司地址不得為空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (viewModel.profileCorrespondenceAddress.get().isNullOrEmpty()) {
                Toast.makeText(context, "通訊地址不得為空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (viewModel.profileDeliveryFee.get().toString().isEmpty()) {
                Toast.makeText(context, "運費不得為空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                viewModel.profileDeliveryFee.get().toString().toFloat()
            } catch (e: Exception) {
                Toast.makeText(context, "運費請輸入 數字 類型", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val token = TokenManager.getToken(requireContext())
            val userId = UserInformationManager.getUserID(requireContext())

            if (!token.isNullOrEmpty() && userId != 0) {
                viewModel.updateUserProfile(token, userId)
            } else {
                Toast.makeText(requireContext(), "目前沒有資料", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.viewModelRefreshInternetStatus.observe(this, EventObserver {
            when(it) {
                VIEW_MODEL_INTERNET_SUCCESS ->
                    Toast.makeText(requireContext(), "查詢成功", Toast.LENGTH_LONG).show()
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
        val userId = UserInformationManager.getUserID(requireContext())

        if (!token.isNullOrEmpty() && userId != 0) {
            viewModel.refreshUserProfile(token, userId)
        } else {
            Toast.makeText(requireContext(), "目前沒有資料", Toast.LENGTH_LONG).show()
        }
    }
}