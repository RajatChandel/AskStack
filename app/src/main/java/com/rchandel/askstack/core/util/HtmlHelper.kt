package com.rchandel.askstack.core.util

import android.text.Html
import android.text.Spanned

fun parseHtmlToSpanned(htmlText: String): Spanned {
    return Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT)
}