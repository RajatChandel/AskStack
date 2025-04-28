package com.rchandel.askstack.presentation.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.rchandel.askstack.R

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    focusManager: FocusManager,
    enabled: Boolean = false,
) {
    Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_medium))
    ) {
        OutlinedTextField(
            value = query,
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.spacing_medium)),
            onValueChange = onQueryChange,
            placeholder = { Text(stringResource(R.string.search_questions)) },
            modifier = Modifier
                .weight(1f)
                .height(dimensionResource(id = R.dimen.button_height)),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    focusManager.clearFocus()
                    onSearch()
                }
            )
        )

        Button(
            onClick = {
                focusManager.clearFocus()
                onSearch()
            },
            enabled = enabled,
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.spacing_medium)),
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .height(dimensionResource(id = R.dimen.button_height))

        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_search_24),
                contentDescription = "Search",
                modifier = Modifier.size(dimensionResource(id = R.dimen.spacing_xlarge)) // Size of the icon
            )
        }

    }
}