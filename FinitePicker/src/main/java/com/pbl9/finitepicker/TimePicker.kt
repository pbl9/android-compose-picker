package com.pbl9.finitepicker

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import java.time.LocalTime

private fun Int.toDoubleDigitString() = toString().padStart(2, '0')

@Composable
fun TimePicker(
    modifier: Modifier = Modifier,
    visibleItemsCount: Int = 3,//should be odd number greater than 1
    onTimeChanged: (LocalTime) -> Unit,
) {
    var currentTime by remember {
        mutableStateOf(LocalTime.of(0, 0))
    }
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        NumberPicker(
            range = 0..23,
            intToStringMapper = { it.toDoubleDigitString() },
            onOptionChanged = {
                currentTime = LocalTime.of(it, currentTime.minute)
                onTimeChanged(currentTime)
            },
            visibleItemsCount = visibleItemsCount,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        )
        Text(":", fontSize = 20.sp)
        NumberPicker(
            range = 0..59,
            intToStringMapper = { it.toDoubleDigitString() },
            onOptionChanged = {
                currentTime = LocalTime.of(currentTime.hour, it)
                onTimeChanged(currentTime)
            },
            visibleItemsCount = visibleItemsCount,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        )
    }
}