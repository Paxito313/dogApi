package com.example.dogapp.data

import com.google.gson.annotations.SerializedName

data class DogsResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val images: List<String>
)
