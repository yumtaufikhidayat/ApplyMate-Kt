package id.yumtaufikhidayat.applymate.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import id.yumtaufikhidayat.applymate.core.ext.normalizeJobLink
import id.yumtaufikhidayat.applymate.core.helper.ApplicationTextType

@Composable
fun ApplicationTextInfo(
    modifier: Modifier = Modifier,
    title: String = "",
    description: String = "",
    type: ApplicationTextType = ApplicationTextType.TEXT,
) {

    val uriHandler = LocalUriHandler.current
    val color = if (type == ApplicationTextType.LINK)
        MaterialTheme.colorScheme.primary
    else
        MaterialTheme.colorScheme.onSurface

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        ApplicationTitle(
            title = title,
        )
        ApplicationDescription(
            description = description,
            color = color,
            uriHandler = uriHandler,
            type = type
        )
    }
}

@Composable
fun ApplicationTitle(
    modifier: Modifier = Modifier,
    title: String,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
fun ApplicationDescription(
    modifier: Modifier = Modifier,
    description: String,
    type: ApplicationTextType,
    color: Color,
    uriHandler: UriHandler,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        if (type == ApplicationTextType.LINK) {
            Text(
                text = description,
                color = color,
                style = MaterialTheme.typography.bodyMedium.copy(
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.clickable {
                    val normalized = description.normalizeJobLink()
                    uriHandler.openUri(normalized)
                }
            )
        } else {
            Text(
                text = description,
                color = color,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal)
            )
        }
    }
}