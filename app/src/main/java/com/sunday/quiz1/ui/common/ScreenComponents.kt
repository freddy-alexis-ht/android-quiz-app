package com.sunday.quiz1.ui.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MyButton(
    onclick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    colors: Color = MaterialTheme.colors.primary
) {
    Button(
        onClick = onclick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = colors)
    ) {
        Text(text = text )
    }
}

@Composable
fun MySpacer16Dp() {
    Spacer(modifier = Modifier.padding(top = 16.dp))
}