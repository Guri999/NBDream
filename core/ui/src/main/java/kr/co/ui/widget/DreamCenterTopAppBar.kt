package kr.co.ui.widget

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.ArrowLeft
import kr.co.ui.icon.dreamicon.Edit
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DreamCenterTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typo.pageName,
                color = MaterialTheme.colors.gray1
            )
        },
        navigationIcon = navigationIcon,
        actions = actions
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DreamCenterTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = title,
        navigationIcon = navigationIcon,
        actions = actions
    )
}

@Preview
@Composable
private fun DreamCenterTopAppBarPreview() {
    NBDreamTheme {
        DreamCenterTopAppBar(
            title = "장부",
            navigationIcon = {},
            actions = {}
        )
    }
}