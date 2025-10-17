package id.yumtaufikhidayat.applymate.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    show: Boolean,
    initialTime: LocalTime? = null,
    onDismiss: () -> Unit,
    onConfirm: (LocalTime) -> Unit
) {
    if (!show) return

    val now = LocalTime.now()
    val timePickerState = rememberTimePickerState(
        initialHour = initialTime?.hour ?: now.hour,
        initialMinute = initialTime?.minute ?: now.minute
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onConfirm(LocalTime.of(timePickerState.hour, timePickerState.minute))
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Batal") }
        },
        text = { TimePicker(state = timePickerState) }
    )
}
