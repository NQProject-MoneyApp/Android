package com.nqproject.MoneyApp.ui.screens.about

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.nqproject.MoneyApp.components.BasicHeader
import com.nqproject.MoneyApp.ui.theme.AppTheme

@Composable
fun AboutScreen(
    onBackNavigate: () -> Unit,
) {

    val scrollState = rememberScrollState()
    val uriTag = "URI"
    val uriHandler = LocalUriHandler.current

    val annotatedString = buildAnnotatedString {
        pushStyle(
            style = SpanStyle(
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colors.onSurface,
            )
        )
        pushStringAnnotation(
            tag = uriTag,
            annotation = "https://github.com/NQProject-MoneyApp",
        )
        append("https://github.com/NQProject-MoneyApp")
    }

    BasicHeader(title = "About", didPressBackButton = onBackNavigate) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                "Creators",
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h2
            )

            Spacer(modifier = Modifier.height(21.dp))

            CreatorComponent("Danielle Saldanha", "danielle.saldanha98@gmail.com")
            CreatorComponent("Szymon Gęsicki", "szym.gesicki@gmail.com")
            CreatorComponent("Jędrzej Głowaczewski", "x.speerit@gmail.com")
            CreatorComponent("Miłosz Głowaczwski", "milosz.glowaczewski@gmail.com")

            Spacer(modifier = Modifier.height(34.dp))

            Text(
                "Source Code",
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h2
            )
            Spacer(modifier = Modifier.height(21.dp))

            ClickableText(
                text = annotatedString,
                style = MaterialTheme.typography.body1,
                onClick = { position ->
                    val annotations = annotatedString.getStringAnnotations(
                        uriTag, start = position, end = position
                    )
                    annotations.firstOrNull()?.let {
                        uriHandler.openUri(it.item)
                    }
                },
            )

            Text(
                "All rights reserved.",
                color = AppTheme.colors.hintText,
                style = MaterialTheme.typography.body1,
            )
        }
    }
}