package com.webronin_26.online_mart_retailer.profile

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
class ProfileViewModel  @Inject constructor(private val retailerRepository: RetailerRepository) : ViewModel(), LifecycleEventObserver {

    val viewModelRefreshInternetStatus = MutableLiveData<Event<Int>>()
    val viewModelUpdateInternetStatus = MutableLiveData<Event<Int>>()

    val profileName = ObservableField<String>()
    val profileResponsiblePerson = ObservableField<String>()
    val profileInvoice = ObservableField<String>()
    val profileRemittanceAccount = ObservableField<String>()
    val profileOfficePhone = ObservableField<String>()
    val profilePersonalPhone = ObservableField<String>()
    val profileOfficeAddress = ObservableField<String>()
    val profileCorrespondenceAddress = ObservableField<String>()
    val profileDeliveryFee = ObservableField<String>() // float

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        //
    }

    fun refreshUserProfile(token: String, retailerId : Int) {
        viewModelScope.launch {
            retailerRepository.profileQuery(token, retailerId).let { result ->
                when (result) {
                    is Result.Success -> {
                        profileName.set(result.data.data.name)
                        profileResponsiblePerson.set(result.data.data.responsiblePerson)
                        profileInvoice.set(result.data.data.invoice)
                        profileRemittanceAccount.set(result.data.data.remittanceAccount)
                        profileOfficePhone.set(result.data.data.officePhone)
                        profilePersonalPhone.set(result.data.data.personalPhone)
                        profileOfficeAddress.set(result.data.data.officeAddress)
                        profileCorrespondenceAddress.set(result.data.data.correspondenceAddress)
                        profileDeliveryFee.set(result.data.data.deliveryFee.toString())

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

    fun updateUserProfile(token: String, retailerId: Int) {
        viewModelScope.launch {
            retailerRepository.updateProfile(token,
                profileName.get()!!,
                profileResponsiblePerson.get()!!,
                profileInvoice.get()!!,
                profileRemittanceAccount.get()!!,
                profileOfficePhone.get()!!,
                profilePersonalPhone.get()!!,
                profileOfficeAddress.get()!!,
                profileCorrespondenceAddress.get()!!,
                profileDeliveryFee.get()!!.toFloat()).let { result ->
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