package com.example.recyclerviewlab.sectionList

import com.google.gson.annotations.SerializedName
import java.util.*

data class People(
    val uuid: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val birthDate: String,
    val image: String,
    val email: String,
    val city: String,
    val country: String
)