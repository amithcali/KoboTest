package com.kobo.demo.challenge.application.model


import com.google.gson.annotations.SerializedName

data class UserPage(
    @SerializedName("pages")
    val pages: List<String>?
)