package com.larryyu.zonak_task.ui.extensions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

@Composable
fun Modifier.npClickable(withRipple: Boolean = false, onClick: () -> Unit): Modifier =
    composed {
        if (withRipple) {
            this.clickable { onClick() }
        } else {
            this.clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ) {
                onClick()
            }
        }
    }

