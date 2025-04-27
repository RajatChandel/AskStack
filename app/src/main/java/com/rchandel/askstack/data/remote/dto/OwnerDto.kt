package com.rchandel.askstack.data.remote.dto

import com.google.gson.annotations.SerializedName

data class OwnerDto(
    @SerializedName("display_name") val displayName: String
)