package id.yumtaufikhidayat.applymate.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import id.yumtaufikhidayat.applymate.core.ext.toCurrencyFormatted

@Composable
fun FormCurrencyTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var textFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                value.toCurrencyFormatted()
            )
        )
    }

    OutlinedTextField(
        value = textFieldValue,
        onValueChange = { newValue ->
            val digitsOnly = newValue.text.filter { it.isDigit() }
            val formatted = digitsOnly.toCurrencyFormatted()

            // Make sure the cursor is always at the end after formatting
            textFieldValue = TextFieldValue(
                text = formatted,
                selection = TextRange(formatted.length)
            )

            onValueChange(digitsOnly)
        },
        label = { Text(label) },
        prefix = { Text("Rp ") },
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        modifier = modifier.fillMaxWidth()
    )

    // Synchronize if the state of the ViewModel changes (edit mode for example)
    LaunchedEffect(value) {
        val formatted = value.toCurrencyFormatted()
        if (formatted != textFieldValue.text) {
            textFieldValue = TextFieldValue(
                text = formatted,
                selection = TextRange(formatted.length)
            )
        }
    }
}