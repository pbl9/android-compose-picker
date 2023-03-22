package com.pbl9.pickers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.pbl9.finitepicker.FinitePicker
import com.pbl9.finitepicker.NumberPicker
import com.pbl9.finitepicker.TimePicker
import com.pbl9.pickers.ui.theme.NumberPickerDialogTheme

enum class PickerDialog {
    CUSTOM, TIME, NUMBER
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var dialog: PickerDialog? by remember {
                mutableStateOf(null)
            }
            val options = listOf(
                "Mokotów",
                "Wola",
                "Ochota",
                "Żoliborz",
                "Gocław",
                "Praga Południe",
                "Bemowo"
            )
            val dialogCloser = {
                dialog = null
            }
            NumberPickerDialogTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Box(Modifier.fillMaxSize()) {
                        when (dialog) {
                            PickerDialog.CUSTOM -> {
                                FinitePickerDialog(
                                    options = options,
                                    modifier = Modifier.align(Alignment.BottomStart),
                                    onClose = dialogCloser
                                )
                            }
                            PickerDialog.TIME -> {
                                TimePickerDialog(
                                    modifier = Modifier.align(Alignment.BottomStart),
                                    onClose = dialogCloser
                                )
                            }
                            PickerDialog.NUMBER -> {
                                NumberPickerDialog(
                                    range = 0..100,
                                    Modifier.align(Alignment.BottomStart),
                                    onClose = dialogCloser
                                )
                            }
                            else -> {
                                Column(
                                    Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    PickerDialog.values().forEach { pickerDialog ->
                                        Button(modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 32.dp),
                                            onClick = { dialog = pickerDialog }) {
                                            Text(text = "${pickerDialog.name.lowercase()} picker")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun <T> FinitePickerDialog(
    options: List<T>,
    optionToStringMapper: (T) -> String = { it.toString() },
    modifier: Modifier = Modifier, onClose: () -> Unit
) {
    var currentOption by remember {
        mutableStateOf("")
    }
    Dialog(onDismissRequest = onClose) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(
                    color = Color.White, shape = RoundedCornerShape(16.dp)
                )
                .then(modifier)
        ) {
            Text(text = "You selected: $currentOption", modifier = Modifier.align(Alignment.TopCenter).padding(top = 4.dp), fontSize = 20.sp)
            FinitePicker(
                modifier = Modifier
                    .height(240.dp)
                    .align(Alignment.Center),
                options = options,
                optionToStringMapper = optionToStringMapper,
                onOptionChanged = {
                    currentOption = it.toString()
                })
        }
    }
}

@Composable
fun TimePickerDialog(
    modifier: Modifier = Modifier, onClose: () -> Unit
) {
    Dialog(onDismissRequest = onClose) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(272.dp)
                .background(
                    color = Color.White, shape = RoundedCornerShape(16.dp)
                )
                .then(modifier)
        ) {
            TimePicker(
                modifier = Modifier
                    .height(240.dp)
                    .align(Alignment.Center),
                onTimeChanged = {
                    println("Current value is $it")
                })
        }
    }
}

@Composable
fun NumberPickerDialog(
    range: IntRange,
    modifier: Modifier = Modifier,
    onClose: () -> Unit
) {
    Dialog(onDismissRequest = onClose) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(272.dp)
                .background(
                    color = Color.White, shape = RoundedCornerShape(16.dp)
                )
                .then(modifier)
        ) {
            NumberPicker(
                range = range,
                modifier = Modifier
                    .height(240.dp)
                    .align(Alignment.Center),
                onOptionChanged = {
                    println("Current value is $it")
                })
        }
    }
}
