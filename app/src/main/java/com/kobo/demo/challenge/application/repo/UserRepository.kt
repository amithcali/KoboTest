package com.kobo.demo.challenge.application.repo

import androidx.lifecycle.MutableLiveData
import com.kobo.demo.challenge.application.data.Resource
import com.kobo.demo.challenge.application.model.User
import com.kobo.demo.challenge.application.model.UserPage


interface UserRepository {
    fun getUserPages() : MutableLiveData<Resource<UserPage>>
    fun getUsers(url:String): MutableLiveData<Resource<List<User>>>
}