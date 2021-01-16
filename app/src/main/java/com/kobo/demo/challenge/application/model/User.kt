package com.kobo.demo.challenge.application.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class User(
    @SerializedName("avatar")
    val avatar: String? = "",
    @SerializedName("avatar_large")
    val avatarLarge: String? = "",
    @SerializedName("backgroundColor")
    val backgroundColor: String? = "",
    @SerializedName("email")
    val email: String? = "",
    @SerializedName("first_name")
    val firstName: String? = "",
    @PrimaryKey
    @SerializedName("id")
    val id: String = "",
    @SerializedName("last_name")
    val lastName: String? = "",
    @SerializedName("text")
    val text: String? = ""
)