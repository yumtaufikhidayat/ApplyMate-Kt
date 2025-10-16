package id.yumtaufikhidayat.applymate.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FormDateField(
    modifier: Modifier = Modifier,
    label: String,
    valueText: String,
    error: String? = null,
    onClick: () -> Unit,
) {
    Box(modifier = modifier) {

        OutlinedTextField(
            value = valueText,
            onValueChange = {},
            label = { Text(label) },
            isError = error != null,
            readOnly = true,
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        )

        Box(
            Modifier
                .matchParentSize()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onClick() }
        )
    }

    AnimatedVisibility(visible = error != null) {
        Text(
            text = error ?: "",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 2.dp, start = 4.dp)
        )
    }
}
