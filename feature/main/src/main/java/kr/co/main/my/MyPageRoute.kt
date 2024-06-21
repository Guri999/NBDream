package kr.co.main.my

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kr.co.ui.ext.noRippleClickable
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Defaultprofile
import kr.co.ui.icon.dreamicon.OutlineEdit
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamTopAppBar

@Composable
internal fun MyPageRoute(
    viewModel: MyPageViewModel = hiltViewModel(),
    navigateToProfileEdit: () -> Unit = {},
    navigateToSetting: () -> Unit = {},
    navigateToBookmark: () -> Unit = {},
    navigateToWrite: () -> Unit = {},
    navigateToComment: () -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MyPageScreen(
        state = state,
        navigateToProfileEdit = navigateToProfileEdit,
        navigateToSetting = navigateToSetting,
        navigateToBookmark = navigateToBookmark,
        navigateToWrite = navigateToWrite,
        navigateToComment = navigateToComment
    )
}

@Composable
private fun MyPageScreen(
    state: MyPageViewModel.State = MyPageViewModel.State(),
    navigateToProfileEdit: () -> Unit = {},
    navigateToSetting: () -> Unit = {},
    navigateToBookmark: () -> Unit = {},
    navigateToWrite: () -> Unit = {},
    navigateToComment: () -> Unit = {},
) {
    Surface {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.gray9)
                .padding(horizontal = 16.dp)
        ) {
            item {
                DreamTopAppBar(
                    title = "마이페이지",
                ) {
                    Row {
                        IconButton(onClick = navigateToProfileEdit) {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                imageVector = DreamIcon.OutlineEdit,
                                contentDescription = "edit"
                            )
                        }

                        IconButton(onClick = navigateToSetting) {
                            Icon(
                                modifier = Modifier.size(28.dp),
                                imageVector = Icons.Outlined.Settings,
                                contentDescription = "edit"
                            )
                        }
                    }
                }
            }

            item {
                ProfileCard(
                    imageUrl = state.profileImageUrl?: "",
                    userName = state.name?:"",
                    address = state.address?: "주소를 설정해 주세요"
                )
            }

            item {
                Spacer(modifier = Modifier.height(48.dp))
                BulletinCard(
                    crops = state.crops.orEmpty().ifEmpty { listOf("작물을 등록해 보세요") }
                )
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
                CommunityCard(
                    navigateToBookmark = navigateToBookmark,
                    navigateToWrite = navigateToWrite,
                    navigateToComment = navigateToComment
                )
            }
        }
    }
}

@Composable
private fun CommunityCard(
    navigateToBookmark: () -> Unit = {},
    navigateToWrite: () -> Unit = {},
    navigateToComment: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.white,
                shape = MaterialTheme.shapes.medium
            )
            .padding(24.dp),
    ) {
        Text(
            text = "커뮤니티",
            style = MaterialTheme.typo.h4,
            color = MaterialTheme.colors.gray1,
        )

        Column(
            modifier = Modifier
                .padding(
                    top = 32.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            listOf(
                "저장한 글 보러가기",
                "작성한 글 보러가기",
                "작성한 댓글 보러가기"
            ).forEachIndexed { index, text ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .noRippleClickable {
                            when (index) {
                                0 -> navigateToBookmark()
                                1 -> navigateToWrite()
                                2 -> navigateToComment()
                            }
                        },
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = text,
                        style = MaterialTheme.typo.body1,
                        color = MaterialTheme.colors.gray1
                    )
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = text,
                        tint = MaterialTheme.colors.gray5,
                    )
                }
                if (index < 2)
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colors.gray8
                    )
            }
        }

    }
}

@Composable
private fun BulletinCard(
    crops: List<String> = listOf(),
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.white,
                shape = MaterialTheme.shapes.medium
            )
            .padding(24.dp),
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ){
            Text(
                text = "재배 작물",
                style = MaterialTheme.typo.h4,
                color = MaterialTheme.colors.gray1,
            )

            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .noRippleClickable {  },
                imageVector = Icons.Filled.Add,
                contentDescription = "add crop button"
            )
        }

        Column(
            modifier = Modifier
                .padding(
                    top = 32.dp,
                    bottom = 48.dp
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            crops.forEachIndexed { index, s ->
                Text(
                    text = s,
                    style = MaterialTheme.typo.body1,
                    color = MaterialTheme.colors.gray1
                )

                if (index != crops.lastIndex) {
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colors.gray8
                    )
                }
            }
        }

        if (crops.size > 3) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "전체 보기",
                style = MaterialTheme.typo.label,
                color = MaterialTheme.colors.gray3
            )

            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "extend bulletin list",
                tint = MaterialTheme.colors.gray5
            )
        }
            }
    }
}

@Composable
private fun ProfileCard(
    imageUrl: String = "",
    userName: String = "",
    address: String = "",
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape),
            model = imageUrl,
            error = rememberVectorPainter(image = DreamIcon.Defaultprofile),
            contentDescription = "User's profile image",
            contentScale = ContentScale.Crop,
            placeholder = rememberVectorPainter(image = DreamIcon.Defaultprofile)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = userName,
                style = MaterialTheme.typo.body1,
                color = MaterialTheme.colors.gray1
            )

            Text(
                text = address,
                style = MaterialTheme.typo.body2,
                color = MaterialTheme.colors.gray3
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    NBDreamTheme {
        MyPageScreen()
    }
}