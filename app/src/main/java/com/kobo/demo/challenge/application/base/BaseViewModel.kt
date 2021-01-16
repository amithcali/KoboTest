package com.kobo.demo.challenge.application.base
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kobo.demo.challenge.application.data.ErrorMessage


open class BaseViewModel : ViewModel() {
    private val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    private val isError: MutableLiveData<Boolean> = MutableLiveData()
    private val isSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val error: MutableLiveData<ErrorMessage> = MutableLiveData()

    internal fun setSuccess() {
        isLoading.value = false
        isSuccess.value = true
        isError.value = false
    }

    internal fun setError() {
        isLoading.value = false
        isSuccess.value = false
        isError.value = true
    }

    internal fun setLoading() {
        isLoading.value = true
        isSuccess.value = false
        isError.value = false
    }

}