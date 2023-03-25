package com.lifeutil.jokester.ui.util

import android.content.res.Configuration
import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Default radius of an unbounded ripple in an IconButton
private val RippleRadius = 24.dp

/**
 * Icon Button with adjustable indication option. Indication of standard [IconButton] cannot
 * be changed thus making it have bigger radius than necessary in some cases.
 */
@Composable
fun IndicatingIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    indication: Indication = rememberRipple(bounded = false, radius = RippleRadius),
    content: @Composable () -> Unit
) {

    Box(
        modifier = modifier
            .clickable(
                onClick = onClick,
                enabled = enabled,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = indication
            ),
        contentAlignment = Alignment.Center
    ) {
        // for alpha
        val contentColor =
            if (enabled) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(
                alpha = 0.38f
            )
        CompositionLocalProvider(LocalContentColor provides contentColor, content = content)
    }
}

@Preview
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun IndicatingIconButtonPreview() {
    IndicatingIconButton(onClick = {}) {
        Icon(
            imageVector = Icons.Filled.CameraAlt,
            contentDescription = "camera"
        )
    }
}