package com.kobo.demo.challenge.application.data



class Resource<T> private constructor(val status: Status, val data: T?, val apiError: ErrorMessage?) {
    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(apiError: ErrorMessage): Resource<T> {
            return Resource(Status.ERROR, null, apiError)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}