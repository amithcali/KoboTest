package com.kobo.demo.challenge.application.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


import com.kobo.demo.challenge.application.base.BaseViewModel
import com.kobo.demo.challenge.application.data.Resource
import com.kobo.demo.challenge.application.local.UserDatabase
import com.kobo.demo.challenge.application.model.User
import com.kobo.demo.challenge.application.model.UserPage
import com.kobo.demo.challenge.application.repo.UserRepository

class HomeViewModel @ViewModelInject constructor(private val service: UserRepository, private val userDatabase: UserDatabase): BaseViewModel() {

    init {
        getUsers()
    }

    private var userPagesLiveData = MutableLiveData<UserPage>()

    private val callObserver: Observer<Resource<UserPage>> =
        Observer { t -> processResponse(t) }

    private fun processResponse(response: Resource<UserPage>?) {
        when (response?.status) {
            Resource.Status.LOADING -> {
                setLoading()
            }
            Resource.Status.SUCCESS -> {
                setSuccess()
                userPagesLiveData.value = response.data
            }
            Resource.Status.ERROR -> {
                setError()
                error.value = response.apiError
            }
        }
    }


    private fun getUsers() {
        service.getUserPages().observeForever { callObserver.onChanged(it) }
    }

    fun callUsers(url: String): MutableLiveData<Resource<List<User>>> {
        return service.getUsers(url)
    }

    fun getUserPages(): LiveData<UserPage> {
        return userPagesLiveData
    }

    fun getUsersFromDb(): LiveData<List<User>> {
        return userDatabase.userDao().getUsers()
    }
}