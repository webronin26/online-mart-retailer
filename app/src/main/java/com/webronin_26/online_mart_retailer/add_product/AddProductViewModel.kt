package com.webronin_26.online_mart_retailer.add_product

import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.webronin_26.online_mart_retailer.*
import com.webronin_26.online_mart_retailer.data.remote.Result
import com.webronin_26.online_mart_retailer.data.source.RetailerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel  @Inject constructor(private val retailerRepository: RetailerRepository) : ViewModel(), LifecycleEventObserver {

    val viewModelInternetStatus = MutableLiveData<Event<Int>>()

    val addProductEditTextName = ObservableField<String>()
    val addProductEditTextImageUrl = ObservableField<String>()
    val addProductEditTextSummary = ObservableField<String>()
    val addProductEditTextInformation = ObservableField<String>()
    val addProductEditTextPrice = ObservableField<String>() // float
    val addProductEditTextNumber = ObservableField<String>() // int
    val addProductEditTextMaxBuy = ObservableField<String>() // int

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        // do nothing
    }

    fun addProduct(token: String) {
        viewModelScope.launch {
            if (addProductEditTextImageUrl.get().isNullOrEmpty()) { addProductEditTextImageUrl.set("") }
            if (addProductEditTextSummary.get().isNullOrEmpty()) { addProductEditTextSummary.set("") }
            if (addProductEditTextInformation.get().isNullOrEmpty()) { addProductEditTextInformation.set("") }

            retailerRepository.addProduct(token,
                addProductEditTextName.get()!!,
                addProductEditTextImageUrl.get()!!,
                addProductEditTextSummary.get()!!,
                addProductEditTextInformation.get()!!,
                addProductEditTextPrice.get()!!.toFloat(),
                addProductEditTextNumber.get()!!.toInt(),
                addProductEditTextMaxBuy.get()!!.toInt()).let { result ->
                when (result) {
                    is Result.Success -> {
                        viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_SUCCESS)
                        setAllValueNull()
                    }
                    is Result.CreateRecordNameError -> {
                        viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_ERROR_PRODUCT_NAME)
                    }
                    is Result.ConnectException ->
                        viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION)
                    else ->
                        viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_ERROR)
                }
            }
        }
    }

    private fun setAllValueNull() {
        addProductEditTextName.set("")
        addProductEditTextImageUrl.set("")
        addProductEditTextSummary.set("")
        addProductEditTextInformation.set("")
        addProductEditTextPrice.set("")
        addProductEditTextNumber.set("")
        addProductEditTextMaxBuy.set("")
    }
}