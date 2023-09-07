package com.simon.storeharmonytest.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * This is a button, used for primary button that are important and main action
 * @param modifier used for passing custom modifiers
 * @param text the string to display on button
 * @param isRounded whether the button should be rounded
 * @param onClick th callback action to invoke when button is clicked
 * @author Tule Simon
 */

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier, text: String,
    isRounded: Boolean = true,
    loading: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        shape = if (isRounded) MaterialTheme.shapes.medium else MaterialTheme.shapes.small.copy(
            CornerSize(0.dp)
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(0.5f)
        ),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 18.dp)
    ) {
        if (loading.not()) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.W500,
                    lineHeight = 18.7.sp
                ), textAlign = TextAlign.Center
            )
        } else {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    }
}


/**
 * This is a button, used for primary button that are important and main action
 * @param modifier used for passing custom modifiers
 * @param text the string to display on button
 * @param onClick th callback action to invoke when button is clicked
 * @author Tule Simon
 */

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier, text: String,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        modifier = modifier, shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 18.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 17.sp,
                fontWeight = FontWeight.W500,
                lineHeight = 18.7.sp
            ), textAlign = TextAlign.Center
        )
    }

}

