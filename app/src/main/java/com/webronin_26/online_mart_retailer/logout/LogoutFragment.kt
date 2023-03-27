package com.webronin_26.online_mart_retailer.logout

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
import com.webronin_26.online_mart_retailer.databinding.LogoutFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogoutFragment : Fragment() {

    private lateinit var viewModel: LogoutViewModel
    private lateinit var viewDataBinding: LogoutFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.logout_fragment, container, false)

        viewModel = ViewModelProvider(this)[LogoutViewModel::class.java]
        lifecycle.addObserver(viewModel)
        viewDataBinding = LogoutFragmentBinding.bind(view).apply { viewmodel = viewModel }
        viewDataBinding.lifecycleOwner = this

        return view
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView() {

        viewDataBinding.logoutFragmentLogoutButton.setOnClickListener {
            TokenManager.getToken(requireContext())?.let { token ->
                viewModel.logout(token)
            }
        }

        viewModel.viewModelInternetStatus.observe(this, EventObserver {
            when(it) {
                VIEW_MODEL_INTERNET_SUCCESS -> {
                    TokenManager.setToken(requireContext(), "")
                    findNavController().navigate(R.id.action_logout_fragment_to_main_fragment)
                }
                VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION ->
                    Toast.makeText(requireContext(), "網路連線異常，請確認網路狀態", Toast.LENGTH_LONG).show()
                VIEW_MODEL_INTERNET_ERROR ->
                    Toast.makeText(requireContext(), "網路錯誤，請稍等", Toast.LENGTH_LONG).show()
            }
        })
    }
}