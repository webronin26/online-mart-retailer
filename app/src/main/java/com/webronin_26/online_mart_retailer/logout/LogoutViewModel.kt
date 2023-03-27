package com.webronin_26.online_mart_retailer.logout

import androidx.lifecycle.*
import com.webronin_26.online_mart_retailer.Event
import com.webronin_26.online_mart_retailer.VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION
import com.webronin_26.online_mart_retailer.VIEW_MODEL_INTERNET_ERROR
import com.webronin_26.online_mart_retailer.VIEW_MODEL_INTERNET_SUCCESS
import com.webronin_26.online_mart_retailer.data.source.RetailerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import com.webronin_26.online_mart_retailer.data.remote.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogoutViewModel  @Inject constructor(private val retailerRepository: RetailerRepository) : ViewModel(), LifecycleEventObserver {

    val viewModelInternetStatus = MutableLiveData<Event<Int>>()

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        // do nothing
    }

    fun logout(token: String) {
        viewModelScope.launch {
            retailerRepository.logout(token).let { result ->
                when (result) {
                    is Result.Success ->
                        viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_SUCCESS)
                    is Result.ConnectException ->
                        viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION)
                    else ->
                        viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_ERROR)
                }
            }
        }
    }
}