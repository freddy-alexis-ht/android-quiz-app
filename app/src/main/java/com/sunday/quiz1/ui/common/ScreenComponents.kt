package com.sunday.quiz1.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun MyButton(
    onclick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    colors: Color = MaterialTheme.colors.primary,
    textAlign: TextAlign = TextAlign.Center,
) {
    Button(
        onClick = onclick,
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(backgroundColor = colors)
    ) {
        var firstLine: String = text
        var secondLine: String = ""
        if (text.contains("\n")) {
            firstLine = text.substring(0, text.indexOf("\n"))
            secondLine = text.substring(text.indexOf("\n"))
        }
        Text(
            text = buildAnnotatedString {
                append(firstLine)
                withStyle((MaterialTheme.typography.caption).toSpanStyle()){
                    append(secondLine)
                }
            },
            textAlign = textAlign)
    }
}

@Composable
fun MyVerticalSpacer(dp: Dp) {
    Spacer(modifier = Modifier.height(dp))
}

@Composable
fun MyHorizontalSpacer(dp: Dp) {
    Spacer(modifier = Modifier.width(dp))
}

@Composable
fun VerticalDivider() {
    Divider(
        modifier = Modifier
            .fillMaxHeight()
            .width(1.dp)
    )
}
