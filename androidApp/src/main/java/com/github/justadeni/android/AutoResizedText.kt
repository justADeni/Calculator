package com.github.justadeni.android

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.isUnspecified
import androidx.compose.ui.unit.sp

@Composable
fun AutoResizedText(
    text: String,
    style: TextStyle,
    modifier: Modifier = Modifier,
) {
    var resizedTextStyle by remember { mutableStateOf(style) }
    var shouldDraw by remember { mutableStateOf(false) }

    var scalingfactor by remember { mutableFloatStateOf(0f) }
    var scalesOverChars by remember { mutableIntStateOf(0) }
    var lastlength by remember { mutableIntStateOf(text.length) }

    Text(
        text = text,
        color = style.color,
        modifier = modifier.drawWithContent {
           if (shouldDraw)
               drawContent()
        },
        fontSize = resizedTextStyle.fontSize,
        softWrap = false,
        onTextLayout = { result ->
            if (style.fontSize.isUnspecified) {
                resizedTextStyle = resizedTextStyle.copy(
                    fontSize = 80.sp
                )
            }

            if (result.didOverflowWidth) {
                resizedTextStyle = resizedTextStyle.copy(
                    fontSize = resizedTextStyle.fontSize * 0.95
                )

            } else {

                if (scalingfactor == 0f && resizedTextStyle.fontSize != style.fontSize) {
                    scalingfactor = (style.fontSize.value - resizedTextStyle.fontSize.value)
                    scalesOverChars = text.length - 1
                } else if (text.length > scalesOverChars) {
                    scalingfactor = ((style.fontSize.value - resizedTextStyle.fontSize.value)/(text.length - scalesOverChars))
                }

                if (text.length > scalesOverChars) {
                    if (scalesOverChars > 0 && text.length < lastlength) {
                        resizedTextStyle = resizedTextStyle.copy(
                            fontSize = (resizedTextStyle.fontSize.value + (scalingfactor * (lastlength - text.length))).sp
                        )
                    }
                } else {
                    resizedTextStyle = resizedTextStyle.copy(
                        fontSize = style.fontSize
                    )
                }

                shouldDraw = true
            }
            lastlength = text.length
        }
    )
}