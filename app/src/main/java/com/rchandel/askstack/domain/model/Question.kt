package com.rchandel.askstack.domain.model

data class Question(
    val title: String,
    val authorName: String,
    val creationDate: Long,
    val link: String,
    val body: String?
)