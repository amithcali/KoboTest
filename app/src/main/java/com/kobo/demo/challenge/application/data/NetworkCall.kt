package com.kobo.demo.challenge.application.data

import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class NetworkCall<T> {
    lateinit var call: Call<T>

    fun makeCall(call: Call<T>): MutableLiveData<Resource<T>> {
        this.call = call
        val callBackKt = CallBackKt<T>()
        callBackKt.result.postValue(Resource.loading(null))
        this.call.clone().enqueue(callBackKt)
        return callBackKt.result
    }

    class CallBackKt<T> : Callback<T> {
        var result: MutableLiveData<Resource<T>> = MutableLiveData()

        override fun onFailure(call: Call<T>, t: Throwable) {
            val hashMap = HashMap<String, String>()
            t.message?.let { hashMap.put("message", it) }
            result.postValue(Resource.error(ErrorMessage(t.message ?: "", 500)))
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful)
                result.postValue(Resource.success(response.body()))
            else {
                result.postValue(
                    Resource.error(
                        ErrorMessage(
                            "Something went wrong. Try again",
                            response.code()
                        )
                    )
                )
            }
        }
    }

    fun cancel() {
        if (::call.isInitialized) {
            call.cancel()
        }
    }
}