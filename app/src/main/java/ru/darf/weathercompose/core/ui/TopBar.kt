package ru.darf.weathercompose.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopBar(
    title: String,
    modifier: Modifier = Modifier,
    height: Dp = 56.dp,
    actions: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .height(height)
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            fontSize = 20.sp,
            fontWeight = FontWeight.W500
        )
        actions?.invoke()
    }
}