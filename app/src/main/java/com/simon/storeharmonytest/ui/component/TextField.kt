package com.simon.storeharmonytest.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


/**
 *  a custom textfield following the UI
 * @param modifier the default modifier
 * @param keyboardActions the action of the phone keyboard end button
 * @param hint the hint
 * @param header the header text at the top
 * @param maxLines the maximum number of lines
 * @param keyboardOptions the keyboard options for the textfield input
 */
@Composable
fun FilledTextField(
    modifier: Modifier, value: String,
    keyboardActions: KeyboardActions = KeyboardActions(),
    hint: String,
    hasIcon: Boolean = false,
    icon: Int? = null,
    contentPadding:PaddingValues = PaddingValues(horizontal = 17.dp, vertical = 20.dp),
    header: String? = null,
    maxLines: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    onChange: (String) -> Unit
) {

    val interactionSource = remember {
        MutableInteractionSource()
    }
    val isFocused = interactionSource.collectIsFocusedAsState().value

    Column(modifier = modifier) {
        if (header != null) {
            TitleText(text = header)
            Spacer(modifier = Modifier.height(10.dp))
        }
        BasicTextField(
            value = value,
            onValueChange = {
                onChange(it)
            },
            interactionSource = interactionSource,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    if (isFocused) 1.dp else 0.dp,
                    if (isFocused) MaterialTheme.colorScheme.primary else Color.Transparent,
                    MaterialTheme.shapes.small
                )
                .background(
                    MaterialTheme.colorScheme.surface,
                    MaterialTheme.shapes.medium
                )
                .clip(MaterialTheme.shapes.medium)
                .padding(contentPadding),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            maxLines = maxLines,
            textStyle = MaterialTheme.typography.bodyMedium,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (hasIcon && icon != null) {
                    Icon(
                        painter = painterResource(id = icon),
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = null, modifier = Modifier.width(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                    if (value.isEmpty()) {
                        BodyText(
                            text = hint,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    it()
                }
            }
        }
    }

}