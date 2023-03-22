package com.pbl9.finitepicker

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter


@OptIn(ExperimentalSnapperApi::class)
@Composable
fun <T> FinitePicker(
    modifier: Modifier = Modifier,
    options: List<T>,
    optionToStringMapper: (T) -> String = { it.toString() },
    visibleItemsCount: Int = 3,//should be odd number greater than 1,
    optionTextStyle: TextStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
    onOptionChanged: (T) -> Unit,
) {
    require(visibleItemsCount > 1 && visibleItemsCount % 2 == 1 && options.size > visibleItemsCount)
    val listState = rememberLazyListState(Int.MAX_VALUE / 2)
    Box(
        modifier
    ) {
        LazyColumn(
            state = listState,
            flingBehavior = rememberSnapperFlingBehavior(lazyListState = listState),
            modifier = Modifier.fillMaxSize()
        ) {
            items(Int.MAX_VALUE, itemContent = {
                val index = it % options.size
                val option = options[index]
                OptionItem(
                    optionToStringMapper(option),
                    style = optionTextStyle,
                    Modifier
                        .fillParentMaxHeight(1f / visibleItemsCount)
                        .fillMaxWidth()
                )
            })
        }
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth()
                .fillMaxHeight((visibleItemsCount - 1f) / (2f * visibleItemsCount))
                .drawBehind {
                    drawRect(Color.White.copy(alpha = 0.5f))
                }
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .fillMaxHeight((visibleItemsCount - 1f) / (2f * visibleItemsCount))
                .drawBehind {
                    drawRect(Color.White.copy(alpha = 0.5f))
                }
        )
    }
    LaunchedEffect(listState) {
        snapshotFlow { listState.isScrollInProgress to listState.firstVisibleItemIndex }
            .distinctUntilChanged { old, new ->
                old.first == new.first
            }
            .filter { !it.first }
            .collect {
                onOptionChanged(options[(it.second + visibleItemsCount / 2) % options.size])
            }
    }
}

@Composable
private fun OptionItem(optionText: String, style: TextStyle, modifier: Modifier = Modifier) {
    Box(modifier = Modifier.then(modifier)) {
        Text(
            text = optionText,
            style = style,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
@Preview
fun FinitePickerPreview() {
    val options = List(20) { it }
    FinitePicker(options = options,
        visibleItemsCount = 3,
        modifier = Modifier
            .width(300.dp)
            .height(240.dp),
        onOptionChanged = {})
}
