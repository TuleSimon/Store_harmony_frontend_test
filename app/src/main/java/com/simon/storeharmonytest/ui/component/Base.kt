@file:OptIn(ExperimentalMaterial3Api::class)

package com.simon.storeharmonytest.ui.component

import android.app.Activity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.simon.storeharmonytest.R

/**
 * THis is a base scaffold, use for creating screesn with appbars
 * @param modifier the modifier
 * @param title The appbar title,
 * @param content the scaffold content
 * @param onBackPressed the action called when click on back button
 * @author Tule Simon
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScaffoldWithAppbar(
    modifier: Modifier = Modifier,
    title: String,
    hideBackButton: Boolean = false,
    iconsBackgroundTint: Color = MaterialTheme.colorScheme.surface,
    onBackPressed: () -> Unit,
    actions: @Composable() (RowScope.() -> Unit) = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),
                title = {
                    TitleText(text = title)
                },
                navigationIcon = {
                    if (hideBackButton.not()) {
                        SimonIconButton(
                            onClick = onBackPressed,
                            icon = R.drawable.icon_nav_back,
                            iconsBackgroundTint = iconsBackgroundTint
                        )
                    }
                },
                actions = actions
            )
        }
    ) {
        content(it)
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SimonIconButton(
    onClick: () -> Unit, icon: Int,
    badge: String? = null,
    iconsBackgroundTint: Color = MaterialTheme.colorScheme.surface,
) {
    BadgedBox(
        badge = {
            if (badge != null)
                Badge(
                    containerColor = Color.Red,
                    modifier = Modifier.offset(x = -10.dp, y = 5.dp),
                ) {
                    BodyTextSmall(
                        text = badge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
        },
        modifier = Modifier.combinedClickable() {
            onClick()
        }

    ) {
        Icon(
            modifier = Modifier
                .width(40.dp)
                .shadow(
                    2.dp,
                    CircleShape,
                    ambientColor = MaterialTheme.colorScheme.onSurface.copy(0.1f)
                )
                .background(iconsBackgroundTint)
                .aspectRatio(1f)
                .padding(5.dp),
            tint = MaterialTheme.colorScheme.onSurface.copy(0.7f),
            painter = painterResource(id = icon),
            contentDescription = "back"
        )
    }

}

@Composable
fun SimonPrimaryIconButton(
    onClick: () -> Unit, icon: Int, modifier: Modifier = Modifier,
    iconsBackgroundTint: Color = MaterialTheme.colorScheme.primary
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.primary),
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = iconsBackgroundTint
        )
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "back",
            tint = Color.White
        )
    }
}

@Composable
fun ChangeNavigationBarColor(color: Color, dontDispose:Boolean=true) {
    val view = LocalView.current
    val scheme = MaterialTheme.colorScheme
    val dark = isSystemInDarkTheme()
    DisposableEffect(true) {
        val window = (view.context as Activity).window
        if (!view.isInEditMode) {
            window.navigationBarColor = color.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
        }
        onDispose {
            if(dontDispose.not()) {
                window.navigationBarColor = scheme.background.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                    !dark
            }
        }
    }

}