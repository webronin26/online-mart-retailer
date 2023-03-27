package com.webronin_26.online_mart_retailer.order

import androidx.lifecycle.*
import com.webronin_26.online_mart_retailer.Event
import com.webronin_26.online_mart_retailer.VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION
import com.webronin_26.online_mart_retailer.VIEW_MODEL_INTERNET_ERROR
import com.webronin_26.online_mart_retailer.VIEW_MODEL_INTERNET_SUCCESS
import com.webronin_26.online_mart_retailer.data.remote.Result
import com.webronin_26.online_mart_retailer.data.source.RetailerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel  @Inject constructor(private val retailerRepository: RetailerRepository) : ViewModel(), LifecycleEventObserver {

    val viewModelInternetStatus = MutableLiveData<Event<Int>>()

    val orderTextViewNumber = MutableLiveData<String>()
    val orderTextViewAddress = MutableLiveData<String>()
    val orderTextViewProductList = MutableLiveData<String>()
    val orderTextViewPrice = MutableLiveData<Float>()
    val orderTextViewPaidTime = MutableLiveData<String>()

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        // do nothing
    }

    fun refreshOrder(token: String, orderId: Int) {
        viewModelScope.launch {
            retailerRepository.orderQuery(token, orderId).let { result ->
                when (result) {
                    is Result.Success -> {
                        orderTextViewNumber.value = result.data.data.orderNumber
                        orderTextViewAddress.value = result.data.data.orderAddress
                        orderTextViewProductList.value = result.data.data.orderProductList
                        orderTextViewPrice.value = result.data.data.totalPrice
                        orderTextViewPaidTime.value = result.data.data.paidTime

                        viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_SUCCESS)
                    }
                    is Result.ConnectException ->
                        viewModelInternetStatus.value = Event(
                            VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION
                        )
                    else ->
                        viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_ERROR)
                }
            }
        }
    }
}