package com.kobo.demo.challenge.application.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kobo.demo.challenge.application.data.APIService
import com.kobo.demo.challenge.application.data.NetworkCall
import com.kobo.demo.challenge.application.data.Resource
import com.kobo.demo.challenge.application.model.User
import com.kobo.demo.challenge.application.model.UserPage
import com.kobo.demo.challenge.application.repo.UserRepository

import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val service: APIService) : UserRepository {
    override fun getUserPages(): MutableLiveData<Resource<UserPage>> {
            return NetworkCall<UserPage>().makeCall(service.getUserPages())
    }

    override fun getUsers(url:String): MutableLiveData<Resource<List<User>>> {
        return NetworkCall<List<User>>().makeCall(service.getUsers(url))
    }

}