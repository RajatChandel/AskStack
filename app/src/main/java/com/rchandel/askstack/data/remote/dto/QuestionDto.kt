package com.rchandel.askstack.data.remote.dto

import com.google.gson.annotations.SerializedName

data class QuestionDto(
    @SerializedName("items") val items: List<QuestionItem>
)

data class QuestionItem(
    @SerializedName("title") val title: String,
    @SerializedName("owner") val owner: OwnerDto,
    @SerializedName("creation_date") val creationDate: Long,
    @SerializedName("link") val link: String
)