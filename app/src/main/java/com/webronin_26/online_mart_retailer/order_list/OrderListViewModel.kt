package com.webronin_26.online_mart_retailer.order_list

import androidx.lifecycle.*
import com.webronin_26.online_mart_retailer.*
import com.webronin_26.online_mart_retailer.data.remote.Response
import com.webronin_26.online_mart_retailer.data.source.RetailerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.webronin_26.online_mart_retailer.data.remote.Result
import javax.inject.Inject

@HiltViewModel
class OrderListViewModel  @Inject constructor(private val retailerRepository: RetailerRepository) : ViewModel(), LifecycleEventObserver {

    val orders = MutableLiveData<Event<List<Response.Orders>>>()
    val viewModelInternetStatus = MutableLiveData<Event<Int>>()

    val orderId =  MutableLiveData<Event<Int>>()

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        //
    }

    fun refreshList(token: String) {
        viewModelScope.launch {
            retailerRepository.orderList(token).let { result ->
                when (result) {
                    is Result.Success -> {
                        if (result.data.count != 0) {
                            val orderList =  result.data.data.toMutableList()
                            if (orderList.size != 0) {
                                orders.value = Event(orderList)
                                viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_SUCCESS)
                            } else {
                                viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_SUCCESS_BUT_EMPTY)
                            }
                        } else {
                            viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_SUCCESS_BUT_EMPTY)
                        }
                    }
                    is Result.ConnectException ->
                        viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION)
                    else ->
                        viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_ERROR)
                }
            }
        }
    }
}