package com.simon.storeharmonytest.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

/**
 * This is a header text, used for text that are titles
 * @param modifier used for passing custom modifiers
 * @param text the string to display
 * @param textAlign the alignemnt of the text
 * @author Tule Simon
 */

@Composable
fun HeaderText(
    modifier: Modifier = Modifier,
    textAlign:TextAlign = TextAlign.Center,
    text: String
) {

    Text(
        text = text, modifier = modifier,
        textAlign = textAlign,
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.titleMedium.copy(
            fontSize = 26.sp,
            fontWeight = FontWeight.W600,
            lineHeight = 27.5.sp
        )
    )
}

@Composable
fun TitleText(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Center
) {

    Text(
        text = text, modifier = modifier,
        textAlign = textAlign,
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.titleMedium.copy(
            fontSize = 17.sp,
            fontWeight = FontWeight.W600,
            lineHeight = 18.7.sp
        )
    )

}


/**
 * This is a body text, used for text that are for descriptions
 * @param modifier used for passing custom modifiers
 * @param text the string to display
 * @param textAlign the alignemnt of the text
 * @author Tule Simon
 */

@Composable
fun BodyText(
    modifier: Modifier = Modifier, text: String,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colorScheme.onBackground
) {

    Text(
        text = text, modifier = modifier,
        maxLines = maxLines,
        textAlign = textAlign,
        color = color,
        style = MaterialTheme.typography.bodyMedium.copy(
            fontSize = 15.sp,
            fontWeight = FontWeight.W400,
            lineHeight = 21.sp
        )
    )

}

@Composable
fun BodyTextSmaller(
    modifier: Modifier = Modifier, text: String,
    textAlign: TextAlign = TextAlign.Start
) {

    Text(
        text = text, modifier = modifier,
        textAlign = textAlign,
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.bodyMedium.copy(
            fontSize = 12.sp,
            fontWeight = FontWeight.W400,
            lineHeight = 21.sp
        )
    )

}

@Composable
fun BodyTextSmall(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    maxLines: Int = Int.MAX_VALUE,
    text: String,
    textAlign: TextAlign = TextAlign.Start
) {

    Text(
        text = text, modifier = modifier,
        maxLines = maxLines,
        textAlign = textAlign,
        color = color,
        style = MaterialTheme.typography.bodyMedium.copy(
            fontSize = 12.sp,
            fontWeight = FontWeight.W400,
            lineHeight = 21.sp
        )
    )

}