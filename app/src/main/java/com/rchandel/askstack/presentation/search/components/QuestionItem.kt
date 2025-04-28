package com.rchandel.askstack.presentation.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.rchandel.askstack.R
import com.rchandel.askstack.core.util.formatTimestamp
import com.rchandel.askstack.domain.model.Question

@Composable
fun QuestionItem(question: Question, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.spacing_small))
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.spacing_small))
    ) {
        Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.spacing_s_large))) {
            Text(
                text = question.title,
                style = MaterialTheme.typography.titleMedium,
                color = colorScheme.primary
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_s_medium)))

            Text(
                text = "Author: ${question.authorName}",
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = "Created: ${formatTimestamp(question.creationDate)}",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_s_medium)))

            Text(
                text = question.link,
                style = MaterialTheme.typography.bodySmall,
                color = colorScheme.secondary
            )
        }
    }
}