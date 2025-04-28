package com.rchandel.askstack.data.mappers

import com.rchandel.askstack.data.remote.dto.QuestionItem
import com.rchandel.askstack.domain.model.Question

fun QuestionItem.toDomain(): Question {
    return Question(
        title = title,
        authorName = owner.displayName,
        creationDate = creationDate,
        link = link,
        body = body
    )
}