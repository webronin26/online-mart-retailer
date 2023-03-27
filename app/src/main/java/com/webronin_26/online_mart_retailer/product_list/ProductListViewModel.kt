package com.webronin_26.online_mart_retailer.product_list

import androidx.lifecycle.*
import com.webronin_26.online_mart_retailer.*
import com.webronin_26.online_mart_retailer.data.remote.Response
import com.webronin_26.online_mart_retailer.data.remote.Result
import com.webronin_26.online_mart_retailer.data.source.RetailerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel  @Inject constructor(private val retailerRepository: RetailerRepository) : ViewModel(), LifecycleEventObserver {

    val products = MutableLiveData<Event<List<Response.Products>>>()
    val viewModelInternetStatus = MutableLiveData<Event<Int>>()

    val productId =  MutableLiveData<Event<Int>>()

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        //
    }

    fun refreshList(token: String) {
        viewModelScope.launch {
            retailerRepository.productList(token).let { result ->
                when (result) {
                    is Result.Success -> {
                        if (result.data.count != 0) {
                            val productList =  result.data.data.toMutableList()
                            if (productList.size != 0) {
                                products.value = Event(productList)
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