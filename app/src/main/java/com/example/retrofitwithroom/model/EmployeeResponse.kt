package com.example.retrofitwithroom.model

import com.google.gson.annotations.SerializedName

data class EmployeeResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    val data: List<EmployeeData>,
    @SerializedName("message")
    val message: String
)
