package com.example.worktracker.contributors.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ContributorDto(
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("name")
    @Expose
    var name: String,
    @SerializedName("imageUrl")
    @Expose
    var imageUrl: String?,
    @SerializedName("createdAt")
    @Expose
    var createdAt: String,
    @SerializedName("updatedAt")
    @Expose
    var updatedAt: String
)