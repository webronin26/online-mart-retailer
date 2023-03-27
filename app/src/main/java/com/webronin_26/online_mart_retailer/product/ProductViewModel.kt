package com.webronin_26.online_mart_retailer.product

import androidx.databinding.ObservableField
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
class ProductViewModel  @Inject constructor(private val retailerRepository: RetailerRepository) : ViewModel(), LifecycleEventObserver {

    val viewModelRefreshInternetStatus = MutableLiveData<Event<Int>>()
    val viewModelUpdateInternetStatus = MutableLiveData<Event<Int>>()

    val productEditTextName = ObservableField<String>()
    val productEditTextImageUrl = ObservableField<String>()
    val productEditTextSummary = ObservableField<String>()
    val productEditTextInformation = ObservableField<String>()
    val productEditTextPrice = ObservableField<String>()
    val productEditTextNumber = ObservableField<String>()
    val productEditTextMaxBuy = ObservableField<String>()

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        // do nothing
    }

    fun refreshProduct(token: String, productId: Int) {
        viewModelScope.launch {
            retailerRepository.productQuery(token, productId).let { result ->
                when (result) {
                    is Result.Success -> {
                        productEditTextName.set(result.data.data.name)
                        productEditTextImageUrl.set(result.data.data.imageUrl)
                        productEditTextSummary.set(result.data.data.summary)
                        productEditTextInformation.set(result.data.data.information)
                        productEditTextPrice.set(result.data.data.price.toString())
                        productEditTextNumber.set(result.data.data.inventoryNumber.toString())
                        productEditTextMaxBuy.set(result.data.data.maxBuy.toString())

                        viewModelRefreshInternetStatus.value = Event(VIEW_MODEL_INTERNET_SUCCESS)
                    }
                    is Result.ConnectException ->
                        viewModelRefreshInternetStatus.value = Event(VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION)
                    else ->
                        viewModelRefreshInternetStatus.value = Event(VIEW_MODEL_INTERNET_ERROR)
                }
            }
        }
    }

    fun updateProduct(token: String, productId: Int) {
        viewModelScope.launch {
            if (productEditTextImageUrl.get().isNullOrEmpty()) { productEditTextImageUrl.set("") }
            if (productEditTextSummary.get().isNullOrEmpty()) { productEditTextSummary.set("") }
            if (productEditTextInformation.get().isNullOrEmpty()) { productEditTextInformation.set("") }

            retailerRepository.updateProduct(token, productId,
                productEditTextName.get()!!,
                productEditTextImageUrl.get()!!,
                productEditTextSummary.get()!!,
                productEditTextInformation.get()!!,
                productEditTextPrice.get()!!.toFloat(),
                productEditTextNumber.get()!!.toInt(),
                productEditTextMaxBuy.get()!!.toInt()).let { result ->
                when (result) {
                    is Result.Success ->
                        viewModelUpdateInternetStatus.value = Event(VIEW_MODEL_INTERNET_SUCCESS)
                    is Result.ConnectException ->
                        viewModelUpdateInternetStatus.value = Event(VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION)
                    else ->
                        viewModelUpdateInternetStatus.value = Event(VIEW_MODEL_INTERNET_ERROR)
                }
            }
        }
    }
}