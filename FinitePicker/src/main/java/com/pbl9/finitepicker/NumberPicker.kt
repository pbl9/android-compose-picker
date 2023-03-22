package com.pbl9.finitepicker

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun NumberPicker(
    modifier: Modifier = Modifier,
    range: IntRange,
    intToStringMapper: (Int) -> String = { it.toString() },
    visibleItemsCount: Int = 3,//should be odd number greater than 1
    optionTextStyle: TextStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
    onOptionChanged: (Int) -> Unit
) = FinitePicker(
    modifier = modifier,
    options = range.toList(),
    optionToStringMapper = intToStringMapper,
    onOptionChanged = onOptionChanged,
    optionTextStyle = optionTextStyle,
    visibleItemsCount = visibleItemsCount,
)