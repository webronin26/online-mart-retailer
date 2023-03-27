package com.webronin_26.online_mart_retailer.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.webronin_26.online_mart_retailer.Event
import com.webronin_26.online_mart_retailer.VIEW_MODEL_INTERNET_ERROR
import com.webronin_26.online_mart_retailer.VIEW_MODEL_INTERNET_SUCCESS
import com.webronin_26.online_mart_retailer.data.source.RetailerRepository
import com.webronin_26.online_mart_retailer.data.remote.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginViewModelState(
    var showAlertDialog: Boolean = false,
    var errorTitle: String = "",
    var errorText: String = "",
)

data class LoginInformation(
    var token: String = "",
    var id: Int = 0,
    var name: String = "",
    var currentAccount: String = "",
    var currentPassword: String = "",
)

@HiltViewModel
class LoginViewModel @Inject constructor(private val retailerRepository: RetailerRepository) : ViewModel() {

    var loginInformation = LoginInformation()
        get() = field

    // view model's internet status (success / error)
    val viewModelInternetStatus = MutableLiveData<Event<Int>>()

    private val loginState = MutableStateFlow(LoginViewModelState())
    val state: StateFlow<LoginViewModelState> = loginState.asStateFlow()

    fun updateState(show: Boolean, titleString: String, alertString: String) {
        loginState.update {
            it.copy(
                showAlertDialog = show,
                errorTitle = titleString,
                errorText = alertString
            )
        }
    }

    fun loginRequest(account: String, password: String) {
        viewModelScope.launch {
            retailerRepository.login(account, password).let { result ->
                when (result) {
                    is Result.Success -> {
                        loginInformation.token = result.data.data.token
                        loginInformation.id = result.data.data.id
                        loginInformation.name = result.data.data.name
                        loginInformation.currentAccount = account
                        loginInformation.currentPassword = password

                        viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_SUCCESS)
                    }
                    is Result.ConnectException -> {
                        updateState(
                            show = true,
                            titleString = "注意",
                            alertString = "網路連線異常",
                        )
                        viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_ERROR)
                    }
                    else -> {
                        updateState(
                            show = true,
                            titleString = "警告",
                            alertString = "帳號密碼有誤",
                        )
                        viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_ERROR)
                    }
                }
            }
        }
    }
}