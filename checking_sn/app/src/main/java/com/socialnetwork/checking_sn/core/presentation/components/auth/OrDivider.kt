package com.socialnetwork.checking_sn.core.presentation.components.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.socialnetwork.checking_sn.R
import com.socialnetwork.checking_sn.ui.theme.DividerColor

@Composable
fun OrDivider(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth(0.8f)
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f), color = DividerColor)
        Text(
            text = stringResource(id = R.string.or),
            modifier = Modifier,
            color = androidx.compose.ui.graphics.Color.Gray
        )
        HorizontalDivider(modifier = Modifier.weight(1f), color = DividerColor)
    }
}
